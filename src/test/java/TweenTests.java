import it.unicam.cs.mpgc.rpg130575.Types.Tweening.Tween;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TweenTests
{
    @Test
    public void PassingEnoughTimeCompletes()
    {
        TestingTween tween = new TestingTween(5d);

        assertEquals(0, tween.GetProgress());
        assertFalse(tween.IsDone());

        float[] expectedProgress = new float[]
        {
                0.2f,
                0.4f,
                0.6f,
                0.8f
        };

        for (int i = 0; i < expectedProgress.length; i++)
        {
            tween.TickForward(1d);
            assertEquals(expectedProgress[i], tween.GetProgress());
        }

        assertFalse(tween.IsDone());

        tween.TickForward(1d);
        assertEquals(1f, tween.GetProgress());

        assertTrue(tween.IsDone());
    }

    private class TestingTween extends Tween
    {
        private int _updates;

        public TestingTween(double duration)
        {
            super(duration);
        }

        public int GetUpdatesCount() { return _updates; }

        @Override
        protected void Update()
        {
            _updates++;
        }
    }
}
