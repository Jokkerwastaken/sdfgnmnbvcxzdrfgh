package loops;

import modules.GFrame;

public class RepaintLoop implements Runnable {
    private GFrame frame;
    private float FPSLimit = 0.0f;
    private boolean isFPSLimited;

    public RepaintLoop(GFrame frame, float FPSLimit) {
        this.frame = frame;

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

            frame.canvas.F_deltaTime = deltaTime;
            if (frame.canvas.F_samples < frame.canvas.lastFewFPS.length) frame.canvas.F_samples++; 
            
            frame.canvas.repaint();
            
            if (!isFPSLimited) return;

            try {
                Thread.sleep((int)(1000/FPSLimit));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
    }
}