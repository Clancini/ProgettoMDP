package it.unicam.cs.mpgc.rpg130575.Game.Albums;

import java.util.Map;

public record AlbumInfo(String ListItemPath, String ListItemLookup, Map<String, ChartInfo> Charts) {
}
