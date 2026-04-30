package objects;

import classes.Point2;
import classes.Vector2;
import classes.contracts.Controllable;
import classes.contracts.Drawable;
import classes.contracts.Movable;
import loops.InputHandler;
import modules.GFrame;
import objects.modules.Moving;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Entity implements Drawable, Movable, Controllable {
    public Point2 Position;

    private final Moving moveHandler;
    private InputHandler inputHandler;

    private boolean debug;
    private float ScaleToFrame;

    public Entity(Point2 position, Vector2 velocity, int Radius, Color color, boolean gravityApplied, GFrame frame) {
        this.Position = position;
        this.moveHandler = new Moving(this, velocity, gravityApplied);

        this.Position.Color = color;
        this.Position.Radius = Radius;
        
        // Adds itself to canvas's lists just because
        if (frame == null) return;
        frame.canvas.addMovable(this);
        frame.canvas.addDrawable(this);

        this.debug = frame.canvas.debug;
    }


    public void makeControllable(GFrame frame) {
        frame.canvas.addControllable(this);
    }


    @Override
    public void draw(Graphics g, int offsetX, int offsetY, float scale) {
        if (this.ScaleToFrame != scale) this.ScaleToFrame = scale;

        int OffsetX = offsetX + (int)(this.Position.x*scale);
        int OffsetY = offsetY - (int)(this.Position.y*scale);

        Position.draw(g, OffsetX, OffsetY, scale);
        if (debug) for (Vector2 elem : moveHandler.AppliedVectors) {
            elem.draw(g, OffsetX, OffsetY, scale);
        }
    }

    
    @Override
    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }


    private void input() {
        if (inputHandler.keyHandler.getKeyState(KeyEvent.VK_D)) this.moveHandler.Velocity.x += 1;
        if (inputHandler.keyHandler.getKeyState(KeyEvent.VK_A)) this.moveHandler.Velocity.x -= 1;
        if (inputHandler.keyHandler.getKeyState(KeyEvent.VK_W)) this.moveHandler.Velocity.y += 1;
        if (inputHandler.keyHandler.getKeyState(KeyEvent.VK_S)) this.moveHandler.Velocity.y -= 1;
    }

    @Override
    public void move(float deltaTime) {
        if (this.inputHandler != null) input();
        moveHandler.move(deltaTime);
    }
}