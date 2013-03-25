package controllers;

import models.objects.User;
import controllers.interfaces.IPayment;

public class Payment implements IPayment {

	@Override
	public double getBalance(User user) {
		// TODO Auto-generated method stub
		return User.find.where().eq("login", user.getLogin()).findUnique().getBalance();
//		return user.getBalance();
	}

	@Override
	public void debit(User user, double amount) {
		double newBalance = getBalance(user) - amount;
//		User.find.where().eq("login", user.getLogin()).findUnique().setBalance(newBalance);
		user.setBalance(newBalance);
		user.save();
		return;
	}

	@Override
	public void credit(User user, double amount) {
		double newBalance = getBalance(user) + amount;
//		User.find.where().eq("login", user.getLogin()).findUnique().setBalance(newBalance);
		user.setBalance(newBalance);
		user.save();		
	}

}
