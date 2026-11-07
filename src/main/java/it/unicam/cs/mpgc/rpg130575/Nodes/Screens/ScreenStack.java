package it.unicam.cs.mpgc.rpg130575.Nodes.Screens;

import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeNode;

import java.util.Stack;

public class ScreenStack extends CompositeNode
{
    private final Stack<Screen> _screens = new Stack<>();

    public void Push(Screen newScreen)
    {
        _screens.peek().OnScreenQuit();

        _screens.push(newScreen);

        Add(newScreen);
        newScreen.OnPushedToStack();

        newScreen.OnScreenStart();
    }

    public void Pop()
    {
        Screen screen = _screens.pop();

        screen.OnScreenQuit();

        Remove(screen);
        screen.OnPoppedFromStack();

        _screens.peek().OnScreenStart();
    }
}
