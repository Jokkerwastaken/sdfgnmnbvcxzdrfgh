package classes.contracts;

import java.awt.Graphics;

public interface Drawable {
    public void setAlpha(float alpha);
    public boolean inScreen(int offsetX, int offsetY, float scale);
    public void draw(Graphics g, int offsetX, int offsetY, float scale);
}