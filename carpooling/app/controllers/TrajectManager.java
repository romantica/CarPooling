package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.*;

import com.avaje.ebean.Ebean;

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
		//TODO : 24 après le trajet, il doit etre considéré comme OK!
		l.lock();
		
		int seat =  traj.getProposal().getAvailableSeats();
		if(seat > 0){
			
			traj.getDeparturePP().save();
			traj.getArrivalPP().save();
			
			traj.getProposal().setAvailableSeats(seat-1);
			traj.getProposal().addTraject(traj);
			traj.getUser().addTraject(traj);
			
			traj.getRequest().setTraject(traj);
			
			Payment.debit(traj.getUser(), (int)traj.getTotalCost());

			Ebean.save(traj);
			Ebean.save(traj.getProposal());
			Ebean.save(traj.getUser());
			Ebean.save(traj.getRequest());
			
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

    /**
     * Retourne le liste des traject de l'utilisateur.
     */
    public static List<Traject> getTrajects(User user){
        return user.getTrajects();
    }

	/**
	 * Supprime un trajet de la base de donnee,
	 * notifie le conducteur de l'annulation du passager
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
		
		IPayment.credit(traj.getUser(), (int)traj.getTotalCost());
		ICommunication.requestCancelled(traj.getUser(), traj);
		
		traj.getProposal().getTraject().remove(traj);
		traj.getProposal().save();
		traj.getUser().getTrajects().remove(traj);
		traj.getUser().save();
		traj.getRequest().setTraject(null);
		//TODO : quid de ce null?
		traj.getRequest().save();
		traj.delete();

	}

	public static void proposalCancelled(Proposal prop){
		for(Traject t : prop.getTraject()){
			IPayment.credit(t.getUser(), (int)t.getTotalCost());
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
			ICommunication.helpMeStaff(traj);
			return;
		}

		User driver = traj.getProposal().getUser();
		driver.getAssessment().setRating(driver.getAssessment().getRating()+rating);
		IPayment.credit(driver, (int)traj.getTotalCost());
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
            //TODO: compatibilite
			//ICommunication.trajectReminder(prop);
		}
		
		public Proposal getProposal(){
			return prop;
		}
		
		public void stop(){
			stop = true;
		}
	}
}
