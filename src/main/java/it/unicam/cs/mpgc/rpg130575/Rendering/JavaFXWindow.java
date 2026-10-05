package it.unicam.cs.mpgc.rpg130575.Rendering;

import javafx.stage.Stage;

public final class JavaFXWindow implements IWindow
{
    private final Stage _stage;

    private final static float _defaultWidth = 480;
    private final static float _defaultHeight = 270;

    public JavaFXWindow(Stage stage, String title)
    {
        this(stage, title, _defaultWidth, _defaultHeight);
    }

    public JavaFXWindow(Stage stage, String title, float width, float height)
    {
        _stage = stage;

        SetTitle(title);
        SetWidth(width);
        SetHeight(height);

        _stage.show();
    }

    // We want pixel size (int).
    public float GetWidth() { return (float)_stage.getWidth(); }
    public void SetWidth(float pixels) { _stage.setWidth(pixels); }

    public float GetHeight() { return (float)_stage.getHeight(); }
    public void SetHeight(float pixels) { _stage.setHeight(pixels); }

    public String GetTitle() { return _stage.getTitle(); }
    public void SetTitle(String title) { _stage.setTitle(title); }
}
