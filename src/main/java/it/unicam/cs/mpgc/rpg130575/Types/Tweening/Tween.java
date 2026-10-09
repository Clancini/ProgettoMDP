package it.unicam.cs.mpgc.rpg130575.Types.Tweening;

public abstract class Tween
{
    private final double _duration;
    private double _passedTime;

    protected float Progress;

    private boolean _cancelled;

    public final float GetProgress() { return Progress; }

    public final boolean GetIsCancelled() { return _cancelled; }

    public Tween(double duration)
    {
        _duration = duration;
    }

    public final void TickForward(double deltaSeconds)
    {
        if (_passedTime >= _duration)
            return;

        _passedTime += deltaSeconds;

        Progress = (float)(_passedTime / _duration);

        if (Progress > 1.0f)
            Progress = 1.0f;

        Update();
    }

    protected abstract void Update();

    public final boolean IsDone() { return Progress >= 1.0f; }

    public final void Cancel()
    {
        _passedTime = _duration;

        _cancelled = true;
    }
}
