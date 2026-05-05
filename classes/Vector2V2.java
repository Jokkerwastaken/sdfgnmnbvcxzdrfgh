package classes;

import java.awt.Color;
import java.awt.Graphics;

import classes.contracts.Drawable;
import classes.contracts.Parentable;
import modules.GFrame;

public class Vector2V2 implements Drawable {
    private GFrame frame;
    public Parentable parent;

    public double x, y, screenX, screenY;
    public Color color;
    private float alpha;

    public Vector2V2(double x, double y, GFrame frame) {
        this.x = x;
        this.y = y;

        if (frame == null) return;
        this.frame = frame;
        frame.canvas.addDrawable(this);
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2 normalize() {
        double mag = magnitude();
        if (mag <= 1e-9) return new Vector2(0, 0);
        return new Vector2(this.x / mag, this.y / mag);
    }
    

    public double dotProduct(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }


    // Drawable contract
    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public boolean inScreen() {
        boolean inX = (this.screenX+(this.x * this.frame.canvas.Scale) >= 0 && this.screenX+(this.x * this.frame.canvas.Scale) <= this.frame.width);
        boolean inY = (this.screenY+(this.y * this.frame.canvas.Scale) >= 0 && this.screenY-(this.y * this.frame.canvas.Scale) <= this.frame.height);

        return inX && inY;
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.color == null) return;

        this.screenX = offsetX; // Beginning points
        this.screenY = offsetY;

        if (!inScreen()) return;        

        int width = offsetX + (int)(this.x*scale);
        int height = offsetY - (int)(this.y*scale);

        g.setColor(this.color);
        g.drawLine((int)screenX, (int)screenY, width, height);
    }
}
