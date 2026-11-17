package it.unicam.cs.mpgc.rpg130575.Game.Albums;

/// Represents a generic chart event.
/// Consumers are responsible for appropriately dispatching based on type.
public interface IChartEventInfo
{
    public ChartEventType Type();
    public double StartTime();
}
