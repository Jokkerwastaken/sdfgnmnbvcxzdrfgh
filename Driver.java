import classes.*;
import java.awt.Color;
import javax.swing.Timer;
import loops.PhysicsLoop;
import loops.RepaintLoop;
import modules.GFrame;
import objects.EntityV2;

public class Driver implements Runnable {
    public final long startTime = System.nanoTime();    // Not gonna change
    private Thread repaintThread, phyThread;

    //fps
    private final float FPSLimit = 1000;

    private PhysicsLoop gameLoop;

    private void ThreadAndTimerInits(GFrame frame) {
        this.repaintThread = new Thread() {
            public void run() {
                RepaintLoop repaintLoop = new RepaintLoop(frame, FPSLimit);
                repaintLoop.run();
                repaintLoop.keepPlayerInFrame();
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
        
        //new Vector2(50, 50, Color.BLACK, frame.canvas);

        new Grid(frame);

        //Entity player = new Entity(new Point2(0, 0), new Vector2(0, 0, Color.GREEN, null), 5, Color.BLACK, false, frame);
        //player.makeControllable();

        try {
            new EntityV2(new Point2(0, 0), 5, Color.BLUE, 0, frame).makeControllable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.canvas.fps.fpsManager.FPSLimit = this.FPSLimit;

        ThreadAndTimerInits(frame);

        frame.canvas.requestFocus(true);
    }
}