import it.unicam.cs.mpgc.rpg130575.Nodes.Base.Node;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CompositeNodeTests
{
    @Test
    public void CompositeLogicNodeAddWaitsOneFrame()
    {
        CompositeNode composite = new CompositeNode();

        TestingNode child1 = new TestingNode(null, null);
        TestingNode child2 = new TestingNode(null, null);
        TestingNode addingChild3 = new TestingNode(composite, child2);

        composite.Add(child1);
        composite.Add(addingChild3);

        assertEquals(0, child1.GetUpdates());
        assertEquals(0, child2.GetUpdates());
        assertEquals(0, addingChild3.GetUpdates());
        assertNull(child2.GetLayoutNode().GetParent());

        composite.Update(0);

        assertEquals(1, child1.GetUpdates());
        assertEquals(0, child2.GetUpdates());
        assertEquals(1, addingChild3.GetUpdates());
        assertEquals(composite, child2.GetLayoutNode().GetParent());

        composite.Update(0);

        assertEquals(2, child1.GetUpdates());
        assertEquals(1, child2.GetUpdates());
        assertEquals(2, addingChild3.GetUpdates());
    }

    private class TestingNode extends Node
    {
        private CompositeNode _toAddTo;
        private Node _toAdd;

        private int _updates;

        public TestingNode(CompositeNode toAddTo, Node toAdd)
        {
            _toAddTo = toAddTo;
            _toAdd = toAdd;
        }

        public int GetUpdates() { return _updates; }

        @Override
        public void Update(double deltaSeconds)
        {
            if (_toAddTo != null && _toAdd != null)
            {
                _toAddTo.Add(_toAdd);

                _toAddTo = null;
                _toAdd = null;
            }

            _updates++;
        }
    }

    private class TestingCompositeNode extends CompositeNode
    {
        private int _updates;

        public int GetUpdates() { return _updates; }

        @Override
        public void PostChildrenUpdate(double deltaSeconds)
        {
            _updates++;
        }
    }
}
