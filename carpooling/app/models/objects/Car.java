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
	private User user;

    public Car(){}
	public Car(String plateNumber, String model, String color, User user) {
		super();
		this.setUser(user);
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
    
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public static Finder<Integer, Car> find = new Finder<Integer, Car>(Integer.class, Car.class);
}
