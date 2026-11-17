package it.unicam.cs.mpgc.rpg130575.Nodes.Base;

import it.unicam.cs.mpgc.rpg130575.Types.Layout.ILayoutChangeListener;
import it.unicam.cs.mpgc.rpg130575.Types.Layout.LayoutInvalidation;
import it.unicam.cs.mpgc.rpg130575.Types.Layout.Transform;

import java.util.EnumSet;

/** A component used to attach layout information to a Node.<br/>
 * <br/>
 * Layout is handled using 2 Transforms, 1 in local space and 1 in global (world) space.<br/>
 * Local space is relative to an eventual parent container, while global space is relative to the screen.<br/>
 * <br/>
 * Layout includes X and Y positioning, scale, height and width.<br/>
 * Updating a container's layout will invalidate itself and its children, causing them to be updated accordingly.
 */
public class LayoutNode implements ILayoutChangeListener
{
    // We only care about invalidating ourselves when our local transform changes.
    public final Transform LocalTransform = new Transform(this);
    public final Transform WorldTransform = new Transform(null);

    private Node _parent;

    // Start with all properties invalid.
    private final EnumSet<LayoutInvalidation> _invalidation = EnumSet.allOf(LayoutInvalidation.class);

    public final void SetParent(Node parent) { _parent = parent; }
    public final Node GetParent() { return _parent; }

    public boolean HasLayoutInvalidation(EnumSet<LayoutInvalidation> invalidations)
    {
        if (invalidations.size() != _invalidation.size())
            return false;

        return _invalidation.containsAll(invalidations);
    }
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

        Transform parentTransform = _parent.GetLayoutNode().WorldTransform;

        WorldTransform.SetPositionX((LocalTransform.GetPositionX() * parentTransform.GetScale()) + parentTransform.GetPositionX());
        WorldTransform.SetPositionY((LocalTransform.GetPositionY() * parentTransform.GetScale()) + parentTransform.GetPositionY());
    }

    private void UpdateSizing()
    {
        _invalidation.remove(LayoutInvalidation.Sizing);

        if (_parent == null)
        {
            WorldTransform.SetWidth(LocalTransform.GetWidth() * LocalTransform.GetScale());
            WorldTransform.SetHeight(LocalTransform.GetHeight() * LocalTransform.GetScale());

            WorldTransform.SetScale(LocalTransform.GetScale());

            return;
        }

        float totalScale = LocalTransform.GetScale() * _parent.GetLayoutNode().WorldTransform.GetScale();

        WorldTransform.SetScale(totalScale);

        WorldTransform.SetWidth(LocalTransform.GetWidth() * totalScale);
        WorldTransform.SetHeight(LocalTransform.GetHeight() * totalScale);
    }
}
