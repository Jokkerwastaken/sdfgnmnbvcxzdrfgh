package loops;

import modules.FPSManager;
import modules.GFrame;
import objects.Entity;

public class RepaintLoop implements Runnable {
    private final GFrame Frame;
    private float FPSLimit = 0.0f;
    private final boolean isFPSLimited;

    public RepaintLoop(GFrame frame, float FPSLimit) {
        this.Frame = frame;

        if (FPSLimit > 0) {
            this.FPSLimit = FPSLimit;
            this.isFPSLimited = true;
        } else {
            this.isFPSLimited = false;
        }
    }

    private void fps(float deltaTime) {
        FPSManager fps = Frame.canvas.fps.fpsManager;
        
        fps.F_deltaTime = deltaTime;
        if (fps.F_samples < fps.LastFewFPS.length) fps.F_samples++;
    }

    private void keepPlayerInFrame() {
        Entity player = (Entity) this.Frame.canvas.controllable.get(0);

        if (player.Position.screenX >= Frame.width * 0.75) Frame.canvas.OffsetX -= 2;
        if (player.Position.screenX <= Frame.width * 0.25) Frame.canvas.OffsetX += 2;
        if (player.Position.screenY >= Frame.height * 0.75) Frame.canvas.OffsetY -= 2;
        if (player.Position.screenY <= Frame.height * 0.25) Frame.canvas.OffsetY += 2;
    }

    @Override
    public void run() {
        long repLastTime = System.nanoTime();
        long currentTime;
        float deltaTime;

        while (true) {
            currentTime = System.nanoTime();
            deltaTime = (currentTime - repLastTime) / 1_000_000_000.0f;
            repLastTime = currentTime;

            fps(deltaTime);

            keepPlayerInFrame();
            Frame.canvas.repaint();
            // Frame.canvas.Scale -= 0.001f;    // Example use of scale which in this case slowly zooms out
            // Frame.canvas.OffsetY -= 1;       // Example use of screen offset which in this case is redused 
                                                // and as a result the picture moves downward
            
            if (isFPSLimited) {
                try {
                    Thread.sleep((int)(1000/FPSLimit));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }   
    }
}