package modules.inputs;

public class InputHandler {
    public KeyHandler keyHandler;
    public MouseHandler mouseHandler;

    public InputHandler() {
        this.keyHandler = new KeyHandler();
        this.mouseHandler = new MouseHandler();
    }
}
