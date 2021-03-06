package models.objects;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

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
	@OneToOne
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
	
	public boolean isPastTraject() {
		return this.getArrivalPP().getTime().before(new Date());
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
	
	public static void delete(Traject traj) {
		int seat =  traj.getProposal().getAvailableSeats();
		traj.getProposal().setAvailableSeats(seat+1);
		traj.getProposal().removeTraject(traj);
		Ebean.save(traj.getProposal());
		traj.getUser().removeTraject(traj);
		traj.getUser().save();
		traj.getRequest().setTraject(null);
		traj.getRequest().save();
		traj.delete();
		traj.getArrivalPP().delete();
		traj.getDeparturePP().delete();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Traject)) return false;
		Traject t = (Traject) o;
		return this.getId() == t.getId();
	}
}
