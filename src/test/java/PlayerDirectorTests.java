import it.unicam.cs.mpgc.rpg130575.Game.Albums.ChartEventInfo;
import it.unicam.cs.mpgc.rpg130575.Game.Albums.ChartEventType;
import it.unicam.cs.mpgc.rpg130575.Game.Albums.ChartInfo;
import it.unicam.cs.mpgc.rpg130575.Game.Albums.ChartNoteInfo;
import it.unicam.cs.mpgc.rpg130575.Game.Behavior.Clock;
import it.unicam.cs.mpgc.rpg130575.Game.Behavior.PlayerDirector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerDirectorTests
{
    @Test
    public void AllEventsInvokeIfWaitsTooLong()
    {
        ChartInfo chartInfo = new ChartInfo("", "", "", "", new ArrayList<>(), new ArrayList<>());

        chartInfo.Events().add(new ChartEventInfo(ChartEventType.Pause, 0));
        chartInfo.Events().add(new ChartEventInfo(ChartEventType.Resume, 10));

        Clock clock = new Clock();
        clock.Start();

        PlayerDirector director = new PlayerDirector(chartInfo, clock);

        AtomicInteger eventInvokations = new AtomicInteger();

        // https://stackoverflow.com/questions/75893410/how-to-update-a-variable-from-inside-lambda
        director.AddEventListener((event) ->
        {
            eventInvokations.set(eventInvokations.get() + 1);
        });

        clock.Update(30);
        director.Update();

        assertEquals(2, eventInvokations.get());
    }

    @Test
    public void AllNotesInvokeIfWaitsToLong()
    {
        ChartInfo chartInfo = new ChartInfo("", "", "", "", new ArrayList<>(), new ArrayList<>());

        chartInfo.Notes().add(new ChartNoteInfo(0, 0));
        chartInfo.Notes().add(new ChartNoteInfo(10, 0));

        Clock clock = new Clock();
        clock.Start();

        PlayerDirector director = new PlayerDirector(chartInfo, clock);

        AtomicInteger eventInvokations = new AtomicInteger();

        // https://stackoverflow.com/questions/75893410/how-to-update-a-variable-from-inside-lambda
        director.AddNoteCreatedListener((event) ->
        {
            eventInvokations.set(eventInvokations.get() + 1);
        });

        clock.Update(30);
        director.Update();

        assertEquals(2, eventInvokations.get());
    }
}
