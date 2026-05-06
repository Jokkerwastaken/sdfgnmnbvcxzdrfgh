package modules.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
    public final boolean[] MouseStates = new boolean[MouseEvent.RESERVED_ID_MAX];
    public int MouseTriggered;
    public int mouseWheelState = 0;

    public boolean getKeyTriggered(int key) {
        int Triggered = this.MouseTriggered;
        if (MouseTriggered != -1) MouseTriggered = -1;

        return Triggered == key;
    }

    public int getMouseWheelState() {
        return mouseWheelState;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MouseTriggered = e.getButton();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MouseStates[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MouseStates[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelState = e.getWheelRotation();
    }
}
