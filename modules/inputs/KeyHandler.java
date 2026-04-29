package modules.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public final boolean[] KeyStates = new boolean[KeyEvent.RESERVED_ID_MAX];
    public char KeyTyped;

    public boolean getKeyState(int key) {
        boolean state = KeyStates[key];
        return state;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        KeyStates[e.getKeyCode()] = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        KeyTyped = e.getKeyChar();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyStates[e.getKeyCode()] = false;
    }
}
