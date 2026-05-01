package modules.inputs;

public class InputHandler implements Runnable {
    public KeyHandler keyHandler;
    public MouseHandler mouseHandler;

    public InputHandler() {
        this.keyHandler = new KeyHandler();
        this.mouseHandler = new MouseHandler();
    }

    @Override
    public void run() {
        
    }
}
