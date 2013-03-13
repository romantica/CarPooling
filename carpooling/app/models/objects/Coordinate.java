package models.objects;

public class Coordinate {
    private double x, y;

    public Coordinate() {
    }

    public Coordinate(double x, double y) {
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
        this.x = Double.parseDouble(c[0]);
        this.y =  Double.parseDouble(c[1]);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        return this.x + "," + this.y;
    }

}
