package it.unicam.cs.mpgc.rpg130575.Game.Albums;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/// Handles reading and parsing of chart files inside an album.
/// Required assets (e.g. track, images) must be loaded separately.
public class ChartImporter
{
    public Map.Entry<String, ChartInfo> Import(Path chartPath, String albumName, String albumLookup)
            throws IOException, ParserConfigurationException, SAXException
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document chartXml = builder.parse(chartPath.resolve("ChartInfo.xml").toFile());

        String chartLookup = chartXml.getElementsByTagName("Lookup")
                .item(0)
                .getTextContent()
                .trim();

        Element background = (Element) chartXml.getElementsByTagName("Background").item(0);
        Element track = (Element) chartXml.getElementsByTagName("Track").item(0);

        String assetsRoot = "/Albums/" + albumName + "/Charts/" + chartPath.getFileName() + "/Assets/";
        String prefix = "Chart" + albumLookup + chartLookup;

        List<IChartEventInfo> events = new ArrayList<>();
        NodeList eventNodes = chartXml.getElementsByTagName("Event");

        for (int i = 0; i < eventNodes.getLength(); i++)
        {
            Element event = (Element) eventNodes.item(i);

            // This can be swapped for a dispatcher if more event types become available.
            if (event.hasAttribute("assetPath"))
            {
                events.add(new AssetChartEventInfo(
                        ChartEventType.valueOf(event.getAttribute("type")),
                        Double.parseDouble(event.getAttribute("startTime")),
                        assetsRoot + event.getAttribute("assetPath"),
                        Float.parseFloat(event.getAttribute("width")),
                        Float.parseFloat(event.getAttribute("height"))
                ));
            }
            else
            {
                events.add(new ChartEventInfo(
                        ChartEventType.valueOf(event.getAttribute("type")),
                        Double.parseDouble(event.getAttribute("startTime"))
                ));
            }
        }

        events.sort(Comparator.comparingDouble(IChartEventInfo::StartTime));

        List<ChartNoteInfo> notes = new ArrayList<>();
        NodeList noteNodes = chartXml.getElementsByTagName("Note");

        for (int i = 0; i < noteNodes.getLength(); i++)
        {
            Element note = (Element) noteNodes.item(i);

            notes.add(new ChartNoteInfo(
                    Double.parseDouble(note.getAttribute("pressTime")),
                    Integer.parseInt(note.getAttribute("lane"))
            ));
        }

        ChartInfo chartInfo = new ChartInfo(
                assetsRoot + background.getAttribute("path"),
                prefix + "Background",
                assetsRoot + track.getAttribute("path"),
                prefix + "Track",
                List.copyOf(events),
                List.copyOf(notes));

        return Map.entry(chartLookup, chartInfo);
    }
}