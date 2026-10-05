package it.unicam.cs.mpgc.rpg130575.Hosts;

import it.unicam.cs.mpgc.rpg130575.Game.RightMovingRectangle;
import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;
import it.unicam.cs.mpgc.rpg130575.Rendering.IWindow;

public class GameHost
{
    private final IWindow _window;
    private final IRenderer _renderer;

    private RightMovingRectangle _node = new RightMovingRectangle();

    public GameHost(IWindow window, IRenderer renderer)
    {
        _window = window;
        _renderer = renderer;

        _node.Transform.SetHeight(100);
        _node.Transform.SetWidth(100);
    }

    public void Update()
    {
        _node.Update();

        _renderer.BeginFrame(_window.GetWidth(), _window.GetHeight());

        _node.GetDrawNode().Draw(_renderer);
    }
}
