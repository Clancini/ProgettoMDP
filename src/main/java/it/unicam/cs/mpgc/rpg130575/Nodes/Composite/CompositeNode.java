package it.unicam.cs.mpgc.rpg130575.Nodes.Composite;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.Node;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNodeLoadState;

import java.util.ArrayList;
import java.util.List;

public class CompositeNode extends Node
{
    private final ArrayList<Node> _children = new ArrayList<>();
    private final ArrayList<Node> _pendingChildrenAdd = new ArrayList<>();

    private boolean _ignoreSubtreeForInput;

    public void Add(Node node)
    {
        _pendingChildrenAdd.add(node);

        node.GetLayoutNode().SetParent(this);

        if (node.GetLoadState() == LogicNodeLoadState.NotLoaded)
            node.Load(GetDependencyContainer());
    }

    @Override
    public final boolean JoinInputQueueIfNeeded(List<Node> list)
    {
        if (!super.JoinInputQueueIfNeeded(list))
            return false;

        if (_ignoreSubtreeForInput)
            return false;

        for (Node child : _children)
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

        _children.addAll(_pendingChildrenAdd);
        _pendingChildrenAdd.clear();

        for (Node child : _children)
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
