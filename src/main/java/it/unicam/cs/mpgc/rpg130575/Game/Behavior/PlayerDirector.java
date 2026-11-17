package it.unicam.cs.mpgc.rpg130575.Game.Behavior;

import it.unicam.cs.mpgc.rpg130575.Game.Albums.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// Handles dispatching of notes and events against a chart.
public class PlayerDirector
{
    public static final double NoteTravelSeconds = 2.0;

    private final IClock _clock;

    private final List<IChartEventInfo> _events;
    private final List<Consumer<IChartEventInfo>> _eventListeners = new ArrayList<>();
    private int _nextEventIndex;

    private final List<ChartNoteInfo> _notes;
    private final List<Consumer<ChartNoteInfo>> _noteSpawnListeners = new ArrayList<>();
    private int _nextNoteIndex;

    public PlayerDirector(ChartInfo chartInfo, IClock clock)
    {
        _clock = clock;
        _events = chartInfo.Events();
        _notes = chartInfo.Notes();
    }

    public void AddEventListener(Consumer<IChartEventInfo> listener)
    {
        _eventListeners.add(listener);
    }

    public void AddNoteCreatedListener(Consumer<ChartNoteInfo> listener)
    {
        _noteSpawnListeners.add(listener);
    }

    public void Update()
    {
        UpdateEvents();
        UpdateNotes();
    }

    private void UpdateEvents()
    {
        while (_nextEventIndex < _events.size())
        {
            IChartEventInfo event = _events.get(_nextEventIndex);

            if (_clock.GetTime() < event.StartTime())
                break;

            NotifyEventListeners(event);
            _nextEventIndex++;
        }
    }

    private void NotifyEventListeners(IChartEventInfo event)
    {
        for (Consumer<IChartEventInfo> listener : _eventListeners)
            listener.accept(event);
    }

    private void UpdateNotes()
    {
        while (_nextNoteIndex < _notes.size())
        {
            ChartNoteInfo note = _notes.get(_nextNoteIndex);

            double spawnTime = note.PressTime() - NoteTravelSeconds;

            if (_clock.GetTime() < spawnTime)
                break;

            NotifyNoteSpawned(note);
            _nextNoteIndex++;
        }
    }

    private void NotifyNoteSpawned(ChartNoteInfo note)
    {
        for (Consumer<ChartNoteInfo> listener : _noteSpawnListeners)
            listener.accept(note);
    }
}