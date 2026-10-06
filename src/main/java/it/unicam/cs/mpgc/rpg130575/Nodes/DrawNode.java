package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;
import it.unicam.cs.mpgc.rpg130575.Types.CColor;

public class DrawNode
{
    private final LogicNode _source;

    private boolean _isVisible = true;

    public DrawNode(LogicNode source)
    {
        _source = source;
    }

    public boolean GetIsVisible() { return _isVisible; }
    public void SetIsVisible(boolean value) { _isVisible = value; }

    public final void PreDraw(IRenderer renderer)
    {
        if (!_isVisible)
            return;

        Draw(renderer);
    }

    protected void Draw(IRenderer renderer)
    {
        renderer.DrawQuad(_source.WorldTransform, new CColor(255, 255, 255));
    }
}
