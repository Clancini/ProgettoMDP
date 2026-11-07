package it.unicam.cs.mpgc.rpg130575.Nodes.Screens;

import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeNode;

import java.util.Stack;

public class ScreenStack extends CompositeNode
{
    private final Stack<Screen> _screens = new Stack<>();

    public void Push(Screen newScreen)
    {
        if (!_screens.isEmpty())
        {
            _screens.peek().OnNewScreenPushedToStack();
            _screens.peek().OnScreenQuit();
        }

        _screens.push(newScreen);

        Add(newScreen);
        newScreen.OnScreenStart();
    }

    public void Pop()
    {
        if (!_screens.isEmpty())
        {
            Screen screen = _screens.pop();

            screen.OnScreenQuit();
            Remove(screen);
        }

        if (!_screens.isEmpty())
        {
            _screens.peek().OnPreviousScreenPoppedFromStack();
            _screens.peek().OnScreenStart();
        }
    }
}
