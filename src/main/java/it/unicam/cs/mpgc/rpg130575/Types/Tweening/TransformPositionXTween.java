package it.unicam.cs.mpgc.rpg130575.Types.Tweening;

import it.unicam.cs.mpgc.rpg130575.Types.Layout.Transform;

public class TransformPositionXTween extends Tween
{
    private final Transform _target;

    private final float _startValue;
    private final float _endValue;

    public TransformPositionXTween(Transform target, double duration, float endValue)
    {
        this(target, duration, target.GetPositionX(), endValue);
    }

    public TransformPositionXTween(Transform target, double duration, float startValue, float endValue)
    {
        super(duration);

        _target = target;

        if (startValue != _target.GetPositionX())
            _target.SetPositionX(startValue);

        _startValue = startValue;
        _endValue = endValue;
    }

    @Override
    protected void Update()
    {
        _target.SetPositionX(_startValue + (_endValue * Progress));
    }
}
