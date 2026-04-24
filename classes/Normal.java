package classes;

import classes.contracts.Drawable;
import java.awt.Color;
import java.awt.Graphics;

public class Normal implements Drawable {
    public double x, y;

    public Normal (Vector2 vector) {
        this.x = vector.normalize().y * -1;
        this.y = vector.normalize().x;
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.RED);
        g.drawLine(0 + offsetX, 0 + offsetY, (int)(this.x + offsetX), (int)(this.y + offsetY));
    }
}