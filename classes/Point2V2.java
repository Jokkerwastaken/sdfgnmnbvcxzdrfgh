package classes;

import java.awt.Graphics;

import classes.contracts.Drawable;
import classes.contracts.Listable;
import classes.contracts.Parentable;
import java.awt.Color;
import modules.GFrame;

public class Point2V2 implements Drawable, Listable {
    private GFrame frame;
    public Parentable parent;
    public double x, y, screenX, screenY;
    public float radius;
    public Color color;
    private float alpha;

    public Point2V2(double x, double y, GFrame frame) {
        this.x = x;
        this.y = y;

        if (frame == null) return;
        this.frame = frame;
        frame.canvas.addToLists(this);
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public boolean inScreen() {
        float scaledRadius = this.radius * this.frame.canvas.Scale;
        // Kontrolli, kas ringi servad jäävad raami sisse
        return (screenX + scaledRadius >= 0 && screenX - scaledRadius <= this.frame.canvas.width) &&
               (screenY + scaledRadius >= 0 && screenY - scaledRadius <= this.frame.canvas.height);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.color == null || this.radius == 0 || this.parent == null || this.frame == null) return;

        this.screenX = (double) (offsetX - (this.x*scale) - (this.radius*scale));
        this.screenY = (double) (offsetY + (this.y*scale) - (this.radius*scale));

        float size = (2*this.radius*scale);

        if (!inScreen()) return;

        g.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), (int)(color.getAlpha()*this.alpha)));
        g.fillOval((int)screenX, (int)screenY, (int)size, (int)size);


        g.setColor(Color.BLACK);
        g.drawOval((int)screenX, (int)screenY, (int)size, (int)size);
    }
}
