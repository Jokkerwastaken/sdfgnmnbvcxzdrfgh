package classes;

import java.awt.Graphics;
import java.awt.Color;

import classes.contracts.Drawable;

public class Point2 implements Drawable {
    public double x, y;
    public Color Color;
    public int Radius;
    //public float Scale;

    public Point2 (double x, double y, int radius) {
        this.x = x;
        this.y = y;

        this.Color = null;
        this.Radius = radius;
        //this.Scale = scale;
    }

    public Point2 (double x, double y, int radius, Color color) {
        this.x = x;
        this.y = y;

        this.Color = color;
        this.Radius = radius;
        //this.Scale = scale;
    }

    public void add(Vector2 vector) {
        this.x += vector.x;
        this.y += vector.y;
    }


    public Vector2 vector(Point2 other) {
        return new Vector2(other.x - this.x, other.y - this.y);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.Color == null) return;

        int XCoordinate = (int) (offsetX + (this.x*scale) - (this.Radius*scale));
        int YCoordinate = (int) ((offsetY-(this.y*scale)) - (this.Radius*scale));

        g.setColor(this.Color);
        g.fillOval(XCoordinate, YCoordinate, (int)(Radius*2*scale), (int)(Radius*2*scale));

        g.setColor(Color.BLACK);
        g.drawOval(XCoordinate, YCoordinate, (int)(this.Radius*2*scale), (int)(this.Radius*2*scale));
    }
}
