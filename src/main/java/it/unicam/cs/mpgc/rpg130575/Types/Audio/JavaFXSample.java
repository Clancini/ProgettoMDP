package it.unicam.cs.mpgc.rpg130575.Types.Audio;

import javafx.scene.media.AudioClip;

public class JavaFXSample implements ISample
{
    private final String _resource;

    private AudioClip _clip;

    public JavaFXSample(String resource)
    {
        _resource = resource;
    }

    public String GetResource() { return _resource; }

    public void Load()
    {
        if (_clip != null)
            return;

        _clip = new AudioClip(
                getClass()
                .getResource(_resource)
                .toExternalForm());
    }

    public void Play()
    {
        if (_clip == null)
            Load();

        _clip.play();
    }
}
