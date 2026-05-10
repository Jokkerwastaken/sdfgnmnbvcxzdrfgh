package classes.geometry;

import classes.Point2V2;
import classes.contracts.Drawable;
import classes.contracts.Listable;
import modules.window.GFrame;

import java.awt.Color;
import java.awt.Graphics;

public class Line implements Drawable, Listable {
    private GFrame frame;
    public Point2V2 position1, position2;
    public Color color;
    private float alpha, lastAlpha;

    public Line(Point2V2 p1, Point2V2 p2, GFrame frame) {
        this.position1 = p1;
        this.position2 = p2;
        
        if (frame == null) return;
        this.frame = frame;
        this.frame.canvas.addToLists(this);
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
        if (this.color == null) return;

        if (lastAlpha != alpha) {
            lastAlpha = alpha;
            this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.alpha);
        }
        g.setColor(this.color);

        double upper = position2.y - position1.y;
        double bottom = position2.x - position1.x;

        double k = upper/bottom;

        for (int x = 0; x < upper*scale; x++) {
            int y = (int)(k*(x-position1.x)+position1.y);   

            g.fillRect(offsetX+x, offsetY+y, 1, 1);
        }
    }
}
