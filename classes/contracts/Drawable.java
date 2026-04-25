package classes.contracts;

import java.awt.Graphics;

public interface Drawable {
    public void draw(Graphics g, int offsetX, int offsetY, float scale);
}