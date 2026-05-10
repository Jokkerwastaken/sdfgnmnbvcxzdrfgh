package loops;

import java.awt.Color;

import modules.window.GFrame;

public class PhysicsLoop implements Runnable {
    private GFrame frame;
    public boolean paused;

    public PhysicsLoop(GFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        long refLastTime = System.nanoTime();
        long currentTime;
        float deltaTime;

        frame.canvas.phyThread = this;
        
        while (true) {
            frame.canvas.getInput(this);

            currentTime = System.nanoTime();
            deltaTime = (currentTime - refLastTime) / 1_000_000_000.0f;
            refLastTime = currentTime;

            frame.canvas.P_deltaTime = deltaTime;
            frame.canvas.fps.color = this.paused ? Color.WHITE : Color.BLACK;

            if (!this.paused) {
                frame.canvas.moveMovable(deltaTime);
            }

            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
