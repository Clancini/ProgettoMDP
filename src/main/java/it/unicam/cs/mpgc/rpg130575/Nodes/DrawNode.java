package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;
import it.unicam.cs.mpgc.rpg130575.Types.CColor;

public class DrawNode
{
    private final LogicNode _source;

    public DrawNode(LogicNode source)
    {
        _source = source;
    }

    public void Draw(IRenderer renderer)
    {
        renderer.DrawQuad(_source.WorldTransform, new CColor(255, 255, 255));
    }
}
