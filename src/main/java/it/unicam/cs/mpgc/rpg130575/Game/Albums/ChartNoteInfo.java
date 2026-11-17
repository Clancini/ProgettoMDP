package it.unicam.cs.mpgc.rpg130575.Game.Albums;

/// Stores information about a note in a chart.
/// The note will appear in the corresponding lane (0-3 in left to right fashion).
public record ChartNoteInfo(double PressTime, int Lane) { }