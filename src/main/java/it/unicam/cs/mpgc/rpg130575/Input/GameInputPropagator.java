package it.unicam.cs.mpgc.rpg130575.Input;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.Node;

import java.util.ArrayList;
import java.util.List;

public class GameInputPropagator implements IInputListener
{
    private final Node _treeRoot;

    private final List<Node> _inputQueue = new ArrayList<>();

    public GameInputPropagator(Node treeRoot)
    {
        _treeRoot = treeRoot;
    }

    @Override
    public void OnKeyboardInput(CKeyboardKeyEvent key, CButtonState state)
    {
        _inputQueue.clear();

        _treeRoot.JoinInputQueueIfNeeded(_inputQueue);

        final List<Node> reversedInputQueue = _inputQueue.reversed();

        if (state == CButtonState.DOWN)
        {
            for (Node node : reversedInputQueue)
            {
                if (node.OnKeyboardKeyDown(key))
                    break;
            }
        }
        else
        {
            for (Node node : reversedInputQueue)
            {
                if (node.OnKeyboardKeyUp(key))
                    break;
            }
        }
    }
}
