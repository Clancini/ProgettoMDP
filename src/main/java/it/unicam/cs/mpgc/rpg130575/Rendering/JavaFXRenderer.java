package it.unicam.cs.mpgc.rpg130575.Rendering;

import it.unicam.cs.mpgc.rpg130575.Nodes.Transform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class JavaFXRenderer implements IRenderer
{
    private final GraphicsContext _context;

    public JavaFXRenderer(GraphicsContext context)
    {
        _context = context;
    }

    @Override
    public void BeginFrame(float windowWidth, float windowHeight)
    {
        _context.setFill(Color.BLACK);

        _context.fillRect(0, 0, windowWidth, windowHeight);
    }

    public void DrawQuad(Transform transform, Color color)
    {
        _context.setFill(color);

        _context.fillRect(transform.GetPositionX(), transform.GetPositionY(),
                transform.GetWidth(), transform.GetHeight());
    }
}
