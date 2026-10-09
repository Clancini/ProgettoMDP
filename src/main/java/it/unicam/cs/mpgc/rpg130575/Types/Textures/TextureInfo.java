package it.unicam.cs.mpgc.rpg130575.Types.Textures;

public class TextureInfo
{
    private final String _resource;

    private final float _width;
    private final float _height;

    public TextureInfo(String resource, float width, float height)
    {
        _resource = resource;

        _width = width;
        _height = height;
    }

    public String GetResource() { return _resource; }

    public float GetWidth() { return _width; }

    public float GetHeight() { return _height; }
}
