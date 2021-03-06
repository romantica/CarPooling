package models.objects;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
@Table(name="Request")
public class Request extends Model {
	
	@Id
	private int id;
	
	@Version
    public Timestamp lastUpdate;
	
	@Constraints.Required
	private double depCoordinateX, depCoordinateY, arCoordinateX, arCoordinateY;
	private String departureAddress, arrivalAddress;
	private Date arrivalTime;
	
	// In milliseconds and meters
	private int necessarySeats, toleranceTime, toleranceWalkDistance;
	private float tolerancePrice;

    @ManyToOne
	private User user;
    @OneToOne
	private Traject traject;
	
	public Request(Coordinate departureCoordinates,
			Coordinate arrivalCoordinates, String departureAddress,
			String arrivalAddress, Date arrivalTime, int necessarySeats,
			int toleranceTime, int toleranceWalkDistance, float tolerancePrice,
			User user, Traject traject) {
		super();
		this.setDepartureCoordinates(departureCoordinates);
		this.setArrivalCoordinates(arrivalCoordinates);
		this.departureAddress = departureAddress;
		this.arrivalAddress = arrivalAddress;
		this.arrivalTime = arrivalTime;
		this.necessarySeats = necessarySeats;
		this.toleranceTime = toleranceTime;
		this.toleranceWalkDistance = toleranceWalkDistance;
		this.tolerancePrice = tolerancePrice;
		this.user = user;
		this.traject = traject;
	}

	public Coordinate getDepartureCoordinates() {
		return new Coordinate(depCoordinateX, depCoordinateY);
	}

	public void setDepartureCoordinates(Coordinate departureCoordinates) {
		if (!(departureCoordinates == null))
		{
			this.depCoordinateX = departureCoordinates.getX();
			this.depCoordinateY = departureCoordinates.getY();
		}
	}

	public Coordinate getArrivalCoordinates() {
		return new Coordinate(arCoordinateX, arCoordinateY);
	}

	public void setArrivalCoordinates(Coordinate arrivalCoordinates) {
		if (!(arrivalCoordinates == null))
		{
			this.arCoordinateX = arrivalCoordinates.getX();
			this.arCoordinateY = arrivalCoordinates.getY();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDepCoordinateX() {
		return depCoordinateX;
	}

	public void setDepCoordinateX(double depCoordinateX) {
		this.depCoordinateX = depCoordinateX;
	}

	public double getDepCoordinateY() {
		return depCoordinateY;
	}

	public void setDepCoordinateY(double depCoordinateY) {
		this.depCoordinateY = depCoordinateY;
	}

	public double getArCoordinateX() {
		return arCoordinateX;
	}

	public void setArCoordinateX(double arCoordinateX) {
		this.arCoordinateX = arCoordinateX;
	}

	public double getArCoordinateY() {
		return arCoordinateY;
	}

	public void setArCoordinateY(double arCoordinateY) {
		this.arCoordinateY = arCoordinateY;
	}

	public String getDepartureAddress() {
		return departureAddress;
	}

	public void setDepartureAddress(String departureAddress) {
		this.departureAddress = departureAddress;
	}

	public String getArrivalAddress() {
		return arrivalAddress;
	}

	public void setArrivalAddress(String arrivalAddress) {
		this.arrivalAddress = arrivalAddress;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getNecessarySeats() {
		return necessarySeats;
	}

	public void setNecessarySeats(int necessarySeats) {
		this.necessarySeats = necessarySeats;
	}

	public int getToleranceTime() {
		return toleranceTime;
	}

	public void setToleranceTime(int toleranceTime) {
		this.toleranceTime = toleranceTime;
	}

	public int getToleranceWalkDistance() {
		return toleranceWalkDistance;
	}

	public void setToleranceWalkDistance(int toleranceWalkDistance) {
		this.toleranceWalkDistance = toleranceWalkDistance;
	}

	public float getTolerancePrice() {
		return tolerancePrice;
	}

	public void setTolerancePrice(float tolerancePrice) {
		this.tolerancePrice = tolerancePrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Traject getTraject() {
		return traject;
	}

	public void setTraject(Traject traject) {
		this.traject = traject;
	}
		
	public static Finder<Integer, Request> find = new Finder<Integer, Request>(Integer.class, Request.class);
	
}
