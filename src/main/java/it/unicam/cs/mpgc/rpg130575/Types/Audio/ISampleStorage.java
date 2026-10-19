package it.unicam.cs.mpgc.rpg130575.Types.Audio;

public interface ISampleStorage
{
    public ISample GetSample(String lookup);
    public void AddSample(String resource, String lookup, boolean load);
}
