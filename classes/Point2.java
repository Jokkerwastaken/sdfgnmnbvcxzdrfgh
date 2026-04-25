package classes;

import java.awt.Graphics;
import java.awt.Color;

import classes.contracts.Drawable;

public class Point2 implements Drawable {
    public double x, y;
    public Color Color;
    public int Radius;

    public Point2 (double x, double y, int radius) {
        this.x = x;
        this.y = y;
        this.Color = null;
        this.Radius = radius;
    }

    public Point2 (double x, double y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.Color = color;
        this.Radius = radius;
    }

    public Vector2 vector(Point2 other) {
        return new Vector2(other.x - this.x, other.y - this.y);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.Color == null) return;
        g.setColor(this.Color);
        g.fillOval((int)((offsetX + this.x) - (Radius*scale)), (int)((offsetY - this.y) - (Radius*scale)), (int)(Radius*2*scale), (int)(Radius*2*scale));
    }
}
