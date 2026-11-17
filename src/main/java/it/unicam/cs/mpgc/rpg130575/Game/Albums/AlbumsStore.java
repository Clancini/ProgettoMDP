
package it.unicam.cs.mpgc.rpg130575.Game.Albums;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/// Handles loading and caching of albums (i.e. a collection of charts).
public class AlbumsStore
{
    private final LinkedHashMap<String, AlbumInfo> _lookup = new LinkedHashMap<>();
    private final ChartImporter _chartImporter = new ChartImporter();

    public Path[] FindAlbums() throws URISyntaxException, IOException
    {
        return Files.list(
                        Path.of(AlbumsStore.class.getResource("/Albums")
                                .toURI()))
                .filter(i -> Files.isDirectory(i))
                .sorted()
                .toArray(n -> new Path[n]);
    }

    public void ReloadAllAlbums(Path[] paths) throws IOException, ParserConfigurationException, SAXException
    {
        _lookup.clear();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        for (Path albumPath : paths)
        {
            Document albumXml = builder.parse(albumPath.resolve("AlbumInfo.xml").toFile());

            String albumLookup = albumXml.getElementsByTagName("AlbumLookup")
                    .item(0)
                    .getTextContent()
                    .trim();

            String albumName = albumPath.getFileName().toString();
            String listItemPath = "/Albums/" + albumName + "/ListItem.png";
            String listItemLookup = "Album" + albumLookup + "ListItem";

            Map<String, ChartInfo> chartsMap = new LinkedHashMap<>();

            Path chartsDirectory = albumPath.resolve("Charts");
            List<Path> chartPaths = new ArrayList<>();

            if (Files.isDirectory(chartsDirectory))
            {
                try (Stream<Path> charts = Files.list(chartsDirectory))
                {
                    chartPaths = charts.filter(p -> Files.isDirectory(p))
                            .sorted()
                            .toList();
                }
            }

            for (Path chartPath : chartPaths)
            {
                Map.Entry<String, ChartInfo> chart = _chartImporter.Import(
                        chartPath,
                        albumName,
                        albumLookup);

                chartsMap.put(chart.getKey(), chart.getValue());
            }

            _lookup.put(albumLookup, new AlbumInfo(listItemPath, listItemLookup, chartsMap));
        }
    }

    public AlbumInfo[] GetLoadedAlbums()
    {
        return _lookup.values()
                .toArray(n -> new AlbumInfo[n]);
    }
}
