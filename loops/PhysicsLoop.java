package loops;

import modules.GFrame;
import objects.EntityV2;

public class PhysicsLoop implements Runnable {
    private GFrame frame;
    public boolean paused;

    public PhysicsLoop(GFrame frame) {
        this.frame = frame;
    }

    private void keepPlayerInFrame() {  // Explodes everything else idk why
        EntityV2 player = (EntityV2) this.frame.canvas.controllable.get(0);
        while (true) {
            if (player.position.screenX >= frame.canvas.centerX * 1.5) frame.canvas.OffsetX -= 2;
            if (player.position.screenX <= frame.canvas.centerX * 0.5) frame.canvas.OffsetX += 2;
            if (player.position.screenY >= frame.canvas.centerY * 1.5) frame.canvas.OffsetY -= 2;
            if (player.position.screenY <= frame.canvas.centerY * 0.5) frame.canvas.OffsetY += 2;
        }
    }

    @Override
    public void run() {
        long refLastTime = System.nanoTime();
        long currentTime;
        float deltaTime;

        frame.canvas.phyThread = this;
        
        while (true) {
            frame.canvas.getInputs(this);

            currentTime = System.nanoTime();
            deltaTime = (currentTime - refLastTime) / 1_000_000_000.0f;
            refLastTime = currentTime;

            frame.canvas.P_deltaTime = deltaTime;

            if (!this.paused) {
                frame.canvas.moveMovable(deltaTime);
                //keepPlayerInFrame();
            }

            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
