package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Rendering.IRenderer;

public class TextureDrawNode extends DrawNode
{
    private final SpriteNode _source;

    public TextureDrawNode(SpriteNode source)
    {
        _source = source;

        super(source);
    }

    @Override
    public void Draw(IRenderer renderer)
    {
        renderer.DrawImage(_source.WorldTransform, _source.GetTexture());
    }
}
