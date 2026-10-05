package it.unicam.cs.mpgc.rpg130575.Rendering;

import it.unicam.cs.mpgc.rpg130575.Nodes.Transform;
import javafx.scene.paint.Color;

public interface IRenderer
{
    public void BeginFrame(float windowWidth, float windowHeight);

    public void DrawQuad(Transform transform, Color color);
}
