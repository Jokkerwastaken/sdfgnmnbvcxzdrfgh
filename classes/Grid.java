package classes;

import classes.contracts.Drawable;
import modules.GFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Grid implements Drawable {
    private ArrayList<Line> objects = new ArrayList<>();

    public Grid(GFrame frame) {
        frame.canvas.addDrawable(this);

        for (int x = -160; x < 160; x++) for (int y = -120; y < 120; y++) {
            objects.add(new Line(new Point2V2(x*50, y*50, null), new Point2V2(x*50+50, 0, null), Color.lightGray, frame.canvas));
            objects.add(new Line(new Point2V2(x*50, y*50, null), new Point2V2(0, y*50+50, null), Color.lightGray, frame.canvas));
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
