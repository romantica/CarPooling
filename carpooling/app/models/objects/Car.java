package models.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;

@Entity
public class Car {
	
	@Id
	private int id;
	
	@Constraints.Required
	private String plateNumber;
	private String model, color;

    public Car(){}
	public Car(String plateNumber, String model, String color) {
		super();
		this.plateNumber = plateNumber;
		this.model = model;
		this.color = color;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

    @Override
    public String toString() {
        return "Car{" +
                "plateNumber='" + plateNumber + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
    
    public static Finder<Integer, Car> find = new Finder<Integer, Car>(Integer.class, Car.class);
}
