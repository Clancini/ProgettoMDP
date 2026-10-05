package it.unicam.cs.mpgc.rpg130575.Hosts;

import it.unicam.cs.mpgc.rpg130575.Nodes.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;
import it.unicam.cs.mpgc.rpg130575.Rendering.IWindow;

public class GameHost
{
    private final IWindow _window;
    private final IRenderer _renderer;

    private final CompositeLogicNode _rootLogicNode;

    private long _lastTime;

    public GameHost(IWindow window, IRenderer renderer, CompositeLogicNode game)
    {
        _window = window;
        _renderer = renderer;

        _rootLogicNode = game;
    }

    public void Update(long time)
    {
        if (_lastTime == 0)
        {
            _lastTime = time;
            return;
        }

        long delta = time - _lastTime;
        _lastTime = time;

        double deltaSeconds = delta / 1_000_000_000f;

        _rootLogicNode.Update(deltaSeconds);

        _renderer.BeginFrame(_window.GetWidth(), _window.GetHeight());

        _rootLogicNode.UpdateLayout();
        _rootLogicNode.GetDrawNode().Draw(_renderer);
    }
}
