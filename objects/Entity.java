package objects;

import classes.Point2;
import classes.Vector2;
import classes.contracts.Drawable;
import classes.contracts.Movable;
import modules.GFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Entity implements Drawable, Movable {
    public Point2 Position;
    private Vector2 Velocity, Gravity;

    private Vector2 Heading;
    private double Angle, AngularVelocity; 

    private ArrayList<Vector2> AppliedVectors;
    private double AirTraction;
    private float maxSpeed = 50;

    private boolean debug = true;
    private float Scale;

    public Entity(Point2 position, Vector2 velocity, Color color, GFrame frame) {
        this.Position = position;
        this.Velocity = velocity;

        this.Angle = 0; // In radians
        this.AngularVelocity = 0;

        this.AppliedVectors = new ArrayList<>();
        this.AppliedVectors.add(this.Velocity);                                 // Moving Vector
        this.AppliedVectors.add(new Vector2(0, -15, this.Scale, Color.PINK));   // Gravity Vector
        this.AppliedVectors.add(new Vector2(10, 0, this.Scale, Color.YELLOW));  // Heading vector

        this.AirTraction = 0.1;
        this.Position.Color = color;
        this.Scale = 1;
        
        // Adds itself to canvas's lists just because
        if (frame == null) return;
        frame.canvas.addMovable(this);
        frame.canvas.addDrawable(this);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        Position.draw(g, offsetX, offsetY, scale);
        if (debug) {
            for (Vector2 elem : AppliedVectors) {
                elem.draw(g, offsetX + (int)this.Position.x,
                             offsetY - (int)this.Position.y, scale);
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
    }

    // Checking the hypotenuse of the Velocity
    private void speedCheck() {
        double Hypotenuse = Velocity.magnitude();
        if (Hypotenuse < (maxSpeed)) return;

        Velocity.x = (Velocity.x / Hypotenuse) * maxSpeed;
        Velocity.y = (Velocity.y / Hypotenuse) * maxSpeed;
    }

    @Override
    public void move(float deltaTime) {
        this.Velocity.add(this.Gravity, deltaTime);

        friction(deltaTime);
        speedCheck();

        this.Angle += this.AngularVelocity * deltaTime;
        this.Position.x += (this.Velocity.x * deltaTime) * this.Scale;
        this.Position.y += (this.Velocity.y * deltaTime) * this.Scale; 
    }
}