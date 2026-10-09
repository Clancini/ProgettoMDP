package it.unicam.cs.mpgc.rpg130575.Input;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;

import java.util.ArrayList;
import java.util.List;

public class GameInputPropagator implements IInputListener
{
    private final LogicNode _treeRoot;

    private final List<LogicNode> _inputQueue = new ArrayList<>();

    public GameInputPropagator(LogicNode treeRoot)
    {
        _treeRoot = treeRoot;
    }

    @Override
    public void OnKeyboardInput(CKeyboardKeyEvent key)
    {
        _inputQueue.clear();

        _treeRoot.JoinInputQueueIfNeeded(_inputQueue);

        final List<LogicNode> reversedInputQueue = _inputQueue.reversed();

        for (LogicNode node : reversedInputQueue)
        {
            if (node.OnKeyboardKeyDown(key))
                break;
        }
    }
}
