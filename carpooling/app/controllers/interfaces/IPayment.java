package controllers.interfaces;

import models.objects.User;

public interface IPayment{
	/**
	 * Connaitre le montant du compte de l'utilisateur
	 */
	public double getBalance(User user);
	
	/**
	 * Debite le compte de l'utilisateur du montant amount
	 */
	public void debit(User user, double amount);
	
	/**
	 * Credite le compte de l'utilisateur du montant amount
	 */
	public void credit(User user, double amount);
}