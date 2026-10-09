package it.unicam.cs.mpgc.rpg130575.Nodes.Base;

import it.unicam.cs.mpgc.rpg130575.Types.ILayoutChangeListener;
import it.unicam.cs.mpgc.rpg130575.Types.LayoutInvalidation;
import it.unicam.cs.mpgc.rpg130575.Types.Transform;

import java.util.EnumSet;

public class LayoutNode implements ILayoutChangeListener
{
    // We only care about invalidating ourselves when our local transform changes.
    public final Transform LocalTransform = new Transform(this);
    public final Transform WorldTransform = new Transform(null);

    private LogicNode _parent;

    // Start with all properties invalid.
    private final EnumSet<LayoutInvalidation> _invalidation = EnumSet.allOf(LayoutInvalidation.class);

    public final void SetParent(LogicNode parent) { _parent = parent; }
    public final LogicNode GetParent() { return _parent; }

    public void InvalidateProperty(LayoutInvalidation property) { _invalidation.add(property); }

    public void UpdateLayout()
    {
        if (_invalidation.contains(LayoutInvalidation.Position))
            UpdatePositions();

        if (_invalidation.contains(LayoutInvalidation.Sizing))
            UpdateSizing();
    }

    private void UpdatePositions()
    {
        _invalidation.remove(LayoutInvalidation.Position);

        if (_parent == null)
        {
            WorldTransform.SetPositionX(LocalTransform.GetPositionX());
            WorldTransform.SetPositionY(LocalTransform.GetPositionY());

            return;
        }

        WorldTransform.SetPositionX(LocalTransform.GetPositionX() + _parent.GetLayoutNode().WorldTransform.GetPositionX());
        WorldTransform.SetPositionY(LocalTransform.GetPositionY() + _parent.GetLayoutNode().WorldTransform.GetPositionY());
    }

    private void UpdateSizing()
    {
        _invalidation.remove(LayoutInvalidation.Sizing);

        if (_parent == null)
        {
            WorldTransform.SetWidth(LocalTransform.GetWidth());
            WorldTransform.SetHeight(LocalTransform.GetHeight());

            return;
        }

        WorldTransform.SetWidth(LocalTransform.GetWidth());
        WorldTransform.SetHeight(LocalTransform.GetHeight());
    }
}
