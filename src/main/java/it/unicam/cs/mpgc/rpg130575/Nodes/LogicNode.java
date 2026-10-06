package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.DependencyInjection.IReadOnlyDependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;
import it.unicam.cs.mpgc.rpg130575.Types.ILayoutChangeListener;
import it.unicam.cs.mpgc.rpg130575.Types.LayoutInvalidation;
import it.unicam.cs.mpgc.rpg130575.Types.Transform;

import java.util.EnumSet;
import java.util.List;

public class LogicNode implements ILayoutChangeListener
{
    protected DrawNode DrawNode;

    private LogicNode _parent;

    // We only care about invalidating ourselves when our local transform changes.
    public final Transform LocalTransform = new Transform(this);
    public final Transform WorldTransform = new Transform(null);

    // Start with all properties invalid.
    private final EnumSet<LayoutInvalidation> _invalidation = EnumSet.allOf(LayoutInvalidation.class);

    private LogicNodeLoadState _loadState = LogicNodeLoadState.NotLoaded;

    private IReadOnlyDependencyContainer _container = DependencyContainer.Empty;

    private boolean _acceptsInput = true;

    // REGION Input handling

    public void SetAcceptsInput(boolean value) { _acceptsInput = value; }

    public boolean JoinInputQueueIfNeeded(List<LogicNode> inputs)
    {
        if (_acceptsInput)
            inputs.add(this);

        return true;
    }

    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event) { return false; }

    // REGION Loading

    public final LogicNodeLoadState GetLoadState() { return _loadState; }

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

    // REGION Dependency Injection

    protected final IReadOnlyDependencyContainer GetDependencyContainer() { return _container; }

    protected IReadOnlyDependencyContainer CreateChildDependencies(IReadOnlyDependencyContainer parentDependencies) { return parentDependencies; }

    // REGION Parenting

    public void SetParent(LogicNode parent) { _parent = parent; }
    public LogicNode GetParent() { return _parent; }

    // REGION Layout

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

        WorldTransform.SetPositionX(LocalTransform.GetPositionX() + _parent.WorldTransform.GetPositionX());
        WorldTransform.SetPositionY(LocalTransform.GetPositionY() + _parent.WorldTransform.GetPositionY());
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

    public void Update(double deltaSeconds) { }

    // REGION Rendering

    public void CreateDrawNode() { DrawNode = new DrawNode(this); }

    public DrawNode GetDrawNode()
    {
        if (DrawNode == null)
            CreateDrawNode();

        return DrawNode;
    }
}
