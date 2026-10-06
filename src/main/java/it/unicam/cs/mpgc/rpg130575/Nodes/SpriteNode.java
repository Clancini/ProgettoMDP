package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Types.INativeTexture;
import it.unicam.cs.mpgc.rpg130575.Types.INativeTextureStorage;
import it.unicam.cs.mpgc.rpg130575.Types.TextureInfo;

public class SpriteNode extends LogicNode
{
    private INativeTexture _nativeTexture;

    public void SetTexture(String textureLookup, boolean autoResize)
    {
        _nativeTexture = GetDependencyContainer()
                .Get(INativeTextureStorage.class)
                .GetTexture(textureLookup);

        TextureInfo info =_nativeTexture.GetTextureInfo();

        if (autoResize)
        {
            LocalTransform.SetWidth(info.GetWidth());
            LocalTransform.SetHeight(info.GetHeight());
        }
    }

    @Override
    public void CreateDrawNode() { DrawNode = new TextureDrawNode(this); }

    public final INativeTexture GetTexture() { return _nativeTexture; }
}
