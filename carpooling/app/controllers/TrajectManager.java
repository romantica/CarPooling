package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.*;

import models.objects.Proposal;
import models.objects.Traject;
import models.objects.User;
import controllers.interfaces.ICommunication;
import controllers.interfaces.IHandler;
import controllers.interfaces.IPayment;
import controllers.interfaces.ITimer;
import controllers.interfaces.ITrajectManager;

public class TrajectManager extends ITrajectManager {
	
	private static Lock l = new ReentrantLock();
	
	private static ArrayList<ReminderHandler> timers = new ArrayList<ReminderHandler>();
	
	public static void recordTraject(Traject traj, User user) throws Exception {
		
		l.lock();
		
		int seat =  traj.getProposal().getAvailableSeats();
		if(seat > 0){
			
			
			traj.getProposal().setAvailableSeats(seat-1);
			traj.getProposal().save();
			traj.save();
			
			IPayment.debit(traj.getUser(), traj.getTotalCost());
			
			//Ajout de la proposal pour le timer 
			for(ReminderHandler t : timers){
				if(t.getProposal().equals(traj.getProposal())){
					break;
				}
				ITimer timer = new TimerCP();
				ReminderHandler rh = new ReminderHandler(traj.getProposal());
				timers.add(rh);
				timer.wakeAtDate(new Date(traj.getArrivalPP().getTime().getTime() - 600), rh );
				
				
			}
		}
		else{
			throw new Exception("Il n'y a plus de place dans la voiture");
		}
		
		l.unlock();

	}
	
	//TODO : argent pour les annulation!

	/**
	 * Supprime un trajet de la base de donnee,
	 * notifie le conducteur de l'annulation du passager	 * 
	 */
	public static void cancelTraject(Traject traj) {
		if(traj.getProposal().getTraject().size()-1 > 0){
			for(ReminderHandler t : timers){
				if(t.getProposal().equals(traj.getProposal())){
					timers.remove(t);
					break;
				}
			}
		}
		
		IPayment.credit(traj.getUser(), traj.getTotalCost());
		ICommunication.requestCancelled(traj.getUser(), traj);
		traj.delete();

	}

	public static void proposalCancelled(Proposal prop){
		for(Traject t : prop.getTraject()){
			IPayment.credit(t.getUser(), t.getTotalCost());
			ICommunication.proposalCancelled(t.getUser(), t);
			t.delete();
		}
	}

	public static void cancelTraject(User driver, List<Traject> trajects) {
		for(Traject t : trajects){
			ICommunication.proposalCancelled(t.getUser(), t);
			t.delete();
		}
	}

	public static void arrivalNotification(Traject traj, short rating) {
		if(rating < 0){
			ICommunication.helpMeSatff(traj);
			return;
		}

		User driver = traj.getProposal().getUser();
		driver.getAssessment().setRating(driver.getAssessment().getRating()+rating);
		IPayment.credit(driver, traj.getTotalCost());
		traj.delete();
	}



	public static class ReminderHandler implements IHandler{

		private Proposal prop;
		private boolean stop;

		public ReminderHandler(Proposal prop){
			this.prop = prop;
			stop = false;
		}

		public void execute(){
			//Communicate
			if (stop) return;
			ICommunication.trajectReminder(prop);
		}
		
		public Proposal getProposal(){
			return prop;
		}
		
		public void stop(){
			stop = true;
		}
	}
}
