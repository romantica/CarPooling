package models.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import play.data.validation.Constraints;
//import play.db.ebean.Model;


@Entity
public class Coordinate {
	
	@Id
	private int id;
	
	@Constraints.Required
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
    
    @Override
    public boolean equals(Object o) {
    	if (o == this) return true;
    	if (!(o instanceof Coordinate)) return false;
    	Coordinate c = (Coordinate) o;
    	return this.getX() == c.getX() && this.getY() == c.getY();
    }

    //public static Finder<Integer, Coordinate> find = new Finder<Integer, Coordinate>(Integer.class, Coordinate.class);
}
