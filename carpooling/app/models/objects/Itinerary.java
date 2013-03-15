package models.objects;

import java.util.Date;


public class Itinerary {
	
	private Date departureTime, arrivalTime;
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

    @Override
    public String toString() {
        return "Itinerary{" +
                "departureTime=" + departureTime +
                ", ArrivalTime=" + arrivalTime +
                ", pickupPoint=" + pickupPoint +
                '}';
    }
}
