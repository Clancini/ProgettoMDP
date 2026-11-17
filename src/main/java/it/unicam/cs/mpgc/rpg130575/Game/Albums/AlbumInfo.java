package it.unicam.cs.mpgc.rpg130575.Game.Albums;

import java.util.Map;

/// Stores information about an album and charts it contains.
public record AlbumInfo(String ListItemPath, String ListItemLookup, Map<String, ChartInfo> Charts) { }
