package classes;


public class Normal {
    public final Vector2V2 vector;

    public Normal (Vector2V2 vector, float scale) {
        this.vector = new Vector2V2(vector.normalize().y *-1, vector.normalize().x, null);
    }

    public Vector2V2 vectorize() {
        return new Vector2V2(this.vector.x, this.vector.y, null);
    }
}