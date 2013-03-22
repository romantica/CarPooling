package controllers;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.*;

import models.objects.Proposal;
import models.objects.Traject;
import models.objects.User;
import controllers.interfaces.ICommunication;
import controllers.interfaces.IHandler;
import controllers.interfaces.ITimer;
import controllers.interfaces.ITrajectManager;

public class TrajectManager extends ITrajectManager {

	public static void recordTraject(Traject traj, User user) throws Exception {
		Lock l = new ReentrantLock();
		l.lock();
		try{
			int seat =  traj.getProposal().getAvailableSeats();
			if(seat > 0){
				traj.getProposal().setAvailableSeats(seat-1);
				traj.getProposal().save();
				traj.save();

				ITimer timer = new TimerCP();
				timer.wakeAtDate(new Date(traj.getArrivalPP().getTime().getTime() - 600), new ReminderHandler(traj));

			}
			else{
				throw new Exception("Il n'y a plus de place dans la voiture");
			}
		} finally {
			l.unlock();
		}

	}

	/**
	 * Supprime un trajet de la base de donnee,
	 * notifie le conducteur de l'annulation du passager
	 */
	public static void cancelTraject(Traject traj) {

		ICommunication.requestCancelled(traj.getUser(), traj);
		traj.delete();

	}

	public static void proposalCancelled(Proposal prop){
		for(Traject t : prop.getTraject()){
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

		traj.getProposal().getUser().getAssessment().setRating(traj.getProposal().getUser().getAssessment().getRating() + rating);
		traj.delete();
	}



	public static class ReminderHandler implements IHandler{

		private Traject traject;

		public ReminderHandler(Traject traject){
			this.traject = traject;
		}

		public void execute(){
			//Communicate
			//TODO : quid du null null?
			ICommunication.trajectReminder(null, null, traject);
		}
	}

}
