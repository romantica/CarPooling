package models.objects;

import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import play.data.validation.*;
import play.db.ebean.Model;

@Entity
@Table(name="PickupPoint")
public class PickupPoint extends Model {

    @Id
	private int id;
	
	private String name, description, address;
	
	@Constraints.Required
	private double Coordinatex,Coordinatey;
	
	@Constraints.Required
	private Coordinate coordinates;
	private static double PRECISION = 0.00005;
	
    public PickupPoint(){}
	
	public PickupPoint(String name, String description, String address,
			Coordinate coordinates) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
		this.coordinates = coordinates;
		this.Coordinatex = coordinates.getX();
		this.Coordinatey = coordinates.getY();
	}

    public PickupPoint(int id, String name, String description, String address,
                       Coordinate coordinates) {
        super();
        this.name = name;
        this.description = description;
        this.address = address;
        this.coordinates = coordinates;
        this.id = id;
        this.Coordinatex = coordinates.getX();
		this.Coordinatey = coordinates.getY();
    }

    public int getId() {
        return id;
    }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Coordinate getCoordinates() {
        if (coordinates == null){
            return new Coordinate(getCoordinateX(), getCoordinateY());
        }
		return coordinates;
	}


	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
		setCoordinateX(coordinates.getX());
		setCoordinateY(coordinates.getY());
	}
	
	public double getCoordinateX() {
		return this.Coordinatex;
	}
	
	public double getCoordinateY() {
		return this.Coordinatey;
	}
	
	public void setCoordinateX(double x) {
		this.Coordinatex = x;
        this.coordinates = new Coordinate(x, this.Coordinatey);
	}
	
	public void setCoordinateY(double y) {
		this.Coordinatey = y;
        this.coordinates = new Coordinate(this.Coordinatex, y);
	}
	
	public static Finder<Integer, PickupPoint> find = new Finder<Integer, PickupPoint>(Integer.class, PickupPoint.class);
    
	public static List<PickupPoint> findAll() {
		return find.all();
	}
	
	/**
	 * Stocke le PickupPoint pp dans la base de donnee,
	 * si ce pp est déjà dedans, on ne le sauve pas.
	 * 
	 * Critere de comparaison:
	 * coordonnee avec ± PRECISION
	 * description identique (? TODO voir comment en tenir compte)
	 * 
	 * @param pp
	 */
	public static void create(PickupPoint pp) {
		PickupPoint search = null;
		try {
			search = Ebean.find(PickupPoint.class, pp.getId());
		} catch (NullPointerException e) {}
		if(search != null){
			return;
		}
		List<PickupPoint> all = PickupPoint.findAll();
		boolean add = true;
		for(int i = 0; i < all.size() && add; i++){
			if((Math.abs(all.get(i).getCoordinateX()-pp.getCoordinateX()) < PRECISION || Math.abs(all.get(i).getCoordinateY() - pp.getCoordinateY()) < PRECISION)) {
				add = false;
			}
		}
		if(add) {
			pp.save();
		}
	}
	
	public static void delete(PickupPoint pp) {
		pp.delete();
	}
	
    @Override
    public String toString() {
        return "PickupPoint{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", address='" + address + '\'' +
        ", coordinates=" + coordinates +
        '}';
    }

}
