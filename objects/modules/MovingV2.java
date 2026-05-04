package objects.modules;

import java.util.ArrayList;
import java.awt.Color;

import classes.Vector2;
import objects.EntityV2;

public class MovingV2 {
    private final EntityV2 parent;
    private final ArrayList<Vector2> appliedVectors;

    public Vector2 velocity, gravityNormalized;
    public float gravityApplied;
    public double airFriction;
    public double angle, angularVelocity;
    public float currentSpeed, maxSpeed;

    public MovingV2(EntityV2 parent, float gravityApplied) {
        this.parent = parent;
        this.maxSpeed = 50;
        this.currentSpeed = 0; 
        this.velocity = new Vector2(0, 0);

        this.angle = 0;             // In radians
        this.angularVelocity = 0;   // Radians per second
        
        this.gravityApplied = gravityApplied;
        this.airFriction = 0.3;

        // Almost purely for drawing purosses
        this.appliedVectors = new ArrayList<>();
        this.appliedVectors.add(this.velocity);                                                            // Moving Vector
        this.appliedVectors.add(new Vector2(0*gravityApplied, -1*gravityApplied, Color.PINK, null));      // Gravity Vector
        this.appliedVectors.add(new Vector2(10, 0, Color.YELLOW, null));                      // Heading vector
        
        for (Vector2 elem : appliedVectors) elem.parent = this.parent;
    }

    public ArrayList<Vector2> getVectors() {
        return appliedVectors;
    }


    // Movable contract
    private void friction(float deltaTime) {
        if (this.velocity.x == 0 && this.velocity.y == 0) return;

        double frictionFactor = 1.0 / (1.0 + (deltaTime * airFriction));

        this.velocity.x *= frictionFactor;
        this.velocity.y *= frictionFactor;

        if (Math.abs(this.velocity.x) < 0.001) this.velocity.x = 0;
        if (Math.abs(this.velocity.y) < 0.001) this.velocity.y = 0;
    }

    // Checking the hypotenuse of the Velocity
    private void speedCheck() {
        double Hypotenuse = this.velocity.magnitude();
        if (Hypotenuse < maxSpeed) return;

        this.velocity.x = (this.velocity.x / Hypotenuse) * maxSpeed;
        this.velocity.y = (this.velocity.y / Hypotenuse) * maxSpeed;
    }

    private Vector2 constructGravity() {
        return new Vector2(gravityApplied * gravityNormalized.x, gravityApplied * gravityNormalized.y);
    }

    public void move(float deltaTime) {
        if (gravityApplied != 0) this.velocity.add(constructGravity(), deltaTime);

        friction(deltaTime);
        speedCheck();

        angle += angularVelocity * deltaTime;
        this.parent.position.x += (this.velocity.x * deltaTime);
        this.parent.position.y += (this.velocity.y * deltaTime);
    }
}
