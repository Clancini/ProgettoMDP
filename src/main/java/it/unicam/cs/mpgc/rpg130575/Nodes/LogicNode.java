package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.DependencyInjection.IReadOnlyDependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;

import java.util.EnumSet;
import java.util.List;

public class LogicNode
{
    protected DrawNode DrawNode;

    private LogicNode _parent;

    public final Transform LocalTransform = new Transform();
    public final Transform WorldTransform = new Transform();

    // Start with all properties invalid.
    private EnumSet<LogicNodeInvalidation> _invlidation = EnumSet.allOf(LogicNodeInvalidation.class);

    private LogicNodeLoadState _loadState = LogicNodeLoadState.NotLoaded;

    private IReadOnlyDependencyContainer _container = DependencyContainer.Empty;

    private boolean _acceptsInput = true;

    public void SetAcceptsInput(boolean value) { _acceptsInput = value; }

    public boolean JoinInputQueueIfNeeded(List<LogicNode> inputs)
    {
        if (_acceptsInput)
            inputs.add(this);

        return true;
    }

    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event) { return false; }

    public final LogicNodeLoadState GetLoadState() { return _loadState; }

    protected final IReadOnlyDependencyContainer GetDependencyContainer() { return _container; }

    public final void Load(IReadOnlyDependencyContainer parentDependencies)
    {
        if (_loadState != LogicNodeLoadState.NotLoaded)
            throw new IllegalStateException("Cannot load a LogicNode which is already loaded or loading");

        _loadState = LogicNodeLoadState.Loading;

        // TODO: Decide ordering between these two.
        _container = CreateChildDependencies(parentDependencies);
        OnLoad();

        _loadState = LogicNodeLoadState.Loaded;
    }

    protected void OnLoad() { }

    protected IReadOnlyDependencyContainer CreateChildDependencies(IReadOnlyDependencyContainer parentDependencies) { return parentDependencies; }

    public void SetParent(LogicNode parent) { _parent = parent; }
    public LogicNode GetParent() { return _parent; }

    public void InvalidateProperty(LogicNodeInvalidation property) { _invlidation.add(property); }

    public void Update(double deltaSeconds) { }

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
