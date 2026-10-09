package it.unicam.cs.mpgc.rpg130575.Rendering;

import it.unicam.cs.mpgc.rpg130575.Types.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class JavaFXRenderer implements IRenderer
{
    private final GraphicsContext _context;

    public JavaFXRenderer(GraphicsContext context)
    {
        _context = context;
    }

    public Color ToJavaFXColor(CColor color)
    {
        return new Color(CColor.Clamped01(color.R),
                CColor.Clamped01(color.G),
                CColor.Clamped01(color.B),
                CColor.Clamped01(color.A));
    }

    public void BeginFrame(float windowWidth, float windowHeight)
    {
        _context.setFill(Color.BLACK);

        _context.fillRect(0, 0, windowWidth, windowHeight);
    }

    public void DrawQuad(Transform transform, CColor color)
    {
        _context.setFill(ToJavaFXColor(color));

        _context.fillRect(transform.GetPositionX(), transform.GetPositionY(),
                transform.GetWidth(), transform.GetHeight());
    }

    public void DrawImage(Transform transform, INativeTexture texture)
    {
        if (!(texture instanceof JavaFXTexture javaFXTexture))
            throw new IllegalStateException("Passed texture is not a JavaFXRenderer-compatible texture");

        _context.drawImage(javaFXTexture.GetImage(),
                transform.GetPositionX(),
                transform.GetPositionY(),
                transform.GetWidth(),
                transform.GetHeight());
    }
}
