package it.unicam.cs.mpgc.rpg130575.Input;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Map;

import static java.util.Map.entry;

public class JavaFXInputManager implements EventHandler<KeyEvent>, IInputManager
{
    private final HashSet<IInputListener> _listeners = new HashSet<>();

    private final Map<KeyCode, CKeyboardKey> _keyboardKeysMappings = Map.<KeyCode, CKeyboardKey>ofEntries(
            entry(KeyCode.A, CKeyboardKey.A),
            entry(KeyCode.B, CKeyboardKey.B),
            entry(KeyCode.C, CKeyboardKey.C),
            entry(KeyCode.D, CKeyboardKey.D),
            entry(KeyCode.E, CKeyboardKey.E),
            entry(KeyCode.F, CKeyboardKey.F),
            entry(KeyCode.G, CKeyboardKey.G),
            entry(KeyCode.H, CKeyboardKey.H),
            entry(KeyCode.I, CKeyboardKey.I),
            entry(KeyCode.J, CKeyboardKey.J),
            entry(KeyCode.K, CKeyboardKey.K),
            entry(KeyCode.L, CKeyboardKey.L),
            entry(KeyCode.M, CKeyboardKey.M),
            entry(KeyCode.N, CKeyboardKey.N),
            entry(KeyCode.O, CKeyboardKey.O),
            entry(KeyCode.P, CKeyboardKey.P),
            entry(KeyCode.Q, CKeyboardKey.Q),
            entry(KeyCode.R, CKeyboardKey.R),
            entry(KeyCode.S, CKeyboardKey.S),
            entry(KeyCode.T, CKeyboardKey.T),
            entry(KeyCode.U, CKeyboardKey.U),
            entry(KeyCode.V, CKeyboardKey.V),
            entry(KeyCode.W, CKeyboardKey.W),
            entry(KeyCode.X, CKeyboardKey.X),
            entry(KeyCode.Y, CKeyboardKey.Y),
            entry(KeyCode.Z, CKeyboardKey.Z),

            entry(KeyCode.DIGIT0, CKeyboardKey.DIGIT0),
            entry(KeyCode.DIGIT1, CKeyboardKey.DIGIT1),
            entry(KeyCode.DIGIT2, CKeyboardKey.DIGIT2),
            entry(KeyCode.DIGIT3, CKeyboardKey.DIGIT3),
            entry(KeyCode.DIGIT4, CKeyboardKey.DIGIT4),
            entry(KeyCode.DIGIT5, CKeyboardKey.DIGIT5),
            entry(KeyCode.DIGIT6, CKeyboardKey.DIGIT6),
            entry(KeyCode.DIGIT7, CKeyboardKey.DIGIT7),
            entry(KeyCode.DIGIT8, CKeyboardKey.DIGIT8),
            entry(KeyCode.DIGIT9, CKeyboardKey.DIGIT9),

            entry(KeyCode.SEMICOLON, CKeyboardKey.SEMICOLON),
            entry(KeyCode.QUOTE, CKeyboardKey.QUOTE),
            entry(KeyCode.COMMA, CKeyboardKey.COMMA),
            entry(KeyCode.PERIOD, CKeyboardKey.PERIOD),
            entry(KeyCode.SLASH, CKeyboardKey.SLASH),

            entry(KeyCode.SPACE, CKeyboardKey.SPACE),
            entry(KeyCode.ENTER, CKeyboardKey.ENTER),
            entry(KeyCode.TAB, CKeyboardKey.TAB),
            entry(KeyCode.BACK_SPACE, CKeyboardKey.BACKSPACE),
            entry(KeyCode.ESCAPE, CKeyboardKey.ESCAPE),
            entry(KeyCode.DELETE, CKeyboardKey.DELETE),

            entry(KeyCode.UP, CKeyboardKey.UP),
            entry(KeyCode.DOWN, CKeyboardKey.DOWN),
            entry(KeyCode.LEFT, CKeyboardKey.LEFT),
            entry(KeyCode.RIGHT, CKeyboardKey.RIGHT),

            entry(KeyCode.SHIFT, CKeyboardKey.SHIFT),
            entry(KeyCode.CONTROL, CKeyboardKey.CONTROL),
            entry(KeyCode.ALT, CKeyboardKey.ALT),
            entry(KeyCode.WINDOWS, CKeyboardKey.WINDOWS),
            entry(KeyCode.CAPS, CKeyboardKey.CAPS_LOCK)
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
