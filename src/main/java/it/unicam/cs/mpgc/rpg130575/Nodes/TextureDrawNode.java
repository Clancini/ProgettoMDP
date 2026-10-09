package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.DrawNode;
import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;

public class TextureDrawNode extends DrawNode
{
    private final SpriteNode _source;

    public TextureDrawNode(SpriteNode source)
    {
        _source = source;

        super(source.GetLayoutNode());
    }

    @Override
    public void Draw(IRenderer renderer)
    {
        renderer.DrawImage(_source.GetLayoutNode().WorldTransform, _source.GetTexture());
    }
}
