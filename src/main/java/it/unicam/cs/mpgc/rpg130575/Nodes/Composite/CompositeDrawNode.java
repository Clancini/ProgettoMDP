package it.unicam.cs.mpgc.rpg130575.Nodes.Composite;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.DrawNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LayoutNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.Node;
import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;

import java.util.ArrayList;

/// A component which handles rendering of sub-DrawNodes in containers.
public class CompositeDrawNode extends DrawNode
{
    private final ArrayList<Node> _children;

    public CompositeDrawNode(LayoutNode source, ArrayList<Node> children)
    {
        _children = children;

        super(source);
    }

    @Override
    public final void Draw(IRenderer renderer)
    {
        for (Node child : _children)
        {
            child.GetDrawNode().PreDraw(renderer);
        }
    }
}
