package it.unicam.cs.mpgc.rpg130575.Rendering;

import it.unicam.cs.mpgc.rpg130575.Nodes.Transform;
import it.unicam.cs.mpgc.rpg130575.Types.CColor;

public interface IRenderer
{
    public void BeginFrame(float windowWidth, float windowHeight);

    public void DrawQuad(Transform transform, CColor color);
}
