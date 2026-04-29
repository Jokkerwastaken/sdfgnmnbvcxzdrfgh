package loops;

import modules.GFrame;

public class PhysicsLoop implements Runnable {
    private GFrame frame;

    public PhysicsLoop(GFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        long refLastTime = System.nanoTime();
        long currentTime;
        float deltaTime;
        
        while (true) {
            currentTime = System.nanoTime();
            deltaTime = (currentTime - refLastTime) / 1_000_000_000.0f;
            refLastTime = currentTime;

            frame.canvas.P_deltaTime = deltaTime;

            frame.canvas.moveMovable(deltaTime);

            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
