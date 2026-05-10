package modules.window;

import classes.contracts.*;
import loops.PhysicsLoop;
import modules.FPSManager;
import modules.inputs.*;
import modules.interfaces.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

public class CanvasV2 extends JPanel {
    // Canvas stuff
    private BufferedImage canvas;
    public int width, height, centerX, centerY;
    public float Scale = 1f;
    public int OffsetX, OffsetY;
    public boolean debug = false;

    // Interfaces
    public Pause pause;
    public FPSManager fps;

    // Input handler;
    public InputHandler inputHandler;

    // Threads
    public PhysicsLoop phyThread;

    // Physics
    public float P_deltaTime;

    // Lists
    public final List<Controllable> controllable = new CopyOnWriteArrayList<>();
    private final List<Movable> movable = new CopyOnWriteArrayList<>();
    private final List<Drawable> drawable = new CopyOnWriteArrayList<>();
    private final List<Debugable> debugable = new CopyOnWriteArrayList<>();

    public CanvasV2 (int width, int height) {
        this.width = width;
        this.height = height;

        canvas = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);

        this.centerX = width/2;
        this.centerY = height/2;
        
        this.inputHandler = new InputHandler(this);
        this.pause = new Pause(this);
        this.fps = new FPSManager();

        // JPanel seaded
        setFocusable(true);
        requestFocusInWindow();
    }


    // Input handling
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


    // fps calculating
    public void fpsManager() {
        this.fps.manage();
    }

    
    // Passing the current state of debug
    public void debuger() {
        for (Debugable obj : debugable) obj.deburger(this.debug);
    }


    // Repainting
    public void render() {
        if (this.getWidth() != width || this.getHeight() != height) {
            width = Math.max(1, this.getWidth()); // Väldi 0-suurust (crash)
            height = Math.max(1, this.getHeight());
            
            // Loo uus pilt uue suurusega
            canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            
            // Uuenda keskpunktid kohe
            this.centerX = width / 2 + this.OffsetX;
            this.centerY = height / 2 + this.OffsetY;
        }

        Graphics2D g2d = canvas.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(pause.paused ? Color.BLACK : Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        this.centerX = this.width / 2 + this.OffsetX;
        this.centerY = this.height / 2 + this.OffsetY;

        float A = pause.paused ? 0.2f : 1f;
        for (Drawable obj : this.drawable) {
            try {
                obj.setAlpha(A);
                obj.draw(g2d, centerX, centerY, this.Scale);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pause.draw(g2d);
        fps.draw(g2d);

        g2d.dispose();
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(canvas, 0, 0, null);
    }
}