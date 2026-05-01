package modules;

import java.awt.*;
import javax.swing.JFrame;

import modules.inputs.*;

public class GFrame extends JFrame {
    public int width = 600;
    public int height = 600;

    public Canvas canvas;

    public GFrame () {
        setPreferredSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        KeyHandler keyHandler = new KeyHandler();
        MouseHandler mouseHandler = new MouseHandler();

        setBackground(Color.BLACK);

        canvas = new Canvas(width, height);
        canvas.addInputHandlers(keyHandler, mouseHandler);
        
        add(canvas, BorderLayout.CENTER);

        this.pack();
        setVisible(true);
        requestFocus(true);
    }

    public boolean IsTimedout() {
        return false;
    }
}
