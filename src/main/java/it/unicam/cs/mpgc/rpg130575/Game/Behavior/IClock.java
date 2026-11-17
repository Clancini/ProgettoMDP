package it.unicam.cs.mpgc.rpg130575.Game.Behavior;

public interface IClock
{
    public void Start();

    public void Update(double deltaSeconds);

    public double GetTime();
}
