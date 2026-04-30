package modules;

import classes.contracts.Controllable;
import classes.contracts.Drawable;
import classes.contracts.Movable;
import loops.InputHandler;
import modules.inputs.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    //Canvas stuff
    public int width, height;
    public float Scale = 1f;
    public int OffsetX, OffsetY;
    public final boolean debug = true;

    // Input handler;
    private final InputHandler inputHandler;

    // FPS
    public final FPSManager fpsManager;
    public float P_deltaTime;

    // Lists
    public final List<Controllable> controllable = new CopyOnWriteArrayList<>();
    private final List<Movable> movable = new CopyOnWriteArrayList<>();
    private final List<Drawable> drawable = new CopyOnWriteArrayList<>();

    public Canvas (int width, int height) {
        this.width = width;
        this.height = height;

        this.OffsetX = width/2;
        this.OffsetY = height/2;

        this.fpsManager = new FPSManager();
        this.inputHandler = new InputHandler();

        this.fpsManager.MaxFPS = 0;
        this.fpsManager.MinFPS = Integer.MAX_VALUE;
    }


    public void addInputHandlers(KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.addKeyListener(this.inputHandler.keyHandler);
        this.addMouseListener(this.inputHandler.mouseHandler);
        this.addMouseMotionListener(this.inputHandler.mouseHandler);
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
        //InputLoop.getInputs();
        if (this.inputHandler.keyHandler.getKeyState(KeyEvent.VK_ESCAPE)) System.exit(0);

        for (Movable obj : movable) obj.move(deltaTime);
    }

    public void fpsManager() {
        fpsManager.manage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Drawable obj : this.drawable) {
            try {
                obj.draw(g, this.OffsetX, this.OffsetY, this.Scale);
            } catch (Exception e) {
            }
        }

        // UI
        if (fpsManager.F_deltaTime == 0) return;
        fpsManager.LastFewFPS[fpsManager.Count % fpsManager.LastFewFPS.length] = (int)(1.0 / fpsManager.F_deltaTime);
        fpsManager.Count = (fpsManager.Count + 1) % fpsManager.LastFewFPS.length;

        if (fpsManager.F_deltaTime > 0) {
            g.setColor(Color.BLACK);
            
            g.drawString("Max FPS: " + fpsManager.MaxFPS, 30, 30);
            g.drawString("Avg FPS: " + fpsManager.AverageFPS, 30, 50);
            g.drawString("Min FPS: " + (fpsManager.MinFPS == Integer.MAX_VALUE ? 0 : fpsManager.MinFPS), 30, 70);
        }
    }
}