package it.unicam.cs.mpgc.rpg130575.Hosts;

import it.unicam.cs.mpgc.rpg130575.Nodes.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;
import it.unicam.cs.mpgc.rpg130575.Rendering.IWindow;

public class GameHost
{
    private final IWindow _window;
    private final IRenderer _renderer;

    private final CompositeLogicNode _rootLogicNode;

    public GameHost(IWindow window, IRenderer renderer, CompositeLogicNode game)
    {
        _window = window;
        _renderer = renderer;

        _rootLogicNode = game;
    }

    public void Update()
    {
        _rootLogicNode.Update();

        _renderer.BeginFrame(_window.GetWidth(), _window.GetHeight());

        _rootLogicNode.UpdateLayout();
        _rootLogicNode.GetDrawNode().Draw(_renderer);
    }
}
