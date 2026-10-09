package it.unicam.cs.mpgc.rpg130575.Nodes.Base;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.DependencyInjection.IReadOnlyDependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;

import java.util.List;

public class LogicNode
{
    private DrawNode DrawNode;
    private LayoutNode LayoutNode;

    private LogicNodeLoadState _loadState = LogicNodeLoadState.NotLoaded;

    private IReadOnlyDependencyContainer _container = DependencyContainer.Empty;

    private boolean _acceptsInput = true;

    public final DrawNode GetDrawNode()
    {
        if (DrawNode == null)
            CreateDrawNode();

        return DrawNode;
    }
    protected final void SetDrawNode(DrawNode drawNode) { DrawNode = drawNode; }

    public final LayoutNode GetLayoutNode()
    {
        if (LayoutNode == null)
            CreateLayoutNode();

        return LayoutNode;
    }
    public final void SetLayoutNode(LayoutNode layoutNode) { LayoutNode = layoutNode; }

    public final LogicNodeLoadState GetLoadState() { return _loadState; }

    protected final IReadOnlyDependencyContainer GetDependencyContainer() { return _container; }

    public void SetAcceptsInput(boolean value) { _acceptsInput = value; }

    public boolean JoinInputQueueIfNeeded(List<LogicNode> inputs)
    {
        if (_acceptsInput)
            inputs.add(this);

        return true;
    }

    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event) { return false; }

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

    protected IReadOnlyDependencyContainer CreateChildDependencies(IReadOnlyDependencyContainer parentDependencies) { return parentDependencies; }

    protected void OnLoad() { }

    public void Update(double deltaSeconds) { }

    public void CreateDrawNode() { DrawNode = new DrawNode(GetLayoutNode()); }
    public void CreateLayoutNode() { LayoutNode = new LayoutNode(); }
}
