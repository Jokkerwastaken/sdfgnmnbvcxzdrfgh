package modules.interfaces;

import classes.contracts.DrawableInterface;
import java.awt.Graphics;

public class Pause implements DrawableInterface {
    public boolean paused;
    public int framesFromPause;

    public Pause() {
        this.paused = false;
        this.framesFromPause = 0;
    }

    @Override
    public void draw(Graphics g) {
        // UI

    }
}
