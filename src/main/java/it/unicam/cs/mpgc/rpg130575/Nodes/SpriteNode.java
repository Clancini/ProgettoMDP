package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Types.IReadonlyTexture;

public class SpriteNode extends LogicNode
{
    private IReadonlyTexture _texture;

    public void SetTexture(IReadonlyTexture texture, boolean autoResize)
    {
        _texture = texture;

        if (autoResize)
        {
            LocalTransform.SetWidth(_texture.GetWidth());
            LocalTransform.SetHeight(_texture.GetHeight());
        }
    }

    @Override
    public void CreateDrawNode() { DrawNode = new TextureDrawNode(this); }

    public final IReadonlyTexture GetTexture() { return _texture; }
}
