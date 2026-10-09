package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.DependencyInjection.IReadOnlyDependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LayoutNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TransformPositionXTween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TweenManager;


public class Game extends CompositeLogicNode
{
    private final TweenManager _tweenManager = new TweenManager();

    public Game()
    {

    }

    @Override
    protected IReadOnlyDependencyContainer CreateChildDependencies(IReadOnlyDependencyContainer container)
    {
        DependencyContainer newDeps = new DependencyContainer(container);

        newDeps.Cache(TweenManager.class, _tweenManager);

        return newDeps;
    }

    @Override
    public void OnLoad()
    {
        LogicNode node = new LogicNode();
        LayoutNode nodeLayout = node.GetLayoutNode();

        nodeLayout.LocalTransform.SetWidth(50);
        nodeLayout.LocalTransform.SetHeight(50);

        Add(node);

        _tweenManager.AddTween(new TransformPositionXTween(nodeLayout.LocalTransform, 5d, 100));
    }

    @Override
    public void PostChildrenUpdate(double deltaSeconds)
    {
        _tweenManager.Update(deltaSeconds);
    }
}
