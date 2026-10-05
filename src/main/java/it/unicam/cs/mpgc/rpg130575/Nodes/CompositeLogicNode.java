package it.unicam.cs.mpgc.rpg130575.Nodes;

import java.util.List;
import java.util.Vector;

public class CompositeLogicNode extends LogicNode
{
    private final Vector<LogicNode> _children = new Vector<>();

    private boolean _ignoreSubtreeForInput;

    public void SetIgnoreSubtreeForInput(boolean value) { _ignoreSubtreeForInput = value; }

    @Override
    public final boolean JoinInputQueueIfNeeded(List<LogicNode> list)
    {
        if (_ignoreSubtreeForInput)
            return false;

        return super.JoinInputQueueIfNeeded(list);
    }

    public void Add(LogicNode node)
    {
        _children.add(node);

        node.SetParent(this);

        if (node.GetLoadState() == LogicNodeLoadState.NotLoaded)
            node.Load(GetDependencyContainer());
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

    @Override
    public void UpdateLayout()
    {
        super.UpdateLayout();

        for (LogicNode child : _children)
        {
            child.UpdateLayout();
        }
    }

    public void PreChildrenUpdate(double deltaSeconds) { }
    public void PostChildrenUpdate(double deltaSeconds) { }

    @Override
    public void CreateDrawNode() { DrawNode = new CompositeDrawNode(this, _children); }

    @Override
    public void InvalidateProperty(LogicNodeInvalidation property)
    {
        super.InvalidateProperty(property);

        InvalidatePropertyInChildren(property);
    }

    protected final void InvalidatePropertyInChildren(LogicNodeInvalidation property)
    {
        for (LogicNode child : _children)
        {
            child.InvalidateProperty(property);
        }
    }

    @Override
    public void SetWidth(float width)
    {
        InvalidatePropertyInChildren(LogicNodeInvalidation.Transform);

        super.SetWidth(width);
    }

    @Override
    public void SetHeight(float height)
    {
        InvalidatePropertyInChildren(LogicNodeInvalidation.Transform);

        super.SetHeight(height);
    }

    public void SetPositionX(float positionX)
    {
        InvalidatePropertyInChildren(LogicNodeInvalidation.Transform);

        super.SetPositionX(positionX);
    }

    public void SetPositionY(float positionY)
    {
        InvalidatePropertyInChildren(LogicNodeInvalidation.Transform);

        super.SetPositionY(positionY);
    }
}
