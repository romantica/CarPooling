package controllers;

import static play.mvc.Controller.session;

import java.util.List;

import models.objects.Car;
import models.objects.User;
import play.db.ebean.Model;
import controllers.interfaces.IUserManager;

public class UserManager implements IUserManager {

	public void createUser(String login, String lastName, String firstName,
			String accountNumber, String phone, String email) {
		User user = new User(login, firstName, lastName, email, phone, 100, null,
				null, null, null, null);
		user.save();
	}

	// TODO: A ajouter dans l'interface
	// TODO: getters: login, lastName, firstName, accountNumber, phone, email
	// TODO: setters: lastName, firstName, accountNumber, phone, email

	public User getUser(String login) {
		User user = User.find.where().eq("login", login).findUnique(); // attention, plusieurs users peuvent avoir
										// le meme login
		return user;
	}

	public void addCar(Car car) {
		User user = getUser(session("username"));
		car.save();
		user.addCar(car);
		user.save();
	}

	public List<Car> getCar(String login) {
		User user = getUser(login);
		return user.getCars();
	}

	public List<Car> getCar(User user) {
		user = getUser(user.getLogin());
		return user.getCars();
	}

	public List<User> getUsers(){
		return new Model.Finder<Integer, User>(Integer.class, User.class).all();
	}

	public static User getUserLogged() {
		String login = session("username");
		if (login == null) 
			return null;
		User user = User.find.where().eq("login", login).findUnique();
		return user;
	}

	public void createUser(User user) {
		user.save();
	}
}
