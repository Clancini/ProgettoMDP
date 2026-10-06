package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Types.LayoutInvalidation;

import java.util.List;
import java.util.Vector;

public class CompositeLogicNode extends LogicNode
{
    private final Vector<LogicNode> _children = new Vector<>();

    private boolean _ignoreSubtreeForInput;

    // REGION Input

    public void SetIgnoreSubtreeForInput(boolean value) { _ignoreSubtreeForInput = value; }

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

    // REGION Parenting

    public void Add(LogicNode node)
    {
        _children.add(node);

        node.SetParent(this);

        if (node.GetLoadState() == LogicNodeLoadState.NotLoaded)
            node.Load(GetDependencyContainer());
    }

    // REGION Layout

    @Override
    public void InvalidateProperty(LayoutInvalidation property)
    {
        super.InvalidateProperty(property);

        InvalidatePropertyInChildren(property);
    }

    protected final void InvalidatePropertyInChildren(LayoutInvalidation property)
    {
        for (LogicNode child : _children)
        {
            child.InvalidateProperty(property);
        }
    }

    @Override
    public void UpdateLayout()
    {
        super.UpdateLayout();

        for (LogicNode child : _children)
        {
            child.UpdateLayout();
        }
    }

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

    // REGION Rendering

    @Override
    public void CreateDrawNode() { DrawNode = new CompositeDrawNode(this, _children); }
}
