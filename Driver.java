import classes.*;
import java.awt.Color;
import javax.swing.Timer;
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
    public double currentFPS;
    private float FPSLimit = 144;
    
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

        Thread refThread = new Thread() {
            @Override
            public void run() {
                long LastTime = System.nanoTime();
                long currentTime;
                float deltaTime;
                while (true) {
                    currentTime = System.nanoTime();
                    deltaTime = (currentTime - LastTime) / 1_000_000_000.0f;
                    LastTime = currentTime;

                    frame.canvas.moveMovable(deltaTime);
                    //System.out.println("Hello!");

                    try {
                        Thread.sleep(1000/30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread repThread = new Thread() {
            @Override
            public void run() {
                long LastTime = System.nanoTime();
                long currentTime;
                float deltaTime;

                while (true) {
                    currentTime = System.nanoTime();
                    deltaTime = (currentTime - LastTime) / 1_000_000_000.0f;
                    LastTime = currentTime;

                    frame.canvas.F_deltaTime = deltaTime;
                    if (frame.canvas.F_samples < frame.canvas.lastFewFPS.length) frame.canvas.F_samples++; 
                    
                    frame.canvas.repaint();
                    
                    try {
                        Thread.sleep(1000/144);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }   
            }
        };

        Timer fpsTimer = new Timer(1000, e -> {
            frame.canvas.fpsManager();
        });

        repThread.start();
        refThread.start();
        
        fpsTimer.start();

        frame.canvas.requestFocus(true);
    }
}