package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Types.IReadonlyTexture;

public class SpriteNode extends LogicNode
{
    private IReadonlyTexture _texture;

    public void SetTexture(IReadonlyTexture texture) { _texture = texture; }

    @Override
    public void CreateDrawNode() { DrawNode = new TextureDrawNode(this); }

    public final IReadonlyTexture GetTexture() { return _texture; }
}
