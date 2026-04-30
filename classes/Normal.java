package classes;

import classes.contracts.Drawable;
import java.awt.Color;
import java.awt.Graphics;

public class Normal implements Drawable {
    public final Vector2 Vector;

    public Normal (Vector2 vector, float scale) {
        this.Vector = new Vector2(vector.normalize().y *-1, vector.normalize().x);
    }

    public Normal (Vector2 vector, float scale, Color color) {
        this.Vector = new Vector2(vector.normalize().y *-1, vector.normalize().x);
        this.Vector.Color = color;
    }

    public Vector2 vectorize() {
        return new Vector2(this.Vector.x, this.Vector.y);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        Vector.draw(g, offsetX, offsetY, scale);
    }
}