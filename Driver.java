import java.awt.Color;
import javax.swing.Timer;

import classes.*;
import loops.PhysicsLoop;
import loops.RepaintLoop;
import modules.GFrame;
import objects.Entity;

public class Driver implements Runnable {
    public final long startTime = System.nanoTime();    // Not gonna change

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
        
        for (int x = -80; x < 80; x++) for (int y = -60; y < 60; y++) {
            new Line(new Point2(x*50, y*50), new Vector2(0, 50), Color.lightGray, frame.canvas);
            new Line(new Point2(x*50, y*50), new Vector2(50, 0), Color.lightGray, frame.canvas);
        }

        Entity player = new Entity(new Point2(0, 0), new Vector2(0, 0, Color.GREEN), 5, Color.BLACK, false, frame);
        player.makeControllable(frame);

        frame.canvas.fpsManager.FPSLimit = this.FPSLimit;

        ThreadAndTimerInits(frame);

        frame.canvas.requestFocus(true);
    }
}