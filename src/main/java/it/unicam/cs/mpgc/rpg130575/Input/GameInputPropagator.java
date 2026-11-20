package it.unicam.cs.mpgc.rpg130575.Input;

import it.unicam.cs.mpgc.rpg130575.Nodes.Base.Node;

import java.util.ArrayList;
import java.util.List;

/** Handles discovery and propagation of input from the user inside the node tree.<br/>
 * Nodes can choose whether they want to participate (and further propagate input).<br/>
 * Propagation happens in a bottom-up fashion, stopping at the first node who marks the event as handled.
 */
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

        if (state == CButtonState.Down)
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
