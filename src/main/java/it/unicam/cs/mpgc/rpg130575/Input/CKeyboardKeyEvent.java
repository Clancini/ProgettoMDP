package it.unicam.cs.mpgc.rpg130575.Input;

// C as in Clancì. To distinguish from JavaFX's KeyEvent.
public class CKeyboardKeyEvent
{
    public final CKeyboardKey Key;
    public final boolean IsRepeat;

    public CKeyboardKeyEvent(CKeyboardKey key, boolean isRepeat)
    {
        Key = key;
        IsRepeat = isRepeat;
    }
}
