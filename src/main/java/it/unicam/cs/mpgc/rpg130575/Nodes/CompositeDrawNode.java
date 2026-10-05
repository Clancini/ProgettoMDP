package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;

import java.util.Vector;

public class CompositeDrawNode extends DrawNode
{
    private final Vector<LogicNode> _children;

    public CompositeDrawNode(CompositeLogicNode source, Vector<LogicNode> children)
    {
        super(source);

        _children = children;
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
