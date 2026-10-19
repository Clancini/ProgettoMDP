package it.unicam.cs.mpgc.rpg130575.Types.Tweening;

import it.unicam.cs.mpgc.rpg130575.Types.Layout.Transform;

public class TransformPositionYTween extends Tween
{
    private final Transform _target;

    private final float _startValue;
    private final float _endValue;

    public TransformPositionYTween(Transform target, double duration, float endValue)
    {
        this(target, duration, target.GetPositionY(), endValue);
    }

    public TransformPositionYTween(Transform target, double duration, float startValue, float endValue)
    {
        super(duration);

        _target = target;

        if (startValue != _target.GetPositionY())
            _target.SetPositionY(startValue);

        _startValue = startValue;
        _endValue = endValue;
    }

    @Override
    protected void Update()
    {
        _target.SetPositionY(_startValue + (_endValue - _startValue) * Progress);
    }
}
