package controllers.interfaces;

import models.objects.Car;
import models.objects.User;

public interface UserManager{
	
	/**
	 * Cree un nouvel utilisateur dans la base de donnnees
	 */
	public User createUser(String login, String lastName, String firstName, String accountNumber, String phone, String email);
	
	// A ajouter dans l'interface
	// getters: login, lastName, firstName, accountNumber, phone, email
	// setters: lastName, firstName, accountNumber, phone, email
	
	/**
	 * Recupere un utilisateur dans la base de donnees
	 */
	public User getUser(String login);
	
	/**
	 * Ajout et supprime une voiture dans la base de donnees
	 */
	public void addCar(Car car);
	public void removeCar(Car car);
}