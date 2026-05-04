package modules.interfaces;

import classes.contracts.DrawableInterface;
import modules.FPSManager;

import java.awt.Graphics;
import java.awt.Color;

public class FPS implements DrawableInterface {
    public FPSManager fpsManager;

    public FPS() {
        this.fpsManager = new FPSManager();

        this.fpsManager.MaxFPS = 0;
        this.fpsManager.MinFPS = Integer.MAX_VALUE;
    }

    private void drawFPS(Graphics g) {
        if (this.fpsManager.F_deltaTime == 0) return;

        this.fpsManager.LastFewFPS[this.fpsManager.Count % this.fpsManager.LastFewFPS.length] = (int)(1.0 / this.fpsManager.F_deltaTime);
        this.fpsManager.Count = (this.fpsManager.Count + 1) % this.fpsManager.LastFewFPS.length;

        if (this.fpsManager.F_deltaTime > 0) {
            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(12f));
            
            g.drawString("Max FPS: " + this.fpsManager.MaxFPS, 30, 30);
            g.drawString("Avg FPS: " + this.fpsManager.AverageFPS, 30, 50);
            g.drawString("Min FPS: " + (this.fpsManager.MinFPS == Integer.MAX_VALUE ? 0 : this.fpsManager.MinFPS), 30, 70);
        }
    }

    public void manageFPS() {
        this.fpsManager.manage();
    }


    @Override
    public void draw(Graphics g) {
        // UI
        drawFPS(g);
    }
}
