package it.unicam.cs.mpgc.rpg130575.Game.Albums;

/// Stores information about a generic chart event involving a parameterless action.
public record ChartEventInfo(ChartEventType Type, double StartTime) implements IChartEventInfo { }

