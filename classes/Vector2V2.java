package classes;

import modules.window.GFrame;

public class Vector2V2 {
    public double x, y;

    public Vector2V2(double x, double y, GFrame frame) {
        this.x = x;
        this.y = y;
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2V2 normalize() {
        double mag = magnitude();
        if (mag <= 1e-9) return new Vector2V2(0, 0, null);
        return new Vector2V2(this.x / mag, this.y / mag, null);
    }

    public double dotProduct(Vector2V2 other) { // skalaarkorrutis
        return this.x * other.x + this.y * other.y;
    }
}
