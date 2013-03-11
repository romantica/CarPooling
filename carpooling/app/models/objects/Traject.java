package models.objects;

public class Traject {
	
	private int reservedSeats;
	private float totalCost;
	
	private Request request;
	private User user;
	private Composition departurePP, ArrivalPP;
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
		ArrivalPP = arrivalPP;
		this.proposal = proposal;
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
		return ArrivalPP;
	}

	public void setArrivalPP(Composition arrivalPP) {
		ArrivalPP = arrivalPP;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}
	
	
	
	
}
