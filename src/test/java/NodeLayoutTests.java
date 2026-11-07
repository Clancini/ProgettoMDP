import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.Node;
import it.unicam.cs.mpgc.rpg130575.Types.Layout.LayoutInvalidation;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeLayoutTests
{
    @Test
    public void LayoutUpdatesArePropagated()
    {
        CompositeNode rootNode = new CompositeNode();

        Node childNode1 = new Node();
        Node childNode2 = new Node();

        rootNode.Add(childNode1);
        rootNode.Add(childNode2);

        rootNode.Update(0);

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
        CompositeNode rootNode = new CompositeNode();

        Node childNode1 = new Node();
        Node childNode2 = new Node();

        rootNode.Add(childNode1);
        rootNode.Add(childNode2);

        rootNode.Update(0);

        rootNode.GetLayoutNode().UpdateLayout();

        rootNode.GetLayoutNode().LocalTransform.SetPositionX(50);

        assertTrue(rootNode.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childNode1.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childNode2.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
    }

    @Test
    public void LayoutInvalidationArePropagatedToParents()
    {
        CompositeNode rootNode = new CompositeNode();
        CompositeNode childParentNode = new CompositeNode();

        rootNode.Add(childParentNode);

        Node childNode1 = new Node();
        Node childNode2 = new Node();

        childParentNode.Add(childNode1);
        childParentNode.Add(childNode2);

        rootNode.Update(0);

        rootNode.GetLayoutNode().UpdateLayout();

        rootNode.GetLayoutNode().LocalTransform.SetPositionX(50);

        assertTrue(rootNode.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childParentNode.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childNode1.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
        assertTrue(childNode2.GetLayoutNode().HasLayoutInvalidation(EnumSet.of(LayoutInvalidation.Position)));
    }
}
