package objects;

import classes.Point2V2;
import classes.Vector2V2;
import classes.contracts.Controllable;
import classes.contracts.Debugable;
import classes.contracts.Drawable;
import classes.contracts.Listable;
import classes.contracts.Movable;
import classes.contracts.Parentable;
import classes.geometry.Circle;
import modules.GFrame;
import modules.inputs.InputHandler;
import objects.modules.MovingV2;

import java.awt.Color;
import java.awt.Graphics;

public class EntityV2 implements Drawable, Movable, Controllable, Parentable, Debugable, Listable {
    private final GFrame frame;
    public final MovingV2 moveHandler;
    private int keyForward, keyLeft, keyDown, keyRight;
    public final Point2V2 position;
    public final Circle projection;
    public InputHandler inputHandler;

    private boolean debug;

    public EntityV2(Point2V2 position, int radius, Color color, float gravityApplied, GFrame frame) {
        this.frame = frame;
        this.position = position;

        // Ball representing the entity
        this.projection = new Circle((int)position.x, (int)position.y, null);
        this.projection.color = color;
        this.projection.radius = radius;
        this.projection.parent = this;

        // Moving
        this.moveHandler = new MovingV2(this, gravityApplied);
            // Velocity
        this.moveHandler.velocity = new Vector2V2(0, 0, null);        
        this.moveHandler.velocity.color = Color.GREEN;
            // Gravity normal
        this.moveHandler.appliedVectors.add(this.moveHandler.velocity);
        this.moveHandler.gravityNormalized = new Vector2V2(0, -1, null); 
            // Extra needed operations
        this.moveHandler.maxSpeed = 100;            
        this.moveHandler.finalizeAppliedVectors();
        this.moveHandler.setChildren();

        // Adding to lists
        this.frame.canvas.addToLists(this);

        this.debug = this.frame.canvas.debug;
    }

    public void makeControllable(int keyForward, int keyLeft, int keyDown, int keyRight) throws Exception {
        if (this.frame == null) throw new Exception("Cannot access frame to make this entity controllable!");

        this.keyForward = keyForward;
        this.keyLeft = keyLeft;
        this.keyDown = keyDown;
        this.keyRight = keyRight;
        
        this.frame.canvas.addControllable(this);
    }


    // Drawable contract
    @Override
    public void setAlpha(float alpha) {
        this.projection.setAlpha(alpha);
    }

    @Override
    public boolean inScreen(int offsetX, int offsetY, float scale) {
        return this.projection.inScreen(offsetX, offsetY, scale);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        projection.draw(g, offsetX, offsetY, scale);

        if (this.debug && inScreen(offsetX, offsetY, scale)) 
            for (Vector2V2 elem : this.moveHandler.getVectors()) {
                int OffsetX = (int)(offsetX+(this.position.x*scale) - (this.projection.radius*scale));
                int OffsetY = (int)(offsetY+(this.position.y*scale) - (this.projection.radius*scale));

                elem.draw(g, OffsetX, OffsetY, scale);
            }
    }
    

    // Movable contract
    @Override
    public void move(float deltaTime) {
        if (this.inputHandler != null) input();
        moveHandler.move(deltaTime);
    }

    private void input() {
        if (inputHandler.keyHandler.getKeyState(this.keyForward)) this.moveHandler.velocity.y -= 1;
        if (inputHandler.keyHandler.getKeyState(this.keyLeft)) this.moveHandler.velocity.x -= 1;
        if (inputHandler.keyHandler.getKeyState(this.keyDown)) this.moveHandler.velocity.y += 1;
        if (inputHandler.keyHandler.getKeyState(this.keyRight)) this.moveHandler.velocity.x += 1;
    }


    // Controllable contract
    @Override
    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public void deburger(boolean debug) {
        this.debug = debug;
    }
}
