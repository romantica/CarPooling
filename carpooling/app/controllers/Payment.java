package controllers;

import models.objects.User;
import controllers.interfaces.IPayment;

public class Payment extends IPayment {

	public static int getBalance(User user) {
		// TODO Auto-generated method stub
		return User.find.where().eq("login", user.getLogin()).findUnique().getBalance();
//		return user.getBalance();
	}

	public static void debit(User user, int amount) {
		int newBalance = getBalance(user) - amount;
//		User.find.where().eq("login", user.getLogin()).findUnique().setBalance(newBalance);
		user.setBalance(newBalance);
		user.save();
		return;
	}

	public static void credit(User user, int amount) {
		int newBalance = getBalance(user) + amount;
//		User.find.where().eq("login", user.getLogin()).findUnique().setBalance(newBalance);
		user.setBalance(newBalance);
		user.save();		
	}

}
