import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Types.Layout.LayoutInvalidation;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogicNodeLayoutTests
{
    @Test
    public void LayoutUpdatesArePropagated()
    {
        CompositeLogicNode rootNode = new CompositeLogicNode();

        LogicNode childNode1 = new LogicNode();
        LogicNode childNode2 = new LogicNode();

        rootNode.Add(childNode1);
        rootNode.Add(childNode2);

        assertTrue(rootNode.GetLayoutNode().HasLayoutInvalidation(EnumSet.allOf(LayoutInvalidation.class)));
        assertTrue(childNode1.GetLayoutNode().HasLayoutInvalidation(EnumSet.allOf(LayoutInvalidation.class)));
        assertTrue(childNode2.GetLayoutNode().HasLayoutInvalidation(EnumSet.allOf(LayoutInvalidation.class)));

        rootNode.GetLayoutNode().UpdateLayout();

        assertTrue(rootNode.GetLayoutNode().HasLayoutInvalidation(EnumSet.noneOf(LayoutInvalidation.class)));
        assertTrue(childNode1.GetLayoutNode().HasLayoutInvalidation(EnumSet.noneOf(LayoutInvalidation.class)));
        assertTrue(childNode2.GetLayoutNode().HasLayoutInvalidation(EnumSet.noneOf(LayoutInvalidation.class)));
    }

    @Test
    public void LayoutInvalidationsArePropagated()
    {
        CompositeLogicNode rootNode = new CompositeLogicNode();

        LogicNode childNode1 = new LogicNode();
        LogicNode childNode2 = new LogicNode();

        rootNode.Add(childNode1);
        rootNode.Add(childNode2);

        rootNode.GetLayoutNode().UpdateLayout();

        rootNode.GetLayoutNode().LocalTransform.SetPositionX(50);

        assertTrue(rootNode.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childNode1.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childNode2.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
    }

    @Test
    public void LayoutInvalidationArePropagatedToParents()
    {
        CompositeLogicNode rootNode = new CompositeLogicNode();
        CompositeLogicNode childParentNode = new CompositeLogicNode();

        rootNode.Add(childParentNode);

        LogicNode childNode1 = new LogicNode();
        LogicNode childNode2 = new LogicNode();

        childParentNode.Add(childNode1);
        childParentNode.Add(childNode2);

        rootNode.GetLayoutNode().UpdateLayout();

        rootNode.GetLayoutNode().LocalTransform.SetPositionX(50);

        assertTrue(rootNode.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childParentNode.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childNode1.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childNode2.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
    }
}
