package it.unicam.cs.mpgc.rpg130575.Input;

import it.unicam.cs.mpgc.rpg130575.Nodes.LogicNode;

public class GameInputPropagator implements IInputListener
{
    private final LogicNode _treeRoot;

    public GameInputPropagator(LogicNode treeRoot)
    {
        _treeRoot = treeRoot;
    }

    @Override
    public void OnKeyboardInput(CKeyboardKeyEvent key)
    {

    }
}
