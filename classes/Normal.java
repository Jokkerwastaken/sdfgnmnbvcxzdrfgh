package classes;

import classes.contracts.Drawable;
import java.awt.Color;
import java.awt.Graphics;

public class Normal implements Drawable {
    public double x, y;
    private float Scale;
    private Color Color;

    public Normal (Vector2 vector, float scale) {
        this.x = vector.normalize().y *-1;
        this.y = vector.normalize().x;

        this.Scale = scale;
    }

    public Normal (Vector2 vector, float scale, Color color) {
        this.x = vector.normalize().y *-1;
        this.y = vector.normalize().x;

        this.Scale = scale;
        this.Color = color;
    }

    public Vector2 vectorize() {
        return new Vector2(this.x, this.y, this.Scale);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.Scale == 0 && scale > 1e-9) this.Scale = scale;
        if (this.Scale != scale) this.Scale = scale;

        if (this.Color == null) return;

        g.setColor(this.Color);
        g.drawLine(offsetX, offsetY,
                    (int)((offsetX + this.x)*this.Scale),
                    (int)((offsetY - this.y)*this.Scale));
    }
}