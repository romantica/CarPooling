package models.objects;

public class Coordinate {
    private float x, y;

    public Coordinate() {
    }

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Transform a string to coordinate
     *
     * @param coord String format: FLOAT,FLOAT
     */
    public Coordinate(String coord) {
        String[] c = coord.split(",");
        this.x = Float.parseFloat(c[0]);
        this.y = Float.parseFloat(c[1]);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String toString() {
        return this.x + "," + this.y;
    }

}
