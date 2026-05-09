import classes.*;
import classes.geometry.Circle;

import java.awt.Color;
import javax.swing.Timer;
import loops.PhysicsLoop;
import loops.RepaintLoop;
import modules.GFrame;
import objects.EntityV2;

import java.awt.event.KeyEvent;

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
        new Grid(frame);

        Circle c = new Circle(0, 0, frame);
        c.color = Color.BLACK;
        c.radius = 100;

        try {
            EntityV2 player = new EntityV2(new Point2V2(0, 0), 5, Color.BLUE, 9, frame);
            player.makeControllable(KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D);
            //new EntityV2(new Point2V2(0, 0, frame), 5, Color.RED, -9.8f, frame);
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.canvas.fps.FPSLimit = this.FPSLimit;

        ThreadAndTimerInits(frame);

        frame.canvas.requestFocus(true);
    }
}