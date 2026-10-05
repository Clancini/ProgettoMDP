import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Nodes.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.LogicNode;
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

        assertEquals(0, container.WorldTransform.GetPositionX());
        assertEquals(0, container.WorldTransform.GetPositionY());

        assertEquals(0, node.WorldTransform.GetPositionX());
        assertEquals(0, node.WorldTransform.GetPositionY());

        container.SetPositionX(50);
        container.UpdateLayout();

        assertEquals(50, container.WorldTransform.GetPositionX());
        assertEquals(0, container.WorldTransform.GetPositionY());

        assertEquals(50, node.WorldTransform.GetPositionX());
        assertEquals(0, node.WorldTransform.GetPositionY());

        node.SetPositionX(50);
        container.UpdateLayout();

        assertEquals(50, container.WorldTransform.GetPositionX());
        assertEquals(0, container.WorldTransform.GetPositionY());

        assertEquals(100, node.WorldTransform.GetPositionX());
        assertEquals(0, node.WorldTransform.GetPositionY());
    }
}
