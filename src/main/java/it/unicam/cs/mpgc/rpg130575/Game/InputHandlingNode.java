package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKey;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;

public class InputHandlingNode extends LogicNode
{
    @Override
    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event)
    {
        if (event.Key == CKeyboardKey.Q)
        {
            SetAcceptsInput(false);
            return true;
        }

        if (event.Key == CKeyboardKey.E)
        {
            System.out.println("Hi");
            return true;
        }

        return false;
    }
}
