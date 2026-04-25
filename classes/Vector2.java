package classes;

import classes.contracts.Drawable;
import java.awt.Color;
import java.awt.Graphics;

public class Vector2 implements Drawable {
    public double x, y;
    public Color Color;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.Color = color;
    }

    private double magnitude() {
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

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 multiply(Vector2 other) {
        return new Vector2(this.x * other.x, this.y * other.y);
    }

    public Vector2 divide(Vector2 other) {
        if (other.x == 0 || other.y == 0) throw new ArithmeticException("Division by zero");
        return new Vector2(this.x / other.x, this.y / other.y);
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
        this.x = this.y * (other.x * deltaTime);
        this.y = this.y * (other.y * deltaTime);
    }

    public void divide(Vector2 other, float deltaTime) {
        if (other.x == 0 || other.y == 0 || deltaTime == 0) throw new ArithmeticException("Division by zero");
        this.x = this.y / (other.x * deltaTime);
        this.y = this.y / (other.y * deltaTime);
    }


    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        if (this.Color == null) return;
        g.setColor(this.Color);
        // Draws a line from the offset (origin) to the vector position
        g.drawLine(offsetX, offsetY, (int)(offsetX + this.x), (int)(offsetY - this.y));
    }
}