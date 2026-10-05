package it.unicam.cs.mpgc.rpg130575.Nodes;

import java.util.EnumSet;

public class LogicNode
{
    protected DrawNode DrawNode;

    private LogicNode _parent;

    public final Transform LocalTransform = new Transform();
    public final Transform WorldTransform = new Transform();

    // Start with all properties invalid.
    private EnumSet<LogicNodeInvalidation> _invlidation = EnumSet.allOf(LogicNodeInvalidation.class);

    public void Update() { }

    public void InvalidateProperty(LogicNodeInvalidation property) { _invlidation.add(property); }

    public void UpdateLayout()
    {
        if (_invlidation.contains(LogicNodeInvalidation.Transform))
            UpdateTransforms();
    }

    private void UpdateTransforms()
    {
        _invlidation.remove(LogicNodeInvalidation.Transform);

        if (_parent == null)
        {
            WorldTransform.SetPositionX(LocalTransform.GetPositionX());
            WorldTransform.SetPositionY(LocalTransform.GetPositionY());

            WorldTransform.SetWidth(LocalTransform.GetWidth());
            WorldTransform.SetHeight(LocalTransform.GetHeight());

            return;
        }

        WorldTransform.SetPositionX(LocalTransform.GetPositionX() + _parent.WorldTransform.GetPositionX());
        WorldTransform.SetPositionY(LocalTransform.GetPositionY() + _parent.WorldTransform.GetPositionY());

        WorldTransform.SetWidth(LocalTransform.GetWidth());
        WorldTransform.SetHeight(LocalTransform.GetHeight());
    }

    public void CreateDrawNode() { DrawNode = new DrawNode(this); }
    public DrawNode GetDrawNode()
    {
        if (DrawNode == null)
            CreateDrawNode();

        return DrawNode;
    }

    public void SetParent(LogicNode parent) { _parent = parent; }
    public LogicNode GetParent() { return _parent; }

    public void SetWidth(float width)
    {
        _invlidation.add(LogicNodeInvalidation.Transform);

        LocalTransform.SetWidth(width);
    }

    public void SetHeight(float height)
    {
        _invlidation.add(LogicNodeInvalidation.Transform);

        LocalTransform.SetHeight(height);
    }

    public void SetPositionX(float positionX)
    {
        _invlidation.add(LogicNodeInvalidation.Transform);

        LocalTransform.SetPositionX(positionX);
    }

    public void SetPositionY(float positionY)
    {
        _invlidation.add(LogicNodeInvalidation.Transform);

        LocalTransform.SetPositionY(positionY);
    }
}
