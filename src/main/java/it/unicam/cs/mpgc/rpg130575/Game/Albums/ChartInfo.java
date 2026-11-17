package it.unicam.cs.mpgc.rpg130575.Game.Albums;

import java.util.List;

/// Stores information about a chart, including its attributes, notes, events and asset paths.
public record ChartInfo(
        String BackgroundPath,
        String BackgroundLookup,
        String TrackPath,
        String TrackLookup,
        List<IChartEventInfo> Events,
        List<ChartNoteInfo> Notes) { }