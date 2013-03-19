package models.objects;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Itinerary extends Model {
	
	@Id
	private int id;
	
	@Constraints.Required
	private Date departureTime, arrivalTime;
	@Constraints.Required
	private PickupPoint pickupPoint;

	public Itinerary(Date departureTime, Date arrivalTime,
			PickupPoint pickupPoint) {
		super();
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.pickupPoint = pickupPoint;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public PickupPoint getPickupPoint() {
		return pickupPoint;
	}

	public void setPickupPoint(PickupPoint pickupPoint) {
		this.pickupPoint = pickupPoint;
	}
	
	public static Finder<Integer, Itinerary> find = new Finder<Integer, Itinerary>(Integer.class, Itinerary.class);
    
    @Override
    public String toString() {
        return "Itinerary{" +
        "departureTime=" + departureTime +
        ", ArrivalTime=" + arrivalTime +
        ", pickupPoint=" + pickupPoint +
        '}';
    }
	
}
