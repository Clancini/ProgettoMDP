package it.unicam.cs.mpgc.rpg130575.Input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Map;

public class JavaFXInputManager implements EventHandler<KeyEvent>, IInputManager
{
    private final HashSet<IInputListener> _listeners = new HashSet<>();

    private final Map<KeyCode, CKeyboardKey> _keyboardKeysMappings = Map.of(
        KeyCode.Q, CKeyboardKey.Q,
        KeyCode.W, CKeyboardKey.W,
        KeyCode.E, CKeyboardKey.E
    );

    public void handle(KeyEvent event)
    {
        for (IInputListener listener : _listeners)
        {
            listener.OnKeyboardInput(
                    new CKeyboardKeyEvent(_keyboardKeysMappings.get(event.getCode())));
        }
    }

    public void RegisterListener(IInputListener listener)
    {
        _listeners.add(listener);
    }
}
