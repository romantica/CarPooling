
public class Car {
	
	private String plateNumber;
	private String model, color;
	
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
	
	
}
