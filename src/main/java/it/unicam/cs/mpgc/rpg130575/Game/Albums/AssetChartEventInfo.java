package it.unicam.cs.mpgc.rpg130575.Game.Albums;

public record AssetChartEventInfo(ChartEventType Type, double StartTime, String AssetPath, float Width, float Height) implements IChartEventInfo { }
