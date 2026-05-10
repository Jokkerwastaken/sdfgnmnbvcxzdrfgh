package classes;

import classes.contracts.Drawable;
import classes.contracts.Listable;
import classes.contracts.Parentable;
import modules.window.GFrame;

import java.awt.Color;
import java.awt.Graphics;

public class Vector2V2 implements Drawable, Listable {
    private GFrame frame;
    public Parentable parent;

    public double x, y;
    public Color color;
    private float alpha, lastAlpha;

    public Vector2V2(double x, double y, GFrame frame) {
        this.x = x;
        this.y = y;

        if (frame == null) return;
        this.frame = frame;
        frame.canvas.addToLists(this);
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2V2 normalize() {
        double mag = magnitude();
        if (mag <= 1e-9) return new Vector2V2(0, 0, null);
        return new Vector2V2(this.x / mag, this.y / mag, null);
    }

    public double dotProduct(Vector2V2 other) { // skalaarkorrutis
        return this.x * other.x + this.y * other.y;
    }


    // Drawable contract
    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public boolean inScreen(int offsetX, int offsetY, float scale) {
        if (frame == null) return true;

        boolean in1 =   (offsetX >= 0 && offsetX <= this.frame.canvas.width) &&
                        (offsetY >= 0 && offsetY <= this.frame.canvas.height);

        boolean in2 =   (offsetX-(this.x * scale) >= 0 && offsetX-(this.x * scale) <= this.frame.canvas.width) &&
                        (offsetY+(this.y * scale) >= 0 && offsetY+(this.y * scale) <= this.frame.canvas.height);

        return in1 || in2;
    }
    
    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.color == null || this.parent == null) return;

        if (lastAlpha != alpha) {
            lastAlpha = alpha;
            this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.alpha);
        }

        g.setColor(this.color);
        g.drawLine((int)offsetX, (int)offsetY, (int)(offsetX+this.x), (int)(offsetY+this.y));
    }
}
