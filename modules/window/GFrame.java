package modules.window;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.Timer;

import modules.inputs.*;

public class GFrame extends JFrame {
    public int width = 600;
    public int height = 600;

    public CanvasV2 canvas;

    public GFrame() {
        setPreferredSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        KeyHandler keyHandler = new KeyHandler();
        MouseHandler mouseHandler = new MouseHandler();

        canvas = new CanvasV2(width, height);
        canvas.addInputHandlers(keyHandler, mouseHandler);
        
        add(canvas, BorderLayout.CENTER);

        this.pack();
        setVisible(true);
        requestFocus(true);

        getNewWH(canvas);
    }

    private void getNewWH(CanvasV2 canvas) {
        Timer WD = new Timer(200, e -> {
            canvas.width = this.getWidth();
            canvas.height = this.getHeight();
        });
        WD.start();
    }

    public boolean IsTimedout() {
        return false;
    }
}
