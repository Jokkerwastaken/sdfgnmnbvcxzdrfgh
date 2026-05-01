import java.awt.Color;
import javax.swing.Timer;

import classes.*;
import loops.PhysicsLoop;
import loops.RepaintLoop;
import modules.GFrame;
import objects.Entity;

public class Driver implements Runnable {
    public final long startTime = System.nanoTime();    // Not gonna change
    private Thread repaintThread, phyThread;

    //fps
    private final float FPSLimit = 60;

    private PhysicsLoop gameLoop;

    private void ThreadAndTimerInits(GFrame frame) {
        this.repaintThread = new Thread() {
            public void run() {
                RepaintLoop repaintLoop = new RepaintLoop(frame, FPSLimit);
                repaintLoop.run();
            }
        };
        this.repaintThread.start();

        this.phyThread = new Thread() {
            public void run() {
                gameLoop = new PhysicsLoop(frame);
                gameLoop.run();
            }
        };
        this.phyThread.start();

        Timer fpsTimer = new Timer(1000, e -> {
            frame.canvas.fpsManager();
        });
        fpsTimer.start();
    }
    
    @Override
    public void run() {
        GFrame frame = new GFrame();
        
        for (int x = -80; x < 80; x++) for (int y = -60; y < 60; y++) {
            new Line(new Point2(x*50, y*50), new Vector2(0, 50), Color.lightGray, frame.canvas);
            new Line(new Point2(x*50, y*50), new Vector2(50, 0), Color.lightGray, frame.canvas);
        }

        Entity player = new Entity(new Point2(0, 0), new Vector2(0, 0, Color.GREEN), 5, Color.BLACK, false, frame);
        player.makeControllable(frame);

        frame.canvas.fps.fpsManager.FPSLimit = this.FPSLimit;

        ThreadAndTimerInits(frame);

        frame.canvas.requestFocus(true);
    }
}