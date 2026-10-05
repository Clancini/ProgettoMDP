package it.unicam.cs.mpgc.rpg130575.Types;

import javafx.scene.image.Image;

import java.io.InputStream;

public class JavaFXTexture implements IReadonlyTexture
{
    private final String _resource;

    private final float _width;
    private final float _height;

    private Image _image;

    public JavaFXTexture(String resource, float width, float height)
    {
        _resource = resource;

        _width = width;
        _height = height;
    }

    public String GetResource() { return _resource; }

    public float GetWidth() { return _width; }

    public float GetHeight() { return _height; }

    public Image GetImage()
    {
        if (_image == null)
            LoadImage();

        return _image;
    }

    public void LoadImage()
    {
        // TODO: try?
        InputStream stream = getClass().getResourceAsStream(_resource);

        if (stream == null)
            throw new NullPointerException("Resource doesn't exist at path: " + _resource);

        _image = new Image(stream);
    }
}
