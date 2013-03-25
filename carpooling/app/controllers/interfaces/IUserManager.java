package controllers.interfaces;

import models.objects.Car;
import models.objects.User;

public interface IUserManager{
	
	/**
	 * Cree un nouvel utilisateur dans la base de donnnees
	 */
	public void createUser(String login, String lastName, String firstName, String accountNumber, String phone, String email);
	
	// A ajouter dans l'interface
	// getters: login, lastName, firstName, accountNumber, phone, email
	// setters: lastName, firstName, accountNumber, phone, email
	
	/**
	 * Recupere un utilisateur dans la base de donnees
	 */
	public User getUser(String login);
	
	/**
	 * Ajout une voiture dans la base de donnees
	 */
	public void addCar(Car car);
}