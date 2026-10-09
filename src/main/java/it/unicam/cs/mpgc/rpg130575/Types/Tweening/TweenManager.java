package it.unicam.cs.mpgc.rpg130575.Types.Tweening;

import java.util.ArrayList;

public class TweenManager
{
    private final ArrayList<Tween> _activeTweens = new ArrayList<>();
    private final ArrayList<Tween> _pendingRemovals = new ArrayList<>();

    public void AddTween(Tween tween) { _activeTweens.add(tween); }

    public void Update(double deltaSeconds)
    {
        for (Tween tween : _activeTweens)
        {
            tween.TickForward(deltaSeconds);

            if (tween.IsDone())
                _pendingRemovals.add(tween);
        }

        for (Tween tween : _pendingRemovals)
        {
            _activeTweens.remove(tween);
        }

        _pendingRemovals.clear();
    }
}
