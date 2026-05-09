package modules;

import classes.contracts.DrawableInterface;

import java.awt.Graphics;
import java.awt.Color;

public class FPSManager implements DrawableInterface {
    public float F_samples, F_deltaTime, FPSLimit;
    public final int[] LastFewFPS = new int[100];
    public int Count, MinFPS, MaxFPS, AverageFPS;
    public long LastMinFPS, LastMaxFPS;
    public boolean enabled;

    public Color color;

    public FPSManager() {
        this.F_samples = 0;
        this.Count = 0;
        this.LastMinFPS = 0;
        this.LastMaxFPS = 0;
        this.MaxFPS = 0;
        this.MinFPS = Integer.MAX_VALUE;

        this.enabled = false;
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


    // Drawing
    private void drawFPS(Graphics g) {
        if (this.F_deltaTime == 0) return;

        this.LastFewFPS[this.Count % this.LastFewFPS.length] = (int)(1.0 / this.F_deltaTime);
        this.Count = (this.Count + 1) % this.LastFewFPS.length;

        if (this.F_deltaTime > 0 && color != null) {
            g.setColor(this.color);
            g.setFont(g.getFont().deriveFont(12f));
            
            g.drawString("Max FPS: " + this.MaxFPS, 30, 30);
            g.drawString("Avg FPS: " + this.AverageFPS, 30, 50);
            g.drawString("Min FPS: " + (this.MinFPS == Integer.MAX_VALUE ? 0 : this.MinFPS), 30, 70);
        }
    }

    @Override
    public void draw(Graphics g) {
        // UI
        if (enabled) drawFPS(g);
    }
}
