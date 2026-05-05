package modules.interfaces;

import classes.contracts.DrawableInterface;
import modules.Canvas;

import java.awt.Color;
import java.awt.Graphics;

public class Pause implements DrawableInterface {
    private final Canvas canvas;
    private int width, height;
    public boolean paused;
    public int framesFromPause;

    public Pause(Canvas canvas) {
        this.canvas = canvas;

        this.paused = false;
        this.framesFromPause = 0;
    }

    @Override
    public void draw(Graphics g) {
        this.width = this.canvas.width;
        this.height = this.canvas.height;

        // UI
        if (this.paused) {
            g.setColor(Color.GRAY);
            g.fillRoundRect((int)(this.width*0.2), (int)(this.height*0.2), (int)(this.width*0.6), (int)(this.height*0.6), 10, 10);

            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(24f));
            String text = "Paused";
            int textWidth = g.getFontMetrics().stringWidth(text);
            int x = (this.width - textWidth) / 2;
            int y = (int)(this.height * 0.31);
            g.drawString(text, x, y);
        }
    }
}
