package classes;

import java.awt.Graphics;
import java.awt.Color;

import classes.contracts.Drawable;

public class Point2 implements Drawable {
    public double x, y;
    public Color Color;
    public int Radius;
    public float Scale;

    public Point2 (double x, double y, int radius, float scale) {
        this.x = x;
        this.y = y;

        this.Color = null;
        this.Radius = radius;
        this.Scale = scale;
    }

    public Point2 (double x, double y, int radius, Color color, float scale) {
        this.x = x;
        this.y = y;

        this.Color = color;
        this.Radius = radius;
        this.Scale = scale;
    }

    public void add(Vector2 vector) {
        this.x += vector.x * this.Scale;
        this.y += vector.y * this.Scale;
    }


    public Vector2 vector(Point2 other) {
        return new Vector2(other.x - this.x, other.y - this.y, this.Scale);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.Scale == 0 && scale > 1e-9) this.Scale = scale;
        if (this.Scale != scale) this.Scale = scale;
        
        if (this.Color == null) return;

        g.setColor(this.Color);
        g.fillOval((int)((offsetX + this.x) - (Radius*this.Scale)),
                    (int)((offsetY - this.y) - (Radius*this.Scale)),
                    (int)(Radius*2*this.Scale),
                    (int)(Radius*2*this.Scale));
    }
}
