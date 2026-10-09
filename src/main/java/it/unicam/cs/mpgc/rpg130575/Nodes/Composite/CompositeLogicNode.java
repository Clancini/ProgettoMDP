package it.unicam.cs.mpgc.rpg130575.Nodes.Composite;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNodeLoadState;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CompositeLogicNode extends LogicNode
{
    private final ArrayList<LogicNode> _children = new ArrayList<>();

    private boolean _ignoreSubtreeForInput;

    public void Add(LogicNode node)
    {
        _children.add(node);

        node.GetLayoutNode().SetParent(this);

        if (node.GetLoadState() == LogicNodeLoadState.NotLoaded)
            node.Load(GetDependencyContainer());
    }

    @Override
    public final boolean JoinInputQueueIfNeeded(List<LogicNode> list)
    {
        if (!super.JoinInputQueueIfNeeded(list))
            return false;

        if (_ignoreSubtreeForInput)
            return false;

        for (LogicNode child : _children)
        {
            child.JoinInputQueueIfNeeded(list);
        }

        return true;
    }

    public void SetIgnoreSubtreeForInput(boolean value) { _ignoreSubtreeForInput = value; }

    @Override
    public final void Update(double deltaSeconds)
    {
        PreChildrenUpdate(deltaSeconds);

        for (LogicNode child : _children)
        {
            child.Update(deltaSeconds);
        }

        PostChildrenUpdate(deltaSeconds);
    }

    public void PreChildrenUpdate(double deltaSeconds) { }
    public void PostChildrenUpdate(double deltaSeconds) { }

    @Override
    public void CreateDrawNode() { SetDrawNode(new CompositeDrawNode(GetLayoutNode(), _children)); }
    @Override
    public void CreateLayoutNode() { SetLayoutNode(new CompositeLayoutNode(_children)); }
}
