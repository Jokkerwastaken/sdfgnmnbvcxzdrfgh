package objects.modules;

import objects.Entity;

import java.awt.Color;
import java.util.ArrayList;

import classes.Vector2;

public class Moving {
    private final Entity parent;

    public Vector2 Velocity;

    // Angles
    private double Angle, AngularVelocity; 

    // Vectors
    public ArrayList<Vector2> AppliedVectors;
    private double AirFriction;
    private float maxSpeed = 50;

    private boolean gravityApplied;


    public Moving(Entity parent, Vector2 velocity, boolean gravityApplied) {
        this.parent = parent;

        this.Velocity = velocity;

        this.Angle = 0;             // In radians
        this.AngularVelocity = 0;   // Radians per second
        
        this.gravityApplied = gravityApplied;
        this.AirFriction = 0.3;

        this.AppliedVectors = new ArrayList<>();
        this.AppliedVectors.add(this.Velocity);                                   // Moving Vector
        if (gravityApplied) this.AppliedVectors.add(new Vector2(0, -15, Color.PINK));      // Gravity Vector
        this.AppliedVectors.add(new Vector2(10, 0, Color.YELLOW));                      // Heading vector
    }


    private void friction(float deltaTime) {
        if (Velocity.x == 0 && Velocity.y == 0) return;

        double frictionFactor = 1.0 / (1.0 + (deltaTime * AirFriction));

        Velocity.x *= frictionFactor;
        Velocity.y *= frictionFactor;

        if (Math.abs(Velocity.x) < 0.001) Velocity.x = 0;
        if (Math.abs(Velocity.y) < 0.001) Velocity.y = 0;
    }

    // Checking the hypotenuse of the Velocity
    private void speedCheck() {
        double Hypotenuse = Velocity.magnitude();
        if (Hypotenuse < maxSpeed) return;

        Velocity.x = (Velocity.x / Hypotenuse) * maxSpeed;
        Velocity.y = (Velocity.y / Hypotenuse) * maxSpeed;
    }


    public void move(float deltaTime) {
        if (gravityApplied) Velocity.add(AppliedVectors.get(1), deltaTime);

        friction(deltaTime);
        speedCheck();

        Angle += AngularVelocity * deltaTime;
        this.parent.Position.x += (Velocity.x * deltaTime);
        this.parent.Position.y += (Velocity.y * deltaTime);
    }
}
