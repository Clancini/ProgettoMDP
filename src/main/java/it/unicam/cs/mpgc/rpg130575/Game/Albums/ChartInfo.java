package it.unicam.cs.mpgc.rpg130575.Game.Albums;

import java.util.List;

public record ChartInfo(
        String BackgroundPath,
        String BackgroundLookup,
        String TrackPath,
        String TrackLookup,
        List<IChartEventInfo> Events,
        List<ChartNoteInfo> Notes) { }