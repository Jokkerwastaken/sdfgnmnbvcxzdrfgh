package modules;

public class FPSManager {
    public float F_samples, F_deltaTime, FPSLimit;
    public final int[] LastFewFPS = new int[100];
    public int Count, MinFPS, MaxFPS, AverageFPS;
    public long LastMinFPS, LastMaxFPS;

    public FPSManager() {
        this.F_samples = 0;
        this.Count = 0;
        this.LastMinFPS = 0;
        this.LastMaxFPS = 0;
    }

    private void checkForMax(long currentTime) {
        long diffMax = (currentTime - this.LastMaxFPS) / 1_000_000_000;
        if (diffMax >= 60) {
            this.MaxFPS = 0;
            this.LastMaxFPS = currentTime;
        }
        if (this.AverageFPS > this.MaxFPS) {
            this.MaxFPS = this.AverageFPS;
        }
    }

    private void checkForMin(long currentTime) {
        long diffMin = (currentTime - this.LastMinFPS) / 1_000_000_000;
        if (diffMin >= 60 || this.MinFPS == 0) { 
            this.MinFPS = this.AverageFPS;
            this.LastMinFPS = currentTime;
        }
        if (this.AverageFPS < this.MinFPS && this.AverageFPS > 0) {
            this.MinFPS = this.AverageFPS;
        }
    }

    public void manage() {
        if (this.F_samples == 0 || this.LastFewFPS.length == 0) return;
    
        // Average FPS
        float sum = 0;
        for (int fps : this.LastFewFPS) {
            sum += fps;
        }
        this.AverageFPS = (int) (sum / this.F_samples);
    
        if (this.FPSLimit == 0) return;

        // Min and Max FPS
        long currentTime = System.nanoTime();
        checkForMax(currentTime);
        checkForMin(currentTime);
    }
}
