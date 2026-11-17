package it.unicam.cs.mpgc.rpg130575.Game.Behavior;

import it.unicam.cs.mpgc.rpg130575.Game.Albums.ChartNoteInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/** Handles note lifetimes. Specifically:<br/>
 * - Expiring notes when they time out;<br/>
 * - Judging player input related to note hitting.
 */
public class NoteHitChecker
{
    public static final double HitWindowSeconds = 0.1;
    public static final double MaxEarlyMissWindow = HitWindowSeconds * 2;

    private final IClock _clock;

    private final List<ChartNoteInfo> _activeNotes = new ArrayList<>();
    private final List<ChartNoteInfo> _simultaneousNotes = new ArrayList<>();

    private final List<ChartNoteInfo> _expiredNotes = new ArrayList<>();

    private final List<Consumer<ChartNoteInfo>> _noteDestroyListeners = new ArrayList<>();
    private final List<Consumer<NoteHitResult>> _resultListeners = new ArrayList<>();

    public NoteHitChecker(IClock clock)
    {
        _clock = clock;
    }

    public void OnNoteSpawned(ChartNoteInfo note)
    {
        _activeNotes.add(note);
    }

    public void AddResultListener(Consumer<NoteHitResult> listener)
    {
        _resultListeners.add(listener);
    }

    public void AddNoteDestroyListener(Consumer<ChartNoteInfo> listener)
    {
        _noteDestroyListeners.add(listener);
    }

    public void OnLaneHit(int lane)
    {
        if (_activeNotes.isEmpty())
            return;

        double currentTime = _clock.GetTime();

        ChartNoteInfo firstNote = _activeNotes.getFirst();
        double difference = Math.abs(currentTime - firstNote.PressTime());

        // Do not MISS on notes that aren't remotely close yet.
        if (difference > MaxEarlyMissWindow)
            return;

        _simultaneousNotes.clear();
        ChartNoteInfo pressedNote = null;

        for (ChartNoteInfo note : _activeNotes)
        {
            if (note.PressTime() != firstNote.PressTime())
                break;

            _simultaneousNotes.add(note);

            if (note.Lane() == lane)
                pressedNote = note;
        }

        // If no note was pressed (i.e. MISS) all simultaneous ones should count as MISS as well.
        if (pressedNote == null)
        {
            for (ChartNoteInfo note : _simultaneousNotes)
                JudgeNote(note, NoteHitResult.MISS, currentTime);

            return;
        }

        NoteHitResult result = difference <= HitWindowSeconds ? NoteHitResult.HIT : NoteHitResult.MISS;

        JudgeNote(pressedNote, result, currentTime);
    }

    private void JudgeNote(ChartNoteInfo note, NoteHitResult result, double currentTime)
    {
        NotifyResult(result);
        PrintResult(result, note.PressTime(), currentTime);

        DestroyNote(note);
    }

    public void Update()
    {
        double currentTime = _clock.GetTime();

        _expiredNotes.clear();

        for (ChartNoteInfo note : _activeNotes)
        {
            if (currentTime > note.PressTime() + HitWindowSeconds)
                _expiredNotes.add(note);
        }

        for (ChartNoteInfo note : _expiredNotes)
        {
            NotifyResult(NoteHitResult.MISS);
            PrintResult(NoteHitResult.MISS, note.PressTime(), currentTime);
            DestroyNote(note);
        }
    }

    private void NotifyResult(NoteHitResult result)
    {
        for (Consumer<NoteHitResult> listener : _resultListeners)
            listener.accept(result);
    }

    private void PrintResult(NoteHitResult result, double expectedTime, double actualTime)
    {
        double difference = (actualTime - expectedTime) * 1000;

        System.out.printf(
                "%s | Expected: %.0f ms | Actual: %.0f ms | Difference: %+.0f ms%n",
                result,
                expectedTime * 1000,
                actualTime * 1000,
                difference);
    }

    private void DestroyNote(ChartNoteInfo note)
    {
        _activeNotes.remove(note);

        for (Consumer<ChartNoteInfo> listener : _noteDestroyListeners)
            listener.accept(note);
    }
}