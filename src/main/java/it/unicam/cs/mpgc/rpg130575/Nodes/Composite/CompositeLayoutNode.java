package it.unicam.cs.mpgc.rpg130575.Nodes.Composite;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LayoutNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Types.LayoutInvalidation;

import java.util.ArrayList;

public class CompositeLayoutNode extends LayoutNode
{
    // Reuse the main list. Sharing a reference means we don't have to update this one individually.
    private final ArrayList<LogicNode> _children;

    public CompositeLayoutNode(ArrayList<LogicNode> children)
    {
        _children = children;
    }

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
            child.GetLayoutNode().InvalidateProperty(property);
        }
    }

    @Override
    public void UpdateLayout()
    {
        super.UpdateLayout();

        for (LogicNode child : _children)
        {
            child.GetLayoutNode().UpdateLayout();
        }
    }
}
