package classes.interfaces;

import classes.contracts.DrawableInterface;
import classes.Text;
import classes.contracts.Listable;
import modules.window.CanvasV2;

import java.awt.Graphics;
import java.awt.Color;

public class Button implements DrawableInterface, Listable {
    public Text text;
    public int x, y, width, height;
    public int cornerRadius;
    public Color color;

    public Button(CanvasV2 canvas) {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.text = new Text();

        if (canvas != null) canvas.addToLists(this);
    }

    public void setText(String text, float textSize, Color color) {
        this.text.string = text;
        this.text.textSize = textSize;
        this.text.color = color;
    }

    @Override
    public void draw(Graphics g) {
        if (color == null) return;

        System.out.println("Hello");

        if (cornerRadius > 0) g.drawRoundRect(x, y, width, height, cornerRadius, cornerRadius);
            else g.drawRect(x, y, width, height);

        if (this.text != null) {
            g.setColor(this.text.color);
            g.setFont(g.getFont().deriveFont(this.text.textSize));

            int textWidth = g.getFontMetrics().stringWidth(this.text.string);
            int x = (this.width - textWidth) / 2;
            int y = (int)(this.height * 0.31);

            g.drawString(this.text.string, x, y);
        }
    }
}