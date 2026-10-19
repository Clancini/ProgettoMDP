package it.unicam.cs.mpgc.rpg130575.Types.Audio;

import java.util.HashMap;

public class JavaFXSampleStorage implements ISampleStorage
{
    private final HashMap<String, ISample> _samples = new HashMap<>();

    public ISample GetSample(String lookup) { return _samples.get(lookup); }

    public void AddSample(String resource, String lookup, boolean load)
    {
        JavaFXSample sample = new JavaFXSample(resource);

        if (load)
            sample.Load();

        _samples.put(lookup, sample);
    }
}
