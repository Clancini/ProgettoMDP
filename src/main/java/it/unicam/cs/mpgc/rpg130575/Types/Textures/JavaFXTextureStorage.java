package it.unicam.cs.mpgc.rpg130575.Types.Textures;

import java.util.HashMap;

public class JavaFXTextureStorage implements INativeTextureStorage
{
    private final HashMap<String, JavaFXTexture> _textures = new HashMap<>();

    public INativeTexture GetTexture(String lookup) { return _textures.get(lookup); }

    public void AddTexture(TextureInfo info, String lookup, boolean load)
    {
        JavaFXTexture texture = new JavaFXTexture(info);

        if (load)
            texture.Load();

        _textures.put(lookup, texture);
    }
}
