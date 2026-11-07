package it.unicam.cs.mpgc.rpg130575.Types.Tweening;

import java.util.ArrayList;

public class TweenManager
{
    private final ArrayList<Tween> _activeTweens = new ArrayList<>();
    private final ArrayList<Tween> _pendingAdds = new ArrayList<>();
    private final ArrayList<Tween> _pendingRemovals = new ArrayList<>();

    public void AddTween(Tween tween)
    {
        _pendingAdds.add(tween);
    }

    public void Update(double deltaSeconds)
    {
        _activeTweens.addAll(_pendingAdds);
        _activeTweens.removeAll(_pendingRemovals);

        _pendingAdds.clear();
        _pendingRemovals.clear();

        for (Tween tween : _activeTweens)
        {
            if (tween.IsDone() || tween.GetIsCancelled())
                _pendingRemovals.add(tween);
            else
                tween.TickForward(deltaSeconds);
        }
    }
}
