package classes;

import classes.contracts.Drawable;
import modules.GFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Grid implements Drawable {
    private GFrame frame;
    private ArrayList<Line> objects = new ArrayList<>();

    public Grid(GFrame frame) {
        this.frame = frame;

        frame.canvas.addDrawable(this);

        for (int x = -160; x < 160; x++) for (int y = -120; y < 120; y++) {
            objects.add(new Line(new Point2(x*50, y*50), new Vector2(50, 0), Color.lightGray, null));
            objects.add(new Line(new Point2(x*50, y*50), new Vector2(0, 50), Color.lightGray, null));
        }
    }

    @Override
    public void setAlpha(float alpha) {}

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        for (Line obj : objects) if (obj.inScreen()) {
            obj.draw(g, offsetX, offsetY, scale);
        }
    }

    @Override
    public boolean inScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
