package models.objects;

import java.sql.Timestamp;
import java.util.*;

import javax.persistence.*;

import play.cache.Cache;
import play.data.validation.*;
import play.db.ebean.Model;

@Entity
@Table(name="Proposal")
public class Proposal extends Model{
	
	@Id
	private int id;
	
	@Version
    public Timestamp lastUpdate;
	
	private float kmCost;
	private int availableSeats;
	
	@Constraints.Required
    @ManyToOne
	private Car car;
	@Constraints.Required
	@ManyToOne
	private User user;
	@ManyToMany
	private List<Traject> traject;
	@ManyToMany
	private List<Itinerary> itinerary;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
	
	public void removeTraject(Traject traj){
		this.traject.remove(traj);
	}
	
	@Deprecated
	public void setTraject(List<Traject> traject) {
		this.traject = traject;
	}

	public LinkedList<Itinerary> getItinerary() {
        LinkedList<Itinerary> result = new LinkedList<Itinerary>();
        for (Itinerary i : itinerary)
            result.add(i);
		return result;
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
	
	public static void create(Proposal prop) {
		prop.save();
	}
	
	public static List<Proposal> findAll() {
		return find.all();
	}
	
	public static void delete(Proposal prop) {
		prop.getUser().removeProposal(prop);
		LinkedList<Itinerary> iti = prop.getItinerary();
		prop.deleteManyToManyAssociations("itinerary");
		for(Itinerary i: iti){
			i.delete();
		}
		prop.getUser().save();
		prop.delete();
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

	public List<Itinerary> getItineraryList() {
		return this.itinerary;
	}
}
