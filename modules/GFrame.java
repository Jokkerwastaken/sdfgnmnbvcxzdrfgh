package modules;

import java.awt.*;

import javax.swing.JFrame;

public class GFrame extends JFrame {
    public int width = 600;
    public int height = 600;

    public Canvas canvas;

    public GFrame () {
        setPreferredSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        canvas = new Canvas(width, height);
        
        add(canvas, BorderLayout.CENTER);

        this.pack();
        setVisible(true);
    }

    public boolean IsTimedout() {
        return false;
    }
}
