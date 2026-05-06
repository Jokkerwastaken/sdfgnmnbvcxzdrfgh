package modules.inputs;

import java.awt.event.KeyEvent;
import loops.PhysicsLoop;
import modules.Canvas;

public class InputHandler {
    private final Canvas canvas;

    public KeyHandler keyHandler;
    public MouseHandler mouseHandler;

    public InputHandler(Canvas canvas) {
        this.canvas = canvas;

        this.keyHandler = new KeyHandler();
        this.mouseHandler = new MouseHandler();
    }


    public void getInput(PhysicsLoop gameLoop) {
        getPause();
        if (!gameLoop.paused) getZooming();
    }

    private void getPause() {
        if (this.canvas.pause.framesFromPause < 20) {
            this.canvas.pause.framesFromPause++;
            return;
        }
        if (this.keyHandler.getKeyState(KeyEvent.VK_ESCAPE)) {
            this.canvas.pause.paused = !this.canvas.pause.paused;
            this.canvas.pause.framesFromPause = 0;
        }

        this.canvas.phyThread.paused = this.canvas.pause.paused;
    }

    private void getZooming() {
        int wheelState = this.mouseHandler.getMouseWheelState();
        this.mouseHandler.mouseWheelState = 0;        
        if (wheelState == 0) return;

        if (wheelState < 0 && this.canvas.Scale < 10.0) this.canvas.Scale += 0.2;  //Zoom in
        if (wheelState > 0 && this.canvas.Scale > 0.2) this.canvas.Scale -= 0.2;  //Zoom out
    }
}
