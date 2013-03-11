
public class PickupPoint {
	
	private String name, description, address;
	private Coordinates coordinates;
	
	
	public PickupPoint(String name, String description, String address,
			Coordinates coordinates) {
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


	public Coordinates getCoordinates() {
		return coordinates;
	}


	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	

}
