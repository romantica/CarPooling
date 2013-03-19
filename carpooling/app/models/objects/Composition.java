package models.objects;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Composition extends Model {
	
	@Id
	private int id;
	
	private boolean type;
	private Date time;
	
	@Constraints.Required
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
	
	public static Finder<Integer, Composition> find = new Finder<Integer, Composition>(Integer.class, Composition.class);

}
