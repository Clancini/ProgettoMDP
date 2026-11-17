package it.unicam.cs.mpgc.rpg130575.Game.Albums;

/// Stores information about a chart event involving an asset at the given path.
/// Provides access to some of the asset's attributes.
public record AssetChartEventInfo(ChartEventType Type, double StartTime, String AssetPath, float Width, float Height) implements IChartEventInfo { }
