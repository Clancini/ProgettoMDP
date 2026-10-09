import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LayoutNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogicNodeLayoutTests
{
    @Test
    public void ContainerMovementMovesChildren()
    {
        CompositeLogicNode container = new CompositeLogicNode();
        LogicNode node = new LogicNode();

        container.Load(DependencyContainer.Empty);
        container.Add(node);

        LayoutNode containerLayout = container.GetLayoutNode();
        LayoutNode nodeLayout = node.GetLayoutNode();

        assertEquals(0, containerLayout.WorldTransform.GetPositionX());
        assertEquals(0, containerLayout.WorldTransform.GetPositionY());

        assertEquals(0, nodeLayout.WorldTransform.GetPositionX());
        assertEquals(0, nodeLayout.WorldTransform.GetPositionY());

        containerLayout.LocalTransform.SetPositionX(50);
        containerLayout.UpdateLayout();

        assertEquals(50, containerLayout.WorldTransform.GetPositionX());
        assertEquals(0, containerLayout.WorldTransform.GetPositionY());

        assertEquals(50, nodeLayout.WorldTransform.GetPositionX());
        assertEquals(0, nodeLayout.WorldTransform.GetPositionY());

        nodeLayout.LocalTransform.SetPositionX(50);
        containerLayout.UpdateLayout();

        assertEquals(50, containerLayout.WorldTransform.GetPositionX());
        assertEquals(0, containerLayout.WorldTransform.GetPositionY());

        assertEquals(100, nodeLayout.WorldTransform.GetPositionX());
        assertEquals(0, nodeLayout.WorldTransform.GetPositionY());
    }
}
