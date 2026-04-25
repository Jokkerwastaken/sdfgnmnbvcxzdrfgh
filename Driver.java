import classes.*;
import java.awt.Color;
import javax.swing.Timer;
import loops.GameLoop;
import loops.RepaintLoop;
import modules.GFrame;
import objects.Entity;

public class Driver implements Runnable {
    public final long startTime = System.nanoTime();    // Not gonna change

    // Points
    private final Point2 point = new Point2(0, 0, 10);
    private final Point2 point2 = new Point2(0, 0, 10);
    private final Point2 point3 = new Point2(0, 0, 10);
    // Vectors
    private final Vector2 vector = new Vector2(30, 20, Color.GREEN);
    private final Vector2 vector2 = new Vector2(-10, 4, Color.GREEN);
    private final Vector2 vector3 = new Vector2(0, 0, Color.GREEN);

    //fps
    private final float FPSLimit = 144;
    
    @Override
    public void run() {
        GFrame frame = new GFrame();

        Entity obj = new Entity(point, vector, Color.RED);
        Entity obj2 = new Entity(point2, vector2, Color.BLUE);
        Entity obj3 = new Entity(point3, vector3, Color.BLUE);

        frame.canvas.addDrawable(obj);
        frame.canvas.addDrawable(obj2);
        frame.canvas.addDrawable(obj3);

        frame.canvas.addMovable(obj);
        frame.canvas.addMovable(obj2);
        frame.canvas.addMovable(obj3);

        frame.canvas.FPSLimit = FPSLimit;


        Thread repaintThread = new Thread() {
            public void run() {
                RepaintLoop repaintLoop = new RepaintLoop(frame, FPSLimit);
                repaintLoop.run();
            }
        };
        Thread phyThread = new Thread() {
            public void run() {
                GameLoop gameLoop = new GameLoop(frame);
                gameLoop.run();
            }
        };

        Timer fpsTimer = new Timer(1000, e -> {
            frame.canvas.fpsManager();
        });

        repaintThread.start();
        phyThread.start();
        
        fpsTimer.start();

        frame.canvas.requestFocus(true);
    }
}