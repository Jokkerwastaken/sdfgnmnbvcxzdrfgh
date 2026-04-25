package objects;

import classes.Point2;
import classes.Vector2;
import classes.contracts.Drawable;
import classes.contracts.Movable;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Entity implements Drawable, Movable {
    public Point2 Position;
    public Vector2 Velocity, Gravity;
    public ArrayList<Vector2> AppliedVectors;
    public double AirTraction;

    public boolean debug = true;

    public Entity(Point2 position, Vector2 velocity, Color color) {
        this.Position = position;
        this.Velocity = velocity;
        this.Gravity = new Vector2(0, -15, Color.PINK);

        this.AppliedVectors = new ArrayList<>();
        this.AppliedVectors.add(this.Velocity); // Moving Vector
        this.AppliedVectors.add(this.Gravity);  // Gravity Vector

        this.AirTraction = 0.1;
        this.Position.Color = color;
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        Position.draw(g, offsetX, offsetY);
        if (debug) {
            for (Vector2 elem : AppliedVectors) {
                elem.draw(g, offsetX + (int)this.Position.x, offsetY - (int)this.Position.y);
            }
        }
    }

    private void friction(float deltaTime) {
        if (Velocity.x == 0 && Velocity.y == 0) return;

        double frictionFactor = 1.0 / (1.0 + (deltaTime * AirTraction));

        this.Velocity.x *= frictionFactor;
        this.Velocity.y *= frictionFactor;

        if (Math.abs(Velocity.x) < 0.001) Velocity.x = 0;
        if (Math.abs(Velocity.y) < 0.001) Velocity.y = 0;

        //System.out.printf("Velocity: x: %.8f, y: %.8f\n", Velocity.x, Velocity.y);
    }

    @Override
    public void move(float deltaTime) {
        //this.Velocity.y -= 9.8 * deltaTime; // Gravity
        //this.Velocity.subtract(this.AppliedVectors.get(1), deltaTime);
        this.AppliedVectors.get(0).add(this.AppliedVectors.get(1), deltaTime);

        friction(deltaTime);

        this.Position.x += this.Velocity.x * deltaTime;
        this.Position.y += this.Velocity.y * deltaTime; 
    }
}