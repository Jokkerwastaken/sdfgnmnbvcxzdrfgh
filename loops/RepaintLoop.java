package loops;

import modules.GFrame;

public class RepaintLoop implements Runnable {
    private final GFrame Frame;
    private float FPSLimit = 0.0f;
    private boolean isFPSLimited;

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

            Frame.canvas.F_deltaTime = deltaTime;
            if (Frame.canvas.F_samples < Frame.canvas.lastFewFPS.length) Frame.canvas.F_samples++; 
            
            Frame.canvas.repaint();
            
            if (!isFPSLimited) return;

            try {
                Thread.sleep((int)(1000/FPSLimit));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
    }
}