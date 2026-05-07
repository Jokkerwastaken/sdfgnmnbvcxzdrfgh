package objects;

import classes.Point2V2;
import classes.Vector2V2;
import classes.contracts.Controllable;
import classes.contracts.Debugable;
import classes.contracts.Drawable;
import classes.contracts.Listable;
import classes.contracts.Movable;
import classes.contracts.Parentable;
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
    public InputHandler inputHandler;

    private boolean debug;

    public EntityV2(Point2V2 position, int Radius, Color color, float gravityApplied, GFrame frame) {
        this.frame = frame;

        // Ball representing the entity
        this.position = position;
        this.position.color = Color.BLUE;
        this.position.radius = Radius;
        this.position.parent = this;

        // Moving
        this.moveHandler = new MovingV2(this, gravityApplied);
        this.moveHandler.velocity = new Vector2V2(0, 0, null);        // Velocity
        this.moveHandler.velocity.color = Color.GREEN;

        this.moveHandler.appliedVectors.add(this.moveHandler.velocity);
        this.moveHandler.gravityNormalized = new Vector2V2(0, -1, null); // Gravity normal

        this.moveHandler.maxSpeed = 100;            // Extra needed operations
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
        this.position.setAlpha(alpha);
    }

    @Override
    public boolean inScreen(int offsetX, int offsetY, float scale) {
        return this.position.inScreen(offsetX, offsetY, scale);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        position.draw(g, offsetX, offsetY, scale);
        if (this.debug && inScreen(offsetX, offsetY, scale)) for (Vector2V2 elem : this.moveHandler.getVectors()) {
            elem.draw(g, (int)(offsetX-(this.position.x*scale)), (int)(offsetY+(this.position.y*scale)), scale);
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
        if (inputHandler.keyHandler.getKeyState(this.keyLeft)) this.moveHandler.velocity.x += 1;
        if (inputHandler.keyHandler.getKeyState(this.keyDown)) this.moveHandler.velocity.y += 1;
        if (inputHandler.keyHandler.getKeyState(this.keyRight)) this.moveHandler.velocity.x -= 1;
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
