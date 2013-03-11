import java.util.List;


public class Proposal {
	
	private float kmCost;
	private int availableSeats;
	
	private Car car;
	private User user;
	private Traject traject;
	private List<Itinerary> itinerary;
	
	public Proposal(float kmCost, int availableSeats, Car car, User user,
			Traject traject, List<Itinerary> itinerary) {
		super();
		this.kmCost = kmCost;
		this.availableSeats = availableSeats;
		this.car = car;
		this.user = user;
		this.traject = traject;
		this.itinerary = itinerary;
	}

	public float getKmCost() {
		return kmCost;
	}

	public void setKmCost(float kmCost) {
		this.kmCost = kmCost;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Traject getTraject() {
		return traject;
	}

	public void setTraject(Traject traject) {
		this.traject = traject;
	}

	public List<Itinerary> getItinerary() {
		return itinerary;
	}

	public void setItinerary(List<Itinerary> itinerary) {
		this.itinerary = itinerary;
	}
	
	
	
	
}
