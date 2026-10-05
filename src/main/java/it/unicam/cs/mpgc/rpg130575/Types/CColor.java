package it.unicam.cs.mpgc.rpg130575.Types;

public class CColor
{
    public final int R;
    public final int G;
    public final int B;
    public final int A;

    public CColor(int r, int g, int b)
    {
        this(r, g, b, 255);
    }

    public CColor(int r, int g, int b, int a)
    {
        R = r;
        G = g;
        B = b;
        A = a;
    }

    public static float Clamped01(int value)
    {
        if (value < 0)
            return 0;

        if (value > 255)
            return 1;

        return value / 255f;
    }
}
