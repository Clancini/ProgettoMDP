import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKey;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;
import it.unicam.cs.mpgc.rpg130575.Input.GameInputPropagator;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeLogicNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputPropagationTests
{
    @Test
    public void NodeDoesNotJoinQueue()
    {
        TestingNode node = new TestingNode(false);

        GameInputPropagator propagator = new GameInputPropagator(node);
        propagator.OnKeyboardInput(new CKeyboardKeyEvent(CKeyboardKey.ENTER));

        assertFalse(node.GetHasReceivedInput());
    }

    public void AcceptingCompositeNodeIgnoreChildren()
    {
        TestingCompositeNode compositeNode = new TestingCompositeNode(true, false);
        TestingNode node = new TestingNode(true);

        compositeNode.Add(node);

        GameInputPropagator propagator = new GameInputPropagator(compositeNode);
        propagator.OnKeyboardInput(new CKeyboardKeyEvent(CKeyboardKey.ENTER));

        assertFalse(node.GetHasReceivedInput());
        assertTrue(compositeNode.GetHasReceivedInput());
    }

    public void IgnoringCompositeNodeConsiderChildren()
    {
        TestingCompositeNode compositeNode = new TestingCompositeNode(false, false);
        TestingNode node = new TestingNode(true);

        compositeNode.Add(node);

        GameInputPropagator propagator = new GameInputPropagator(compositeNode);
        propagator.OnKeyboardInput(new CKeyboardKeyEvent(CKeyboardKey.ENTER));

        assertTrue(node.GetHasReceivedInput());
        assertFalse(compositeNode.GetHasReceivedInput());
    }

    public void ReturningTrueBlocksPropagation()
    {
        TestingCompositeNode compositeNode = new TestingCompositeNode(true, false);
        TestingNode node = new TestingNode(true);

        compositeNode.Add(node);

        GameInputPropagator propagator = new GameInputPropagator(compositeNode);
        propagator.OnKeyboardInput(new CKeyboardKeyEvent(CKeyboardKey.ENTER));

        assertTrue(node.GetHasReceivedInput());
        assertFalse(compositeNode.GetHasReceivedInput());
    }

    private class TestingNode extends LogicNode
    {
        private boolean _hasReceivedInput;

        public TestingNode(boolean acceptsInput)
        {
            SetAcceptsInput(acceptsInput);
        }

        public boolean GetHasReceivedInput() { return _hasReceivedInput; }

        @Override
        public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event)
        {
            _hasReceivedInput = true;

            return true;
        }
    }

    private class TestingCompositeNode extends CompositeLogicNode
    {
        private boolean _hasReceivedInput;

        public TestingCompositeNode(boolean acceptsInput, boolean canChildrenAcceptInput)
        {
            SetAcceptsInput(acceptsInput);
            SetIgnoreSubtreeForInput(!canChildrenAcceptInput);
        }

        public boolean GetHasReceivedInput() { return _hasReceivedInput; }

        @Override
        public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event)
        {
            _hasReceivedInput = true;

            return true;
        }
    }
}
