package it.unicam.cs.mpgc.rpg130575.Game.Behavior;

public class Clock implements IClock
{
    private double _time;
    private boolean _isRunning;

    public void Start()
    {
        _time = 0;
        _isRunning = true;
    }

    public void Stop()
    {
        _isRunning = false;
    }

    public void Update(double deltaSeconds)
    {
        if (_isRunning)
            _time += deltaSeconds;
    }

    public double GetTime()
    {
        return _time;
    }
}
