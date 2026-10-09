package it.unicam.cs.mpgc.rpg130575.Types.Layout;

import java.util.ArrayList;
import java.util.List;

public class Transform
{
    private final List<ILayoutChangeListener> _changeListeners = new ArrayList<>();

    private float _positionX;
    private float _positionY;

    private float _width;
    private float _height;

    public Transform(ILayoutChangeListener source)
    {
        if (source != null)
            _changeListeners.add(source);
    }

    public float GetWidth() { return _width; }
    public void SetWidth(float width)
    {
        _width = width;
        SendLayoutChange(LayoutInvalidation.Sizing);
    }

    public float GetHeight() { return _height; }
    public void SetHeight(float height)
    {
        _height = height;
        SendLayoutChange(LayoutInvalidation.Sizing);
    }

    public float GetPositionX() { return _positionX; }
    public void SetPositionX(float x)
    {
        _positionX = x;
        SendLayoutChange(LayoutInvalidation.Position);
    }

    public float GetPositionY() { return _positionY; }
    public void SetPositionY(float y)
    {
        _positionY = y;
        SendLayoutChange(LayoutInvalidation.Position);
    }

    private void SendLayoutChange(LayoutInvalidation type)
    {
        for (ILayoutChangeListener listener : _changeListeners)
        {
            listener.InvalidateProperty(type);
        }
    }
}
