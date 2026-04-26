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
    public final float Scale = 0.2f;

    // FPS
    public final FPSManager fpsManager;
    public float P_deltaTime;

    // Lists
    private final List<Movable> movable = new CopyOnWriteArrayList<>();
    private final List<Drawable> drawable = new CopyOnWriteArrayList<>();

    public Canvas (int width, int height) {
        this.width = width;
        this.height = height;
        this.fpsManager = new FPSManager();

        this.fpsManager.MaxFPS = 0;
        this.fpsManager.MinFPS = Integer.MAX_VALUE;
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
        fpsManager.manage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Drawable obj : this.drawable) {
            try {
                obj.draw(g, this.width/2, this.height/2, this.Scale);
            } catch (Exception e) {
            }
        }

        // UI
        if (fpsManager.F_deltaTime == 0) return;
        fpsManager.LastFewFPS[fpsManager.Count % fpsManager.LastFewFPS.length] = (int)(1.0 / fpsManager.F_deltaTime);
        fpsManager.Count = (fpsManager.Count + 1) % fpsManager.LastFewFPS.length;

        if (fpsManager.F_deltaTime > 0) {
            g.setColor(Color.BLACK);
            
            g.drawString("Max FPS: " + fpsManager.MaxFPS, 30, 30);
            g.drawString("Avg FPS: " + fpsManager.AverageFPS, 30, 50);
            g.drawString("Min FPS: " + (fpsManager.MinFPS == Integer.MAX_VALUE ? 0 : fpsManager.MinFPS), 30, 70);
        }
    }
}