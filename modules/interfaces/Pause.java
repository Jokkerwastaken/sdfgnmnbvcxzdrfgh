package modules.interfaces;

import classes.contracts.DrawableInterface;
import classes.interfaces.Button;
import modules.window.CanvasV2;

import java.awt.Color;
import java.awt.Graphics;

public class Pause implements DrawableInterface {
    private final CanvasV2 canvas;

    private int width, height;
    public boolean paused;
    public int framesFromPause;

    public Pause(CanvasV2 canvas) {
        this.canvas = canvas;

        this.paused = false;
        this.framesFromPause = 0;
    }

    private void createPauseText(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(24f));
        String text = "Paused";
        int textWidth = g.getFontMetrics().stringWidth(text);
        int x = (this.width - textWidth) / 2;
        int y = (int)(this.height * 0.31);
        g.drawString(text, x, y);
    }
    private void createExitButton(Graphics g) {
        Button b1 = new Button(canvas);
        b1.text.string = "Exit";
        b1.color = Color.DARK_GRAY;
        b1.text.color = Color.BLACK;
        b1.width = g.getFontMetrics().stringWidth(b1.text.string + 20);
        b1.height = 40;
        b1.x = (this.width/2)- b1.width;
        b1.y = (this.height/2)- b1.height;
        b1.draw(g);
    }

    @Override
    public void draw(Graphics g) {
        this.width = this.canvas.width;
        this.height = this.canvas.height;

        // UI
        if (!this.paused) return;
        g.setColor(Color.GRAY);
        g.fillRoundRect((int)(this.width*0.2), (int)(this.height*0.2), (int)(this.width*0.6), (int)(this.height*0.6), 10, 10);

        createPauseText(g);
        createExitButton(g);
    }
}
