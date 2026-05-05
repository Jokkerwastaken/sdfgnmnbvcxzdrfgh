package classes;

import classes.contracts.Drawable;
import java.awt.Color;
import java.awt.Graphics;
import modules.Canvas;

public class Line implements Drawable {
    public int screenX, screenY;
    private Color color;
    public float alpha;
    private double x1, y1, x2, y2;

    public Line (Point2V2 position1, Point2V2 position2, Color Color, Canvas Canvas) {
        this.x1 = position1.x;
        this.y1 = position1.y;

        this.x2 = position2.x;
        this.y2 = position2.y;

        this.color = Color;
        this.alpha = 1f;

        if (Canvas != null) Canvas.addDrawable(this);
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    
    @Override
    public boolean inScreen() {
        return true;
    }

    //@Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        int screenX1 = (int)(offsetX+(this.x1*scale));
        int screenY1 = (int)(offsetY-(this.y1*scale));

        int screenX2 = (int)(offsetX+(this.x2*scale));
        int screenY2 = (int)(offsetY-(this.y2*scale));
        
        g.setColor(color);
        g.drawLine(screenX1, screenY1, screenX2, screenY2);
    }
    
    /* //Fun zone
    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.color == null) return;

        screenX = (int) (offsetX+(this.x*scale));
        screenY = (int) ((offsetY-(this.y*scale)));

        g.setColor(this.color);
        g.drawLine(screenX, screenY, (int)((screenX+this.x)*scale), (int)((screenY+this.y)*scale));
    }
    */
}
