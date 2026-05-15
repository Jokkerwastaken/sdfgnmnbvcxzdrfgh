package objects.modules;

import java.awt.Color;
import java.util.ArrayList;

import classes.Vector2V2;
import classes.geometry.Vector;
import objects.EntityV2;

public class MovingV2 {
    private final EntityV2 parent;
    public final ArrayList<Vector2V2> appliedVectors;
    public final ArrayList<Vector> vectors;

    public Vector velocity, gravity, gravityNormalized;
    public float gravityApplied;
    public double airFriction;
    public double angle, angularVelocity;
    public float currentSpeed, maxSpeed;

    public MovingV2(EntityV2 parent, float gravityApplied) {
        this.parent = parent;
        this.maxSpeed = 50;
        this.currentSpeed = 0;

        this.angle = 0;             // In radians
        this.angularVelocity = 0;   // Radians per second
        
        this.gravityApplied = gravityApplied;
        this.airFriction = 0.3;

        // Almost purely for drawing purosses
        this.appliedVectors = new ArrayList<>();
        this.vectors = new ArrayList<>();
    }

    public void finalizeAppliedVectors() {
        this.vectors.add(new Vector((int)(gravityNormalized.vector.x*gravityApplied), 
                                    (int)(gravityNormalized.vector.y*gravityApplied)));
        this.vectors.get(1).color = Color.PINK;
    
        // Heading vector
        this.vectors.add(new Vector(10, 0));
        this.vectors.get(2).color = Color.YELLOW;
    }

    public void setChildren() {
        for (Vector elem : vectors) elem.parent = this.parent;
    }

    public ArrayList<Vector> getVectors() {
        return vectors;
    }

    // Movable contract
    private void friction(float deltaTime) {
        if (this.velocity.vector.x == 0 && this.velocity.vector.y == 0) return;

        double frictionFactor = 1.0 / (1.0 + (deltaTime * airFriction));

        this.velocity.vector.x *= frictionFactor;
        this.velocity.vector.y *= frictionFactor;

        if (Math.abs(this.velocity.vector.x) < 0.001) this.velocity.vector.x = 0;
        if (Math.abs(this.velocity.vector.y) < 0.001) this.velocity.vector.y = 0;
    }

    // Checking the hypotenuse of the Velocity
    private void speedCheck() {
        double Hypotenuse = this.velocity.vector.magnitude();
        if (Hypotenuse < maxSpeed) return;

        this.velocity.vector.x = (this.velocity.vector.x / Hypotenuse) * maxSpeed;
        this.velocity.vector.y = (this.velocity.vector.y / Hypotenuse) * maxSpeed;
    }

    private void constructGravity() {
        gravity.vector.x = gravityApplied * gravityNormalized.vector.x;
        gravity.vector.y = gravityApplied * gravityNormalized.vector.y;
    }

    public void move(float deltaTime) {
        if (gravityApplied != 0 && gravity != null) {
            constructGravity();

            this.velocity.vector.x += gravity.vector.x * deltaTime;
            this.velocity.vector.y += gravity.vector.y * deltaTime;
        } 

        friction(deltaTime);
        speedCheck();

        this.parent.position.x += (this.velocity.vector.x * deltaTime);
        this.parent.position.y += (this.velocity.vector.y * deltaTime);
    }
}
