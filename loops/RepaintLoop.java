package loops;

import modules.GFrame;

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

    @Override
    public void run() {
        long repLastTime = System.nanoTime();
        long currentTime;
        float deltaTime;

        while (true) {
            currentTime = System.nanoTime();
            deltaTime = (currentTime - repLastTime) / 1_000_000_000.0f;
            repLastTime = currentTime;

            Frame.canvas.fpsManager.F_deltaTime = deltaTime;
            if (Frame.canvas.fpsManager.F_samples < Frame.canvas.fpsManager.LastFewFPS.length) Frame.canvas.fpsManager.F_samples++; 
            
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