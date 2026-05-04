package modules;

import classes.contracts.Controllable;
import classes.contracts.Drawable;
import classes.contracts.Movable;
import loops.PhysicsLoop;
import modules.inputs.*;
import modules.interfaces.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    // Canvas stuff
    public int width, height, centerX, centerY;
    public float Scale = 1f;
    public int OffsetX, OffsetY;
    public final boolean debug = true;

    // Interfaces
    private final Pause pause;
    public FPS fps;

    // Input handler;
    private final InputHandler inputHandler;

    // Threads
    public PhysicsLoop phyThread;

    // Physics
    public float P_deltaTime;

    // Lists
    public final List<Controllable> controllable = new CopyOnWriteArrayList<>();
    private final List<Movable> movable = new CopyOnWriteArrayList<>();
    private final List<Drawable> drawable = new CopyOnWriteArrayList<>();

    public Canvas (int width, int height) {
        this.width = width;
        this.height = height;

        this.centerX = width/2;
        this.centerY = height/2;

        this.OffsetX = 0;
        this.OffsetY = 0;

        this.pause = new Pause(this);
        this.fps = new FPS();

        this.inputHandler = new InputHandler();
    }


    public void addInputHandlers(KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.addKeyListener(this.inputHandler.keyHandler);
        this.addMouseListener(this.inputHandler.mouseHandler);
        this.addMouseMotionListener(this.inputHandler.mouseHandler);
    }

    public void getInputs(PhysicsLoop gameLoop) {
        if (this.pause.framesFromPause < 20) {
            this.pause.framesFromPause++;
            return;
        }
        if (this.inputHandler.keyHandler.getKeyState(KeyEvent.VK_ESCAPE)) {
            this.pause.paused = !this.pause.paused;
            this.pause.framesFromPause = 0;
        }

        this.phyThread.paused = this.pause.paused;
    }


    // Adding to lists
    public void addControllable(Controllable obj) {
        obj.setInputHandler(this.inputHandler);
        controllable.add(obj);
    }

    public void addDrawable(Drawable obj) {
        drawable.add(obj);
    }

    public void addMovable(Movable obj) {
        movable.add(obj);
    }



    public void moveMovable(float deltaTime) {
        for (Movable obj : movable) obj.move(deltaTime);
    }



    public void fpsManager() {
        this.fps.manageFPS();
    }


    @Override
    public void paintComponent(Graphics g) {
        this.centerX = this.width/2 + this.OffsetX;
        this.centerY = this.height/2 + this.OffsetY;

        super.paintComponent(g);
        
        setBackground(Color.BLACK);

        float A = pause.paused ? 0.2f : 1f;

        g.setColor(new Color(255, 255, 255, (int)(255 * A)));
        g.fillRect(0,0,width,height);
        
        for (Drawable obj : this.drawable) try {
            obj.setAlpha(A);
            obj.draw(g, centerX + this.OffsetX, centerY + this.OffsetY, this.Scale);
        } catch (Exception e) {}

        // UI
        pause.draw(g);

        fps.draw(g);
    }
}