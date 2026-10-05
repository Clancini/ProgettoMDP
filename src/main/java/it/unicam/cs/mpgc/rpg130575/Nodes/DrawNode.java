package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;
import javafx.scene.paint.Color;

public class DrawNode
{
    private final LogicNode _source;

    public DrawNode(LogicNode source)
    {
        _source = source;
    }

    public void Draw(IRenderer renderer)
    {
        renderer.DrawQuad(_source.Transform, Color.WHITE);
    }
}
