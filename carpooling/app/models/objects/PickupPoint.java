package models.objects;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;

@Entity
@Table(name="PickupPoint")
public class PickupPoint {

    @Id
	private int id;
	
	private String name, description, address;
	
	@Constraints.Required
	private Coordinate coordinates;

    public PickupPoint(){}
	
	public PickupPoint(String name, String description, String address,
			Coordinate coordinates) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
		this.coordinates = coordinates;
	}

    public PickupPoint(int id, String name, String description, String address,
                       Coordinate coordinates) {
        super();
        this.name = name;
        this.description = description;
        this.address = address;
        this.coordinates = coordinates;
        this.id = id;
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
		return coordinates;
	}


	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}
	
	public static Finder<Integer, PickupPoint> find = new Finder<Integer, PickupPoint>(Integer.class, PickupPoint.class);
    
	public static List<PickupPoint> findAll() {
		return find.all();
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
