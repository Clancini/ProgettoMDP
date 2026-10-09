package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.DependencyInjection.IReadOnlyDependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKey;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LayoutNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TransformPositionXTween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.Tween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TweenManager;


public class Game extends CompositeLogicNode
{
    private final TweenManager _tweenManager = new TweenManager();

    private LogicNode _currentNode;
    private Tween _currentTween;
    private int _direction;

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

        _currentNode = node;
    }

    @Override
    public void PostChildrenUpdate(double deltaSeconds)
    {
        _tweenManager.Update(deltaSeconds);
    }

    @Override
    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event)
    {
        if (event.Key == CKeyboardKey.Q)
        {
            if (_direction == 1)
                return true;

            _direction = 1;

            double time;

            if (_currentTween != null)
            {
                _currentTween.Cancel();

                time = 2d * _currentTween.GetProgress();
            }
            else
            {
                time = 2d;
            }

            _currentTween = new TransformPositionXTween(_currentNode.GetLayoutNode().LocalTransform, time, 100);
            _tweenManager.AddTween(_currentTween);
        }
        if (event.Key == CKeyboardKey.W)
        {
            if (_direction == -1)
                return true;

            _direction = -1;

            double time;

            if (_currentTween != null)
            {
                _currentTween.Cancel();

                time = 2d * _currentTween.GetProgress();
            }
            else
            {
                time = 2d;
            }

            _currentTween = new TransformPositionXTween(_currentNode.GetLayoutNode().LocalTransform, time, 0);
            _tweenManager.AddTween(_currentTween);
        }

        return false;
    }
}
