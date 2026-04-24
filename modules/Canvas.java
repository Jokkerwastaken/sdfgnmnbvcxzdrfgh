package modules;

import classes.contracts.Drawable;
import classes.contracts.Movable;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    //Canvas stuff
    public int width, height;

    // FPS
    public float F_deltaTime;
    public float F_samples = 0;
    public final int[] lastFewFPS = new int[100];
    private int count = 0;
    private int maxFPS;
    private int minFPS;
    private int averageFPS;
    public float FPSLimit;

    private long lastMaxFPS = 0;
    private long lastMinFPS = 0;

    // Lists
    public List<Movable> movable = new CopyOnWriteArrayList<>();
    public List<Drawable> drawable = new CopyOnWriteArrayList<>();

    public Canvas (int width, int height) {
        this.width = width;
        this.height = height;
        this.maxFPS = 0;
        this.minFPS = Integer.MAX_VALUE;
    }

    public void addDrawable(Drawable obj) {
        drawable.add(obj);
    }

    public void addMovable(Movable obj) {
        movable.add(obj);
    }

    public void moveMovable(float deltaTime) {
        for (Movable obj : movable) {
            obj.move(deltaTime);
        }
    }

    public void fpsManager() {
        if (this.F_samples == 0 || lastFewFPS.length == 0) return;
    
        float sum = 0;
        for (int fps : lastFewFPS) {
            sum += fps;
        }

        this.averageFPS = (int) (sum / lastFewFPS.length);
    
        if (FPSLimit > 0 && averageFPS > FPSLimit) return;
    
        long currentTime = System.nanoTime();
    
        long diffMax = (currentTime - lastMaxFPS) / 1_000_000_000;
        if (diffMax >= 60) {
            maxFPS = 0;
            lastMaxFPS = currentTime;
        }
        if (averageFPS > maxFPS) {
            maxFPS = averageFPS;
        }
    
        long diffMin = (currentTime - lastMinFPS) / 1_000_000_000;
        if (diffMin >= 60 || minFPS == 0) { 
            minFPS = averageFPS;
            lastMinFPS = currentTime;
        }
        if (averageFPS < minFPS && averageFPS > 0) {
            minFPS = averageFPS;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Drawable obj : this.drawable) {
            obj.draw(g, this.width/2, this.height/2);
        }

        if (F_deltaTime == 0) return;
        lastFewFPS[count % lastFewFPS.length] = (int)(1.0 / F_deltaTime);
        count = (count + 1) % lastFewFPS.length;

        if (F_deltaTime > 0) {
            g.setColor(Color.BLACK);
            
            g.drawString("Max FPS: " + this.maxFPS, 30, 30);
            g.drawString("Avg FPS: " + this.averageFPS, 30, 50);
            g.drawString("Min FPS: " + this.minFPS, 30, 70);
        }
    }
}