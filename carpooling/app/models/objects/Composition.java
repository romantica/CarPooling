package models.objects;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
@Table(name="Composition")
public class Composition extends Model {
	
	@Id
	private int id;
	
	@Version
    public Timestamp lastUpdate;
	
	private boolean type;// TRUE = departure ; FALSE = arrival
	private Date time;
	
	@Constraints.Required
	@OneToOne
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

	public static void create(Composition compo) {
		compo.save();
	}
	
	public static void delete(Composition compo) {
		compo.delete();
	}
}
