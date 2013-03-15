package models.objects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Proposal {

	private float kmCost;
	private int availableSeats;

	private Car car;
	private User user;
	private List<Traject> traject;
	private List<Itinerary> itinerary;

	public Proposal(float kmCost, int availableSeats, Car car, User user) {
		super();
		this.kmCost = kmCost;
		this.availableSeats = availableSeats;
		this.car = car;
		this.user = user;
		this.traject = new ArrayList<Traject>();
		this.itinerary = new ArrayList<Itinerary>();
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

	public void addTraject(Traject traject) {
		this.traject.add(traject);
	}

	public List<Itinerary> getItinerary() {
		return itinerary;
	}


	public void addItinerary(Itinerary itinerary) {
        if (itinerary == null) return;
		this.itinerary.add(itinerary);
    }

	public Itinerary getItinerary(PickupPoint pickupPoint)
	{
		for (Itinerary itinerary : this.getItinerary())
			if (itinerary.getPickupPoint().equals(pickupPoint))
				return itinerary;
		return null;
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
