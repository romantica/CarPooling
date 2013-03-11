package models.objects;

public class PickupPoint {
	
	private String name, description, address;
	private Coordinate coordinates;
	
	
	public PickupPoint(String name, String description, String address,
			Coordinate coordinates) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
		this.coordinates = coordinates;
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
	
	

}
