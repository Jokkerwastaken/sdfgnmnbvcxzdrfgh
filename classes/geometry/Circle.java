package classes.geometry;

import java.awt.Color;
import java.awt.Graphics;

import classes.contracts.Drawable;
import classes.contracts.Listable;
import classes.contracts.Parentable;
import modules.window.GFrame;
import objects.EntityV2;

public class Circle implements Drawable, Listable {
    private GFrame frame;
    public Parentable parent;
    public double x, y, X, Y;
    public int radius;
    public Color color;
    private float alpha, lastAlpha;

    public Circle(double x, double y,  GFrame frame) {
        this.x = x;
        this.y = y;

        if (frame == null) return;
        this.frame = frame;
        this.frame.canvas.addToLists(this);
    }


    // Drawable contract
    @Override
    public void setAlpha(float alpha) {
        
    }

    @Override
    public boolean inScreen(int offsetX, int offsetY, float scale) {
        float scaledRadius = this.radius * scale;

        return (X + scaledRadius >= 0 && X - scaledRadius <= this.frame.canvas.width) &&
               (Y + scaledRadius >= 0 && Y - scaledRadius <= this.frame.canvas.height);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (color == null) return;

        if (lastAlpha != alpha) {
            lastAlpha = alpha;
            this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.alpha);
        }
        g.setColor(this.color);
        

        double currentX = (parent instanceof EntityV2) ? ((EntityV2)parent).position.x : this.x;
        double currentY = (parent instanceof EntityV2) ? ((EntityV2)parent).position.y : this.y;
        
        int screenCenterX = (int) (offsetX + (currentX * scale));
        int screenCenterY = (int) (offsetY + (currentY * scale));

        int sRadius = (int) (radius * scale);
        int rSquared = sRadius * sRadius;
        

        for (int dx = -sRadius; dx <= sRadius; dx++) 
            for (int dy = -sRadius; dy <= sRadius; dy++) 
                if (dx * dx + dy * dy <= rSquared-0.5) 
                    g.fillRect(screenCenterX + dx, screenCenterY + dy, 1, 1);

        this.X = screenCenterX;
        this.Y = screenCenterY;
    }
}