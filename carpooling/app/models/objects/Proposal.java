package models.objects;

import java.util.LinkedList;
import java.util.List;


public class Proposal {
	
	private float kmCost;
	private int availableSeats;
	
	private Car car;
	private User user;
	private List<Traject> traject;
	private LinkedList<Itinerary> itinerary;
	
	public Proposal(float kmCost, int availableSeats, Car car, User user,
			List<Traject> traject, LinkedList<Itinerary> itinerary) {
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

	public List<Traject> getTraject() {
		return traject;
	}

	public void setTraject(List<Traject> traject) {
		this.traject = traject;
	}

	public LinkedList<Itinerary> getItinerary() {
		return itinerary;
	}

	public void setItinerary(LinkedList<Itinerary> itinerary) {
		this.itinerary = itinerary;
	}


    @Override
    public String toString() {
        return "Proposal{" +
                "kmCost=" + kmCost +
                ", availableSeats=" + availableSeats +
                ", car=" + car +
                ", user=" + user +
                ", traject=" + traject +
                ", itinerary=" + itinerary +
                '}';
    }
}
