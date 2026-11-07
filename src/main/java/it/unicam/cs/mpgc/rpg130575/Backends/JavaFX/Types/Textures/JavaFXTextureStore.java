package it.unicam.cs.mpgc.rpg130575.Backends.JavaFX.Types.Textures;

import it.unicam.cs.mpgc.rpg130575.Types.Textures.INativeTexture;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.INativeTextureStorage;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.TextureInfo;

import java.util.HashMap;

public class JavaFXTextureStore implements INativeTextureStorage
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
