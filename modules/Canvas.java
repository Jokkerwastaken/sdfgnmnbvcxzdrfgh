package modules;

import classes.contracts.*;
import loops.PhysicsLoop;
import modules.inputs.*;
import modules.interfaces.*;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    // Canvas stuff
    public int width, height, centerX, centerY;
    public float Scale = 1f;
    public int OffsetX, OffsetY;
    public boolean debug = false;

    // Interfaces
    public final Pause pause;
    public FPSManager fps;

    // Input handler;
    public final InputHandler inputHandler;

    // Threads
    public PhysicsLoop phyThread;

    // Physics
    public float P_deltaTime;

    // Lists
    public final List<Controllable> controllable = new CopyOnWriteArrayList<>();
    private final List<Movable> movable = new CopyOnWriteArrayList<>();
    private final List<Drawable> drawable = new CopyOnWriteArrayList<>();
    private final List<Debugable> debugable = new CopyOnWriteArrayList<>();

    public Canvas (int width, int height) {
        this.width = width;
        this.height = height;

        this.centerX = width/2;
        this.centerY = height/2;

        this.OffsetX = 0;
        this.OffsetY = 0;

        this.pause = new Pause(this);
        this.fps = new FPSManager();

        this.inputHandler = new InputHandler(this);
    }


    public void addInputHandlers(KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.addKeyListener(this.inputHandler.keyHandler);
        this.addMouseListener(this.inputHandler.mouseHandler);
        this.addMouseMotionListener(this.inputHandler.mouseHandler);
        this.addMouseWheelListener(this.inputHandler.mouseHandler);
    }

    public void getInput(PhysicsLoop gameLoop) {
        inputHandler.getInput(gameLoop);
    }



    // Adding to lists
    public void addToLists(Listable obje) {
        if (obje instanceof Drawable objeD) drawable.add(objeD);
        if (obje instanceof Movable objeM) movable.add(objeM);
        if (obje instanceof Debugable objeD) debugable.add(objeD);
    }

    public void addControllable(Controllable obj) {
        obj.setInputHandler(this.inputHandler);
        controllable.add(obj);
    }

    public void moveMovable(float deltaTime) {
        for (Movable obj : movable) obj.move(deltaTime);
    }



    public void fpsManager() {
        this.fps.manage();
    }

    
    public void debuger() {
        for (Debugable obj : debugable) obj.deburger(this.debug);
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
            obj.draw(g, centerX, centerY, this.Scale);
        } catch (Exception e) {}

        // UI
        pause.draw(g);

        fps.draw(g);
    }
}