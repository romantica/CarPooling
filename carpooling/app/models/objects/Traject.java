package models.objects;

import java.sql.Timestamp;

import javax.persistence.*;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
@Table(name="Traject")
public class Traject extends Model {
	
	@Id
	private int id;
	
	@Version
    public Timestamp lastUpdate;
	
	private int reservedSeats;
	private float totalCost;
	
	@Constraints.Required
	private Request request;
	@Constraints.Required
	@ManyToOne
	private User user;
	@OneToOne
	private Composition departurePP;
	@OneToOne
	private Composition arrivalPP;
	@OneToOne
	private Proposal proposal;
	
	public Traject(int reservedSeats, float totalCost, Request request,
			User user, Composition departurePP, Composition arrivalPP,
			Proposal proposal) {
		super();
		this.reservedSeats = reservedSeats;
		this.totalCost = totalCost;
		this.request = request;
		this.user = user;
		this.departurePP = departurePP;
		this.arrivalPP = arrivalPP;
		this.proposal = proposal;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservedSeats() {
		return reservedSeats;
	}

	public void setReservedSeats(int reservedSeats) {
		this.reservedSeats = reservedSeats;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Composition getDeparturePP() {
		return departurePP;
	}

	public void setDeparturePP(Composition departurePP) {
		this.departurePP = departurePP;
	}

	public Composition getArrivalPP() {
		return arrivalPP;
	}

	public void setArrivalPP(Composition arrivalPP) {
		this.arrivalPP = arrivalPP;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}
	
	public static Finder<Integer, Traject> find = new Finder<Integer, Traject>(Integer.class, Traject.class);
	
}
