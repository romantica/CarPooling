package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.*;

import com.avaje.ebean.Ebean;

import models.objects.Assessment;
import models.objects.Itinerary;
import models.objects.Proposal;
import models.objects.Request;
import models.objects.Traject;
import models.objects.User;
import controllers.interfaces.ICommunication;
import controllers.interfaces.IHandler;
import controllers.interfaces.IPayment;
import controllers.interfaces.ITimer;
import controllers.interfaces.ITrajectManager;

public class TrajectManager extends ITrajectManager {

	private static Lock l = new ReentrantLock();

	private static int minutes15 = 15;
	private static int hours24 = 60;

	private static ArrayList<ProposalReminderHandler> proposalRemTimers = new ArrayList<ProposalReminderHandler>();
	private static ArrayList<EndHandler> endTimers = new ArrayList<EndHandler>();

	/*
	 * Il faut : 
	 *   - un timer pour le trajectReminder
	 *   - un timer pour dire que le trajet est ok 24 après la deadlines
	 *   - un timer pour le proposalReminder (unique)
	 */

	public static void recordTraject(Traject traj, User user) throws Exception {
		l.lock();

		int seat =  traj.getProposal().getAvailableSeats();
		if(seat > 0 && user.getBalance() >= traj.getReservedSeats()*traj.getTotalCost()){

			traj.getDeparturePP().save();
			traj.getArrivalPP().save();

			traj.getProposal().setAvailableSeats(seat-1);
			traj.getProposal().addTraject(traj);
			traj.getUser().addTraject(traj);

			Ebean.save(traj.getRequest());
			traj.getRequest().setTraject(traj);

			Ebean.save(traj);
			Ebean.save(traj.getProposal());
			Ebean.save(traj.getRequest());
			Ebean.save(traj.getUser());

			Payment.debit(traj.getUser(), (int)traj.getTotalCost());

			createTimer(traj);


		}
		else{
			l.unlock();
			if(seat > 0) {
				throw new Exception("Il n'y a plus de place dans la voiture");
			} else {
				throw new Exception("Vous n'avez pas assez d'argent pour ce trajet.");
			}
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
		IPayment.credit(traj.getUser(), (int)traj.getTotalCost());
		ICommunication.requestCancelled(traj.getUser(), traj);
		
		deleteTraject(traj);

	}

	public static void proposalCancelled(Proposal prop){
		for(Traject t : prop.getTraject()){
				
			IPayment.credit(t.getUser(), (int)t.getTotalCost());
			ICommunication.proposalCancelled(t.getUser(), User.find.where().eq("id", prop.getUser().getId()).findUnique(), t);
			
			deleteTraject(t);
		}
	}

	public static void arrivalNotification(Traject traj, short rating, String comment) {
		if(rating < 3){
			ICommunication.helpMeStaff(traj);
			return;
		}

		User driver = traj.getProposal().getUser();
		//driver.getAssessment().setRating(driver.getAssessment().getRating()+rating);
		Assessment a = new Assessment(rating, comment, true);
		a.save();
		driver.addAssessment(a);
		driver.update();
		IPayment.credit(driver, (int)traj.getTotalCost());
		
		deleteTraject(traj);
	}
	
	private static void deleteTraject(Traject traj){
		cancelTimer(traj);
		Traject.delete(traj);
		
		if(traj.getArrivalPP().getTime().before(new Date())){
			traj.getUser().getRequest().remove(traj.getRequest());
			traj.getUser().save();
			traj.getRequest().delete();
		}

	}


	private static void createTimer(Traject traj){
		//Ajout pour les timers
		ITimer timer = new TimerCP();
		//porposalReminder : seulement si il n'existe pas déjà
		boolean exist = false;
		for(ProposalReminderHandler p: proposalRemTimers){
			if(p.getProposal().equals(traj.getProposal())){
				exist = true;
				break;
			}
		}
		if(!exist){
			ProposalReminderHandler prh = new ProposalReminderHandler(traj.getProposal());
			timer.wakeAtDate(new Date(traj.getDeparturePP().getTime().getTime() - minutes15), prh);
			proposalRemTimers.add(prh);
		}
		//EndHandler : obligatoire
		EndHandler eh = new EndHandler(traj);
		timer.wakeAtDate(new Date(traj.getArrivalPP().getTime().getTime() + hours24), eh);
		endTimers.add(eh);
	}


	private static void cancelTimer(Traject traj){
		//Suppression des timers
		//24h après : obligatoire
		for(EndHandler eh : endTimers){
			if (eh.getProposal().equals(eh)){
				eh.stop();
				endTimers.remove(eh);
				break;
			}
		}
		//ProposalReminder : seulement si c'est le dernier
		if(traj.getProposal().getTraject().size() == 1){
			for(ProposalReminderHandler prh: proposalRemTimers){
				if(prh.getProposal().equals(traj.getProposal())){
					prh.stop();
					proposalRemTimers.remove(prh);
					break;
				}
			}
		}
	}

	public static class ProposalReminderHandler implements IHandler{

		private Proposal prop;
		private boolean stop;

		public ProposalReminderHandler(Proposal prop){
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

	public static class EndHandler implements IHandler{

		private Traject traj;
		private boolean stop;

		public EndHandler(Traject traj){
			this.traj = traj;
			stop = false;
		}

		public void execute(){
			//Communicate
			if (stop) return;
			deleteTraject(traj);
		}

		public Traject getProposal(){
			return traj;
		}

		public void stop(){
			stop = true;
		}
	}
}
