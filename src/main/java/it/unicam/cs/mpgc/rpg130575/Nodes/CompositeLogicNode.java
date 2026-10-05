package it.unicam.cs.mpgc.rpg130575.Nodes;

import java.util.Vector;

public class CompositeLogicNode extends LogicNode
{
    private final Vector<LogicNode> _children = new Vector<>();

    public void Add(LogicNode node)
    {
        _children.add(node);
    }

    @Override
    public final void Update()
    {
        PreChildrenUpdate();

        for (LogicNode child : _children)
        {
            child.Update();
        }

        PostChildrenUpdate();
    }

    public void PreChildrenUpdate() { }
    public void PostChildrenUpdate() { }

    @Override
    public final CompositeDrawNode GetDrawNode() { return new CompositeDrawNode(this, _children); }
}
