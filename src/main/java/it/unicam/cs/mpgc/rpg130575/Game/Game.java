package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.DependencyInjection.IReadOnlyDependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Screens.ScreenStack;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TweenManager;

public class Game extends CompositeNode
{
    private final TweenManager _tweenManager = new TweenManager();
    private final ScreenStack _screenStack;

    public Game()
    {
        _screenStack = new ScreenStack();

        Add(_screenStack);
    }

    @Override
    protected IReadOnlyDependencyContainer CreateChildDependencies(IReadOnlyDependencyContainer parent)
    {
        DependencyContainer dependencyContainer = new DependencyContainer(parent);

        dependencyContainer.Cache(TweenManager.class, _tweenManager);
        dependencyContainer.Cache(ScreenStack.class, _screenStack);

        return dependencyContainer;
    }
}
