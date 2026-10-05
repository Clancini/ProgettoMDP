package it.unicam.cs.mpgc.rpg130575.Nodes;

public class Transform
{
    private float _positionX;
    private float _positionY;

    private float _width;
    private float _height;

    public float GetWidth() { return _width; }
    public void SetWidth(float width) { _width = width; }

    public float GetHeight() { return _height; }
    public void SetHeight(float height) { _height = height; }

    public float GetPositionX() { return _positionX; }
    public void SetPositionX(float x) { _positionX = x; }

    public float GetPositionY() { return _positionY; }
    public void SetPositionY(float y) { _positionY = y; }
}
