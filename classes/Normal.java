package classes;

import classes.contracts.Drawable;
import java.awt.Color;
import java.awt.Graphics;

public class Normal implements Drawable {
    public double x, y;

    public Normal (Vector2 vector) {
        this.x = vector.normalize().y *-1;
        this.y = vector.normalize().x;
    }

    public Vector2 vectorize() {
        return new Vector2(this.x, this.y);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        g.setColor(Color.RED);
        g.drawLine(0 + offsetX, 0 + offsetY, (int)((offsetX + this.x)*scale), (int)((offsetY - this.y)*scale));
    }
}