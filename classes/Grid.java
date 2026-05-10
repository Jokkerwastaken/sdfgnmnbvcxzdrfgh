package classes;

import classes.contracts.Drawable;
import classes.contracts.Listable;
import modules.window.GFrame;

import java.awt.Color;
import java.awt.Graphics;

public class Grid implements Drawable, Listable {
    private final GFrame frame;
    private final int spacing = 50; // Distance between lines
    private float alpha = 1.0f;

    public Grid(GFrame frame) {
        this.frame = frame;
        if (frame != null && frame.canvas != null) {
            frame.canvas.addToLists(this);
        }
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public boolean inScreen(int offsetX, int offsetY, float scale) {
        return true;
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        g.setColor(new Color(Color.LIGHT_GRAY.getRed(), 
                             Color.LIGHT_GRAY.getGreen(), 
                             Color.LIGHT_GRAY.getBlue(), 
                             (int)(255 * alpha)));

        float scaledSpacing = spacing * scale;
        
        int width = frame.canvas.getWidth();
        int height = frame.canvas.getHeight();

        for (float x = offsetX % scaledSpacing; x < width; x += scaledSpacing) {
            g.drawLine((int)x, 0, (int)x, height);
        }

        for (float y = offsetY % scaledSpacing; y < height; y += scaledSpacing) {
            g.drawLine(0, (int)y, width, (int)y);
        }
        
        g.setColor(new Color(100, 100, 100, (int)(255 * alpha)));
        g.drawLine(offsetX, 0, offsetX, height); // Y-axis
        g.drawLine(0, offsetY, width, offsetY);   // X-axis
    }
}