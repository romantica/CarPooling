package models.objects;

import java.util.List;


public class User {

	private String login, firstName, name, email, phoneNumber;
	private int balance;
	private Assessment assessment;
	
	private List<Car> cars;
	private List<Proposal> proposals;
	private List<Traject> trajects;
	private List<Request> request;

    public User(){}


    public User(String login, String firstName, String name, String email,
			String phoneNumber, int balance, Assessment assessment,
			List<Car> cars, List<Proposal> proposals, List<Traject> trajects,
			List<Request> request) {
		super();
		this.login = login;
		this.firstName = firstName;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
		this.assessment = assessment;
		this.cars = cars;
		this.proposals = proposals;
		this.trajects = trajects;
		this.request = request;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public List<Proposal> getProposals() {
		return proposals;
	}

	public void setProposals(List<Proposal> proposals) {
		this.proposals = proposals;
	}

	public List<Traject> getTrajects() {
		return trajects;
	}

	public void setTrajects(List<Traject> trajects) {
		this.trajects = trajects;
	}

	public List<Request> getRequest() {
		return request;
	}

	public void setRequest(List<Request> request) {
		this.request = request;
	}


    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                ", assessment=" + assessment +
                ", cars=" + cars +
                ", proposals=" + proposals +
                ", trajects=" + trajects +
                ", request=" + request +
                '}';
    }
}
