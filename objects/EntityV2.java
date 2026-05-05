package objects;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;

import classes.Point2V2;
import classes.Vector2V2;
import objects.modules.MovingV2;

import classes.contracts.Controllable;
import classes.contracts.Parentable;
import classes.contracts.Drawable;
import classes.contracts.Movable;

import modules.GFrame;
import modules.inputs.InputHandler;

public class EntityV2 implements Drawable, Movable, Controllable, Parentable {
    private final GFrame frame;
    public final MovingV2 moveHandler;
    public final Point2V2 position;
    public InputHandler inputHandler;

    private boolean debug;

    public EntityV2(Point2V2 position, int Radius, Color color, float gravityApplied, GFrame frame) {
        this.frame = frame;
        this.position = position;
        this.position.color = Color.BLUE;
        this.position.radius = Radius;
        this.position.parent = this;

        // Moving
        this.moveHandler = new MovingV2(this, gravityApplied);
        this.moveHandler.velocity = new Vector2V2(0, 0, frame);
        this.moveHandler.appliedVectors.add(this.moveHandler.velocity);
        this.moveHandler.gravityNormalized = new Vector2V2(1, 1, frame);
        this.moveHandler.maxSpeed = 100;
        this.moveHandler.setChildren();

        // Adding to lists
        this.frame.canvas.addMovable(this);
        this.frame.canvas.addDrawable(this);

        this.debug = this.frame.canvas.debug;
    }

    public void makeControllable() throws Exception {
        if (this.frame == null) throw new Exception("Cannot access frame to make this entity controllable!");
        this.frame.canvas.addControllable(this);
        
    }


    // Drawable contract
    @Override
    public void setAlpha(float alpha) {
        this.position.setAlpha(alpha);
    }

    @Override
    public boolean inScreen() {
        return this.position.inScreen();
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        position.draw(g, offsetX, offsetY, scale);
        if (debug) for (Vector2V2 elem : moveHandler.getVectors()) {
            elem.draw(g, offsetX, offsetY, scale);
        }
    }
    

    // Movable contract
    @Override
    public void move(float deltaTime) {
        if (this.inputHandler != null) input();
        moveHandler.move(deltaTime);
    }

    private void input() {
        if (inputHandler.keyHandler.getKeyState(KeyEvent.VK_D)) this.moveHandler.velocity.x -= 1;
        if (inputHandler.keyHandler.getKeyState(KeyEvent.VK_A)) this.moveHandler.velocity.x += 1;
        if (inputHandler.keyHandler.getKeyState(KeyEvent.VK_W)) this.moveHandler.velocity.y -= 1;
        if (inputHandler.keyHandler.getKeyState(KeyEvent.VK_S)) this.moveHandler.velocity.y += 1;
    }


    // Controllable contract
    @Override
    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
}
