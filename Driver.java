import java.awt.Color;
import javax.swing.Timer;

import classes.*;
import loops.PhysicsLoop;
import loops.RepaintLoop;
import modules.GFrame;
import objects.Entity;

public class Driver implements Runnable {
    public final long startTime = System.nanoTime();    // Not gonna change

    // Points
    private final Point2 point = new Point2(0, 0, 10);
    private final Point2 point2 = new Point2(0, 0, 5);
    private final Point2 point3 = new Point2(0, 0, 30);
    // Vectors
    private final Vector2 vector = new Vector2(30, 20, Color.GREEN);
    private final Vector2 vector2 = new Vector2(-10, 4, Color.GREEN);
    private final Vector2 vector3 = new Vector2(0, 0, Color.GREEN);

    //fps
    private final float FPSLimit = 60;

    private void ThreadAndTimerInits(GFrame frame) {
        Thread repaintThread = new Thread() {
            public void run() {
                RepaintLoop repaintLoop = new RepaintLoop(frame, FPSLimit);
                repaintLoop.run();
            }
        };
        repaintThread.start();

        Thread phyThread = new Thread() {
            public void run() {
                PhysicsLoop gameLoop = new PhysicsLoop(frame);
                gameLoop.run();
            }
        };
        phyThread.start();

        Timer fpsTimer = new Timer(1000, e -> {
            frame.canvas.fpsManager();
        });
        fpsTimer.start();
    }
    
    @Override
    public void run() {
        GFrame frame = new GFrame();

        //new Entity(point, vector, Color.RED, true, frame);
        //new Entity(point2, vector2, Color.BLUE, true, frame);
        //new Entity(point3, vector3, Color.BLUE, true, frame);
        
        Entity player = new Entity(new Point2(0, 0, 5), new Vector2(-10, 0, Color.GREEN), Color.BLACK, false, frame);
        player.makeControllable(frame);

        frame.canvas.fpsManager.FPSLimit = this.FPSLimit;

        ThreadAndTimerInits(frame);

        frame.canvas.requestFocus(true);
    }
}