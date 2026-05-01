package classes;

import classes.contracts.Drawable;
import java.awt.Color;
import java.awt.Graphics;

public class Vector2 implements Drawable {
    public double x, y;
    public Color Color;
    public float alpha;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.Color = color;
        this.alpha = this.Color.getAlpha();
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2 normalize() {
        float mag = (float) magnitude();
        if (mag <= 1e-9) return new Vector2(0, 0);
        return new Vector2(this.x / (double) mag, this.y / (double) mag);
    }
    

    public double dotProduct(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }


    public void add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void subtract(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void multiply(Vector2 other) {
        this.x *= other.x;
        this.y *= other.y;
    }

    public void divide(Vector2 other) {
        if (other.x == 0 || other.y == 0) throw new ArithmeticException("Division by zero");
        this.x /= other.x;
        this.y /= other.y;
    }

    
    public void add(Vector2 other, float deltaTime) {
        this.x += other.x * deltaTime;
        this.y += other.y * deltaTime;
    }

    public void subtract(Vector2 other, float deltaTime) {
        this.x -= other.x * deltaTime;
        this.y -= other.y * deltaTime;
    }

    public void multiply(Vector2 other, float deltaTime) {
        this.x *= other.x * deltaTime;
        this.y *= other.y * deltaTime;
    }

    public void divide(Vector2 other, float deltaTime) {
        if (other.x == 0 || other.y == 0 || deltaTime == 0) throw new ArithmeticException("Division by zero");
        this.x /= other.x * deltaTime;
        this.y /= other.y * deltaTime;
    }


    public void add(float value) {
        this.x += value;
        this.y += value;
    }

    public void subtract(float value) {
        this.x -= value;
        this.y -= value;
    }

    public void multiply(float value) {
        this.x *= value;
        this.y *= value;
    }

    public void divide(float value) {
        if (value == 0) throw new ArithmeticException("Division by zero");
        this.x /= value;
        this.y /= value;
    }

    
    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.Color == null) return;

        int width = (int)(offsetX + (this.x*scale));
        int height = (int)(offsetY - (this.y*scale));

        System.out.println(this.Color.getAlpha());
        g.setColor(new Color(this.Color.getRed(), this.Color.getGreen(), this.Color.getBlue(), (int)(Color.getAlpha()*this.alpha)));
        System.out.println(g.getColor().getAlpha());
        
        // Draws a line from the offset (origin) to the vector position
        g.drawLine(offsetX, offsetY, width, height);
    }
}