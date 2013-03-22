package controllers.interfaces;

import models.objects.User;

public abstract class IPayment{
	/**
	 * Connaitre le montant du compte de l'utilisateur
	 */
	public static double getBalance(User user){ 
		return 0;
	}
	
	/**
	 * Debite le compte de l'utilisateur du montant amount
	 */
	public static void debit(User user, double amount){
	
	}
	
	/**
	 * Credite le compte de l'utilisateur du montant amount
	 */
	public static void credit(User user, double amount){
		
	}
}