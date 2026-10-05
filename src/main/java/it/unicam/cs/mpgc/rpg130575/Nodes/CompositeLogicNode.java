package it.unicam.cs.mpgc.rpg130575.Nodes;

import java.util.Vector;

public class CompositeLogicNode extends LogicNode
{
    private final Vector<LogicNode> _children = new Vector<>();

    public void Add(LogicNode node)
    {
        _children.add(node);

        node.SetParent(this);
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
    public void UpdateLayout()
    {
        super.UpdateLayout();

        for (LogicNode child : _children)
        {
            child.UpdateLayout();
        }
    }

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
