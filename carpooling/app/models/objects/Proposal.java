package models.objects;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;

@Entity
public class Proposal {
	
	@Id
	private int id;
	
	private float kmCost;
	private int availableSeats;
	
	@Constraints.Required
	private Car car;
	@Constraints.Required
	private User user;
	private List<Traject> traject;
	private LinkedList<Itinerary> itinerary;

	public Proposal(float kmCost, int availableSeats, Car car, User user) {
		super();
		this.kmCost = kmCost;
		this.availableSeats = availableSeats;
		this.car = car;
		this.user = user;
		this.traject = new ArrayList<Traject>();
		this.itinerary = new LinkedList<Itinerary>();
	}

    public Proposal(int id, float kmCost, int availableSeats, Car car, User user) {
        this(kmCost,availableSeats,car,user);
        this.id = id;
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

	public void addTraject(Traject traj){
		if(traj == null) return;
		this.traject.add(traj);
	}
	
	@Deprecated
	public void setTraject(List<Traject> traject) {
		this.traject = traject;
	}

	public LinkedList<Itinerary> getItinerary() {
		return itinerary;
	}

	public void addItinerary(Itinerary itinerary){
		if (itinerary == null) return;
		this.itinerary.add(itinerary);
	}
	
	@Deprecated
	public void setItinerary(LinkedList<Itinerary> itinerary) {
		this.itinerary = itinerary;
	}
	
	public Itinerary getItinerary(PickupPoint pickupPoint)
	{
		for (Itinerary itinerary : this.getItinerary())
			if (itinerary.getPickupPoint().equals(pickupPoint))
				return itinerary;
		return null;
	}

	public static Finder<Integer, Proposal> find = new Finder<Integer, Proposal>(Integer.class, Proposal.class);
	
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
