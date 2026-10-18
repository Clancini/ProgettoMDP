package it.unicam.cs.mpgc.rpg130575.Rendering;

import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public final class JavaFXWindow implements IWindow
{
    private final Stage _stage;
    private final Canvas _canvas;

    public final static float DefaultWidth = 480;
    public final static float DefaultHeight = 270;

    public JavaFXWindow(Stage stage, Canvas canvas, String title)
    {
        this(stage, canvas, title, DefaultWidth, DefaultHeight);
    }

    public JavaFXWindow(Stage stage, Canvas canvas, String title, float width, float height)
    {
        _stage = stage;
        _canvas = canvas;

        SetTitle(title);
        SetWidth(width);
        SetHeight(height);

        _stage.show();
    }

    // We want pixel size (int).
    public float GetWidth() { return (float)_stage.getWidth(); }
    public void SetWidth(float pixels)
    {
        _stage.setWidth(pixels);
        _canvas.setWidth(pixels);
    }

    public float GetHeight() { return (float)_stage.getHeight(); }
    public void SetHeight(float pixels)
    {
        _stage.setHeight(pixels);
        _canvas.setHeight(pixels);
    }

    public String GetTitle() { return _stage.getTitle(); }
    public void SetTitle(String title) { _stage.setTitle(title); }
}
