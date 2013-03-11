import java.util.Date;


public class Composition {
	
	private boolean type;
	private Date time;
	private PickupPoint pickupPoint;
	
	public Composition(boolean type, Date time, PickupPoint pickupPoint) {
		super();
		this.type = type;
		this.time = time;
		this.pickupPoint = pickupPoint;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public PickupPoint getPickupPoint() {
		return pickupPoint;
	}

	public void setPickupPoint(PickupPoint pickupPoint) {
		this.pickupPoint = pickupPoint;
	}
	
	

}
