package it.unicam.cs.mpgc.rpg130575.Types;

public interface INativeTextureStorage
{
    public INativeTexture GetTexture(String lookup);
    public void AddTexture(TextureInfo info, String lookup, boolean load);
}
