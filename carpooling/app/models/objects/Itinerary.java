package models.objects;

import java.util.Date;


public class Itinerary {
	
	private Date departureTime, ArrivalTime;
	private PickupPoint pickupPoint;
	
	public Itinerary(Date departureTime, Date arrivalTime,
			PickupPoint pickupPoint) {
		super();
		this.departureTime = departureTime;
		ArrivalTime = arrivalTime;
		this.pickupPoint = pickupPoint;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return ArrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		ArrivalTime = arrivalTime;
	}

	public PickupPoint getPickupPoint() {
		return pickupPoint;
	}

	public void setPickupPoint(PickupPoint pickupPoint) {
		this.pickupPoint = pickupPoint;
	}
	
	
	
	
}
