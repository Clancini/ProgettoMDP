package it.unicam.cs.mpgc.rpg130575.Types.Tweening;

import it.unicam.cs.mpgc.rpg130575.Types.Layout.Transform;

public class TransformHeightTween extends Tween
{
    private final Transform _target;

    private final float _startValue;
    private final float _endValue;

    public TransformHeightTween(Transform target, double duration, float endValue)
    {
        this(target, duration, target.GetHeight(), endValue);
    }

    public TransformHeightTween(Transform target, double duration, float startValue, float endValue)
    {
        super(duration);

        _target = target;

        if (startValue != _target.GetHeight())
            _target.SetHeight(startValue);

        _startValue = startValue;
        _endValue = endValue;
    }

    @Override
    protected void Update()
    {
        _target.SetHeight(_startValue + (_endValue - _startValue) * Progress);
    }
}
