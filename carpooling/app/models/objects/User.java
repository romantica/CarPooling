package models.objects;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
@Table(name="User")
public class User extends Model {

	@Version
    public Timestamp lastUpdate;
	
	@Id
	private int id;
	
	@Constraints.Required
	private String login, firstName, name, email, phoneNumber, password;
	private int balance;
	@ManyToMany
	private List<Assessment> assessment;
	@ManyToMany
	private List<Car> cars;
	@ManyToMany
	private List<Proposal> proposals;
	@ManyToMany
	private List<Traject> trajects;
	@ManyToMany
	private List<Request> request;

    public User(){}


    public User(String login, String firstName, String name, String email,
			String phoneNumber, int balance, List<Assessment> assessment,
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
	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<Assessment> getAssessment() {
		return assessment;
	}
	
	public void addAssessment(Assessment assessment) {
		this.getAssessment().add(assessment);
	}

	public void setAssessment(List<Assessment> assessment) {
		this.assessment = assessment;
	}
	
	public float getMeanRating() {
		if (this.getAssessment() == null || this.getAssessment().size() == 0)
			return 3.0F;
		float result = 0;
		for (Assessment a : this.getAssessment())
			result+= a.getRating();
		return result / this.getAssessment().size();
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	public void addCar(Car car){
		cars.add(car);
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
	
	public void addTraject(Traject t){
		this.trajects.add(t);
	}

	public List<Request> getRequest() {
		return request;
	}

	public void setRequest(List<Request> request) {
		this.request = request;
	}
	
	public void addRequest(Request request){
		this.request.add(request);
	}
	
	public int getId() {
		return this.id;
	}

	public void addProposal(Proposal prop) {
		this.proposals.add(prop);
	}
	
	public void removeProposal(Proposal prop) {
		this.proposals.remove(prop);
	}
	
	public void removeTraject(Traject traj) {
		this.trajects.remove(traj);
	}
	
	public static Finder<Integer, User> find = new Finder<Integer, User>(Integer.class, User.class);
	
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
                //", proposals=" + proposals +
                //", trajects=" + trajects +
                //", request=" + request +
                '}';
    }
}
