package it.unicam.cs.mpgc.rpg130575.Nodes;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.Node;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.INativeTexture;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.INativeTextureStore;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.TextureInfo;

public class SpriteNode extends Node
{
    private INativeTexture _nativeTexture;

    public void SetTexture(String textureLookup, boolean autoResize)
    {
        _nativeTexture = GetDependencyContainer()
                .Get(INativeTextureStore.class)
                .GetTexture(textureLookup);

        TextureInfo info =_nativeTexture.GetTextureInfo();

        if (autoResize)
        {
            GetLayoutNode().LocalTransform.SetWidth(info.GetWidth());
            GetLayoutNode().LocalTransform.SetHeight(info.GetHeight());
        }
    }

    @Override
    public void CreateDrawNode() { SetDrawNode(new TextureDrawNode(this)); }

    public final INativeTexture GetTexture() { return _nativeTexture; }
}
