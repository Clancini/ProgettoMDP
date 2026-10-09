package it.unicam.cs.mpgc.rpg130575.Types.Textures;

import javafx.scene.image.Image;

import java.io.InputStream;

public class JavaFXTexture implements INativeTexture
{
    private final TextureInfo _textureInfo;

    private Image _image;

    public JavaFXTexture(TextureInfo textureInfo)
    {
        _textureInfo = textureInfo;
    }

    public TextureInfo GetTextureInfo() { return _textureInfo; }

    public Image GetImage()
    {
        if (_image == null)
            Load();

        return _image;
    }

    public void Load()
    {
        // TODO: try?
        InputStream stream = getClass().getResourceAsStream(_textureInfo.GetResource());

        if (stream == null)
            throw new NullPointerException("Resource doesn't exist at path: " + _textureInfo.GetResource());

        _image = new Image(stream);
    }
}
