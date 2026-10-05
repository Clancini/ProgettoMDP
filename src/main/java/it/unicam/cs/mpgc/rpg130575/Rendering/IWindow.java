package it.unicam.cs.mpgc.rpg130575.Rendering;

public interface IWindow
{
    public float GetWidth();
    public void SetWidth(float width);

    public float GetHeight();
    public void SetHeight(float height);

    public String GetTitle();
    public void SetTitle(String title);
}
