import it.unicam.cs.mpgc.rpg130575.Game.Albums.ChartNoteInfo;
import it.unicam.cs.mpgc.rpg130575.Game.Behavior.Clock;
import it.unicam.cs.mpgc.rpg130575.Game.Behavior.NoteHitChecker;
import it.unicam.cs.mpgc.rpg130575.Game.Behavior.NoteHitResult;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class NoteHitCheckerTests
{
    @Test
    public void AllOldNotesAreExpired()
    {
        Clock clock = new Clock();
        clock.Start();

        NoteHitChecker checker = new NoteHitChecker(clock);

        checker.OnNoteSpawned(new ChartNoteInfo(0, 0));
        checker.OnNoteSpawned(new ChartNoteInfo(10, 0));

        AtomicInteger destroyedNotes = new AtomicInteger();
        AtomicInteger missJudgements = new AtomicInteger();

        checker.AddNoteDestroyListener((note) ->
        {
            destroyedNotes.set(destroyedNotes.get() + 1);
        });

        checker.AddResultListener((result) ->
        {
            if (result == NoteHitResult.MISS)
                missJudgements.set(missJudgements.get() + 1);
        });

        clock.Update(20);
        checker.Update();

        assertEquals(2, destroyedNotes.get());
        assertEquals(2, missJudgements.get());
    }

    @Test
    public void OnlyCloseNotesCanBeMissed()
    {
        Clock clock = new Clock();
        clock.Start();

        NoteHitChecker checker = new NoteHitChecker(clock);

        checker.OnNoteSpawned(new ChartNoteInfo(1 + NoteHitChecker.MaxEarlyMissWindow, 0));

        AtomicInteger missJudgements = new AtomicInteger();

        checker.AddResultListener((result) ->
        {
            if (result == NoteHitResult.MISS)
                missJudgements.set(missJudgements.get() + 1);
        });

        checker.OnLaneHit(0);

        assertEquals(0, missJudgements.get());

        clock.Update(1);

        checker.OnLaneHit(0);

        assertEquals(1, missJudgements.get());
    }

    @Test
    public void MultipleNotesHitDifferentLaneMisses()
    {
        Clock clock = new Clock();
        clock.Start();

        NoteHitChecker checker = new NoteHitChecker(clock);

        checker.OnNoteSpawned(new ChartNoteInfo(1, 1));
        checker.OnNoteSpawned(new ChartNoteInfo(1, 3));

        AtomicInteger missJudgements = new AtomicInteger();

        checker.AddResultListener((result) ->
        {
            if (result == NoteHitResult.MISS)
                missJudgements.set(missJudgements.get() + 1);
        });

        clock.Update(1 + (NoteHitChecker.HitWindowSeconds / 2));

        checker.OnLaneHit(0);

        assertEquals(2, missJudgements.get());
    }

    @Test
    public void MultipleNotesSameLaneMisses()
    {
        Clock clock = new Clock();
        clock.Start();

        NoteHitChecker checker = new NoteHitChecker(clock);

        checker.OnNoteSpawned(new ChartNoteInfo(1, 1));
        checker.OnNoteSpawned(new ChartNoteInfo(1, 3));

        AtomicInteger missJudgements = new AtomicInteger();

        checker.AddResultListener((result) ->
        {
            if (result == NoteHitResult.MISS)
                missJudgements.set(missJudgements.get() + 1);
        });

        clock.Update(1 + (NoteHitChecker.HitWindowSeconds / 2));

        checker.OnLaneHit(1);
        checker.OnLaneHit(1);

        assertEquals(1, missJudgements.get());
    }

    @Test
    public void MultipleNotesHit()
    {
        Clock clock = new Clock();
        clock.Start();

        NoteHitChecker checker = new NoteHitChecker(clock);

        checker.OnNoteSpawned(new ChartNoteInfo(1, 1));
        checker.OnNoteSpawned(new ChartNoteInfo(1, 3));

        AtomicInteger hitJudgements = new AtomicInteger();

        checker.AddResultListener((result) ->
        {
            if (result == NoteHitResult.HIT)
                hitJudgements.set(hitJudgements.get() + 1);
        });

        clock.Update(1 + (NoteHitChecker.HitWindowSeconds / 2));

        checker.OnLaneHit(1);
        checker.OnLaneHit(3);

        assertEquals(2, hitJudgements.get());
    }

    @Test
    public void FirstNoteHasPriority()
    {
        Clock clock = new Clock();
        clock.Start();

        NoteHitChecker checker = new NoteHitChecker(clock);

        ChartNoteInfo earlyNote = new ChartNoteInfo(1, 0);
        ChartNoteInfo laterNote = new ChartNoteInfo(1 + NoteHitChecker.HitWindowSeconds, 0);

        checker.OnNoteSpawned(earlyNote);
        checker.OnNoteSpawned(laterNote);

        List<ChartNoteInfo> destroyedNotes = new ArrayList<>();
        List<NoteHitResult> results = new ArrayList<>();

        checker.AddNoteDestroyListener(destroyedNotes::add);
        checker.AddResultListener(results::add);

        clock.Update(1 + (NoteHitChecker.HitWindowSeconds * 0.9));

        checker.OnLaneHit(0);

        assertEquals(1, destroyedNotes.size());
        assertSame(earlyNote, destroyedNotes.getFirst());
        assertEquals(List.of(NoteHitResult.HIT), results);

        checker.OnLaneHit(0);

        assertEquals(2, destroyedNotes.size());
        assertSame(laterNote, destroyedNotes.get(1));
        assertEquals(List.of(NoteHitResult.HIT, NoteHitResult.HIT), results);
    }
}
