package it.unicam.cs.mpgc.rpg130575.Nodes.Composite;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.DrawNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LayoutNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;

import java.util.ArrayList;
import java.util.Vector;

public class CompositeDrawNode extends DrawNode
{
    private final ArrayList<LogicNode> _children;

    public CompositeDrawNode(LayoutNode source, ArrayList<LogicNode> children)
    {
        _children = children;

        super(source);
    }

    @Override
    public final void Draw(IRenderer renderer)
    {
        for (LogicNode child : _children)
        {
            child.GetDrawNode().Draw(renderer);
        }
    }
}
