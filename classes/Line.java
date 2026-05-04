package classes;

import classes.contracts.Drawable;
import java.awt.Color;
import java.awt.Graphics;
import modules.Canvas;

public class Line implements Drawable {
    public double x, y;
    public int screenX, screenY;
    private Color Color;
    public float alpha;
    private float LengthX, LengthY;

    public Line (Point2 Position, Vector2 AimedLength, Color Color, Canvas Canvas) {
        this.x = Position.x;
        this.y = Position.y;

        this.LengthX = (float) AimedLength.x;
        this.LengthY = (float) AimedLength.y;

        this.Color = Color;
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
        //return (screenX);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        double x2, y2;

        x2 = this.x + this.LengthX;
        y2 = this.y + this.LengthY;
    
        this.screenX = (int)(offsetX + this.x * scale);
        this.screenY = (int)(offsetY - this.y * scale);

        int width = (int)(offsetX + x2 * scale);
        int height = (int)(offsetY - y2 * scale);

        g.setColor(new Color(this.Color.getRed(), this.Color.getGreen(), this.Color.getBlue(), (int)(Color.getAlpha()*this.alpha)));
        g.drawLine(screenX, screenY, width, height);
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
