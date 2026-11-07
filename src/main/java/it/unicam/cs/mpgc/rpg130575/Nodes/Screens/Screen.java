package it.unicam.cs.mpgc.rpg130575.Nodes.Screens;

import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeNode;

public abstract class Screen extends CompositeNode
{
    private boolean _previousAcceptsInput;
    private boolean _previousSubtreeIgnoredForInput;

    public final void OnPushedToStack()
    {
        _previousAcceptsInput = GetAcceptsInput();
        _previousSubtreeIgnoredForInput = GetIgnoreSubtreeForInput();

        SetAcceptsInput(false);
        SetIgnoreSubtreeForInput(true);
    }

    public final void OnPoppedFromStack()
    {
        SetAcceptsInput(_previousAcceptsInput);
        SetIgnoreSubtreeForInput(_previousSubtreeIgnoredForInput);
    }

    public abstract void OnScreenStart();
    public abstract void OnScreenQuit();
}
