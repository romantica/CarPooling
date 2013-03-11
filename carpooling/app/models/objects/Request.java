package models.objects;

import java.util.Date;


public class Request {
	private Coordinate departureCoordinates, arrivalCoordinates;
	private String departureAddress, arrivalAddress;
	private Date arrivalTime;
	private int necessarySeats, toleranceTime, toleranceWalkDistance;
	private float tolearncePrice;
	
	private User user;
	private Traject traject;
	
	public Request(Coordinate departureCoordinates,
			Coordinate arrivalCoordinates, String departureAddress,
			String arrivalAddress, Date arrivalTime, int necessarySeats,
			int toleranceTime, int toleranceWalkDistance, float tolearncePrice,
			User user, Traject traject) {
		super();
		this.departureCoordinates = departureCoordinates;
		this.arrivalCoordinates = arrivalCoordinates;
		this.departureAddress = departureAddress;
		this.arrivalAddress = arrivalAddress;
		this.arrivalTime = arrivalTime;
		this.necessarySeats = necessarySeats;
		this.toleranceTime = toleranceTime;
		this.toleranceWalkDistance = toleranceWalkDistance;
		this.tolearncePrice = tolearncePrice;
		this.user = user;
		this.traject = traject;
	}

	public Coordinate getDepartureCoordinates() {
		return departureCoordinates;
	}

	public void setDepartureCoordinates(Coordinate departureCoordinates) {
		this.departureCoordinates = departureCoordinates;
	}

	public Coordinate getArrivalCoordinates() {
		return arrivalCoordinates;
	}

	public void setArrivalCoordinates(Coordinate arrivalCoordinates) {
		this.arrivalCoordinates = arrivalCoordinates;
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

	public float getTolearncePrice() {
		return tolearncePrice;
	}

	public void setTolearncePrice(float tolearncePrice) {
		this.tolearncePrice = tolearncePrice;
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
		
	
	
}
