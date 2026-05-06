package classes;

import classes.contracts.Drawable;
import modules.GFrame;
import java.awt.Color;
import java.awt.Graphics;

public class Grid implements Drawable {
    private final GFrame frame;
    private final int spacing = 50; // Distance between lines
    private float alpha = 1.0f;

    public Grid(GFrame frame) {
        this.frame = frame;
        // We add the grid itself as a single drawable object
        if (frame != null && frame.canvas != null) {
            frame.canvas.addDrawable(this);
        }
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public boolean inScreen() {
        // The grid is global, so it's effectively always "in screen"
        return true;
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        g.setColor(new Color(Color.LIGHT_GRAY.getRed(), 
                             Color.LIGHT_GRAY.getGreen(), 
                             Color.LIGHT_GRAY.getBlue(), 
                             (int)(255 * alpha)));

        float scaledSpacing = spacing * scale;
        
        // Calculate the boundaries of the frame
        int width = frame.canvas.getWidth();
        int height = frame.canvas.getHeight();

        // 1. Draw Vertical Lines
        // We start from the offset and move left/right until we hit screen edges
        for (float x = offsetX % scaledSpacing; x < width; x += scaledSpacing) {
            g.drawLine((int)x, 0, (int)x, height);
        }

        // 2. Draw Horizontal Lines
        // We start from the offset and move up/down until we hit screen edges
        for (float y = offsetY % scaledSpacing; y < height; y += scaledSpacing) {
            g.drawLine(0, (int)y, width, (int)y);
        }
        
        // 3. Optional: Draw Axis (Center Lines) in a darker color
        g.setColor(new Color(100, 100, 100, (int)(255 * alpha)));
        g.drawLine(offsetX, 0, offsetX, height); // Y-axis
        g.drawLine(0, offsetY, width, offsetY);   // X-axis
    }
}