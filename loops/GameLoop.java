package loops;

import modules.GFrame;

public class GameLoop implements Runnable {
    private GFrame frame;

    public GameLoop(GFrame frame) {
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

            frame.canvas.moveMovable(deltaTime);
            //System.out.println("Hello!");

            try {
                Thread.sleep(1000/30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
