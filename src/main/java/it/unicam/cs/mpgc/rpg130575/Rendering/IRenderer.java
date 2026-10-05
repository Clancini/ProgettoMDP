package it.unicam.cs.mpgc.rpg130575.Rendering;

import it.unicam.cs.mpgc.rpg130575.Nodes.Transform;
import it.unicam.cs.mpgc.rpg130575.Types.CColor;
import it.unicam.cs.mpgc.rpg130575.Types.IReadonlyTexture;

public interface IRenderer
{
    public void BeginFrame(float windowWidth, float windowHeight);

    public void DrawQuad(Transform transform, CColor color);

    public void DrawImage(Transform transform, IReadonlyTexture texture);
}
