package it.unicam.cs.mpgc.rpg130575.Backends.JavaFX.Input;

import it.unicam.cs.mpgc.rpg130575.Input.*;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Map;

import static java.util.Map.entry;

public class JavaFXInputManager implements EventHandler<KeyEvent>, IInputManager
{
    private final HashSet<IInputListener> _listeners = new HashSet<>();

    private final HashSet<CKeyboardKey> _pressedKeys = new HashSet<>();

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

            entry(KeyCode.DIGIT0, CKeyboardKey.Digit0),
            entry(KeyCode.DIGIT1, CKeyboardKey.Digit1),
            entry(KeyCode.DIGIT2, CKeyboardKey.Digit2),
            entry(KeyCode.DIGIT3, CKeyboardKey.Digit3),
            entry(KeyCode.DIGIT4, CKeyboardKey.Digit4),
            entry(KeyCode.DIGIT5, CKeyboardKey.Digit5),
            entry(KeyCode.DIGIT6, CKeyboardKey.Digit6),
            entry(KeyCode.DIGIT7, CKeyboardKey.Digit7),
            entry(KeyCode.DIGIT8, CKeyboardKey.Digit8),
            entry(KeyCode.DIGIT9, CKeyboardKey.Digit9),

            entry(KeyCode.SEMICOLON, CKeyboardKey.Semicolon),
            entry(KeyCode.QUOTE, CKeyboardKey.Quote),
            entry(KeyCode.COMMA, CKeyboardKey.Comma),
            entry(KeyCode.PERIOD, CKeyboardKey.Period),
            entry(KeyCode.SLASH, CKeyboardKey.ForwardSlash),

            entry(KeyCode.SPACE, CKeyboardKey.Space),
            entry(KeyCode.ENTER, CKeyboardKey.Enter),
            entry(KeyCode.TAB, CKeyboardKey.Tab),
            entry(KeyCode.BACK_SPACE, CKeyboardKey.Backspace),
            entry(KeyCode.ESCAPE, CKeyboardKey.Esc),
            entry(KeyCode.DELETE, CKeyboardKey.Del),

            entry(KeyCode.UP, CKeyboardKey.UpArrow),
            entry(KeyCode.DOWN, CKeyboardKey.DownArrow),
            entry(KeyCode.LEFT, CKeyboardKey.LeftArrow),
            entry(KeyCode.RIGHT, CKeyboardKey.RightArrow),

            entry(KeyCode.SHIFT, CKeyboardKey.Shift),
            entry(KeyCode.CONTROL, CKeyboardKey.Ctrl),
            entry(KeyCode.ALT, CKeyboardKey.Alt),
            entry(KeyCode.WINDOWS, CKeyboardKey.Win),
            entry(KeyCode.CAPS, CKeyboardKey.Caps)
    );


    // Translate JavaFX input into framework input.
    public void handle(KeyEvent event)
    {
        CButtonState buttonState;
        CKeyboardKey convertedKey = _keyboardKeysMappings.get(event.getCode());
        boolean isRepeat = false;

        if (event.getEventType() == KeyEvent.KEY_PRESSED)
        {
            buttonState = CButtonState.Down;
            isRepeat = !_pressedKeys.add(convertedKey);
        }
        else if (event.getEventType() == KeyEvent.KEY_RELEASED)
        {
            buttonState = CButtonState.Up;
            _pressedKeys.remove(convertedKey);
        }
        else
        {
            return;
        }

        for (IInputListener listener : _listeners)
        {
            listener.OnKeyboardInput(
                    new CKeyboardKeyEvent(convertedKey, isRepeat), buttonState);
        }
    }

    public void RegisterListener(IInputListener listener)
    {
        _listeners.add(listener);
    }
}
