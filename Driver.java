import java.awt.Color;
import javax.swing.Timer;

import classes.*;
import loops.PhysicsLoop;
import loops.RepaintLoop;
import modules.GFrame;
import objects.Entity;

public class Driver implements Runnable {
    public final long startTime = System.nanoTime();    // Not gonna change
    private float Scale;

    // Points
    private final Point2 point = new Point2(0, 0, 10, this.Scale);
    private final Point2 point2 = new Point2(0, 0, 10, this.Scale);
    private final Point2 point3 = new Point2(0, 0, 10, this.Scale);
    // Vectors
    private final Vector2 vector = new Vector2(30, 20, this.Scale, Color.GREEN);
    private final Vector2 vector2 = new Vector2(-10, 4, this.Scale, Color.GREEN);
    private final Vector2 vector3 = new Vector2(0, 0, this.Scale, Color.GREEN);

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
        this.Scale = frame.canvas.Scale;

        new Entity(point, vector, Color.RED, frame);
        new Entity(point2, vector2, Color.BLUE, frame);
        new Entity(point3, vector3, Color.BLUE, frame);

        frame.canvas.fpsManager.FPSLimit = this.FPSLimit;

        ThreadAndTimerInits(frame);

        frame.canvas.requestFocus(true);
    }
}