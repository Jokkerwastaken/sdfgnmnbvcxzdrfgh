package objects;

import classes.Point2;
import classes.Vector2;
import classes.contracts.Drawable;
import classes.contracts.Movable;
import classes.contracts.Collidable;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;

public class Prop implements Drawable, Movable, Collidable {
    // Properties
    private Point2 Position;
    private float Radius;
    private Color Color;

    // Forces
    private Vector2 Velocity, Gravity;
    private double AirResistance;
    private ArrayList<Vector2> AppliedVectors;

    // Rotation
    private Vector2 Heading;
    private double Angle, AngularVelocity;

    private boolean debug = true;

    public Prop(Point2 Position, float Radius, Vector2 Velocity, Color Color) {
        this.Position = Position;
        this.Velocity = Velocity;
        this.Gravity = new Vector2(0, -15, Color.PINK);

        this.Angle = 0; // In radians
        this.AngularVelocity = 0;
        this.Heading = new Vector2(10, 0, Color.YELLOW);

        this.AppliedVectors = new ArrayList<>();
        this.AppliedVectors.add(this.Velocity); // Moving Vector
        this.AppliedVectors.add(this.Gravity);  // Gravity Vector
        this.AppliedVectors.add(this.Heading);  // Heading vector

        this.AirResistance = 0.1;
        this.Color = Color;
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        g.setColor(Color);
        g.drawRect((int)((offsetX + this.Position.x) - (Radius*scale)), (int)((offsetY - this.Position.y) - (Radius*scale)), (int)(Radius*2*scale), (int)(Radius*2*scale));
        
        if (debug) {
            for (Vector2 elem : AppliedVectors) {
                elem.draw(g, offsetX + (int)this.Position.x, offsetY - (int)this.Position.y, scale);
            }
        }
    }

    @Override
    public void move(float deltaTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void checkForCollision() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
