package classes;

import classes.contracts.Drawable;
import java.awt.Color;
import java.awt.Graphics;
import modules.Canvas;

public class Point2 implements Drawable {
    public double x, y;
    public int screenX, screenY;
    public Color Color;
    public int Radius;

    public Point2 (double x, double y) {
        this.x = x;
        this.y = y;

        this.Color = null;
        this.Radius = 0;
    }

    public Point2 (double x, double y, int radius, Color color) {
        this.x = x;
        this.y = y;

        this.Color = color;
        this.Radius = radius;
    }

    public Point2 (double x, double y, int radius, Color color, Canvas canvas) {
        this.x = x;
        this.y = y;

        this.Color = color;
        this.Radius = radius;

        canvas.addDrawable(this);
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
        if (this.Color == null || this.Radius == 0) return;

        screenX = (int) (offsetX - (this.Radius*scale));
        screenY = (int) (offsetY - (this.Radius*scale));

        int width = (int)(Radius*2*scale);
        int height = (int)(Radius*2*scale);

        g.setColor(this.Color);
        g.fillOval(screenX, screenY, width, height);

        g.setColor(Color.BLACK);
        g.drawOval(screenX, screenY, width, height);
    }
}
