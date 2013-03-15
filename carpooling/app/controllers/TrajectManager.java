package controllers;

import java.util.Date;
import java.util.List;
import java.util.Timer;

import models.objects.Request;
import models.objects.Traject;
import models.objects.User;
import controllers.interfaces.ICommunication;
import controllers.interfaces.IHandler;
import controllers.interfaces.IMatching;
import controllers.interfaces.ITimer;
import controllers.interfaces.ITrajectManager;

public class TrajectManager implements ITrajectManager {

	@Override
	public void recordTraject(Traject traj, User user) throws Exception {
		int seat =  traj.getProposal().getAvailableSeats();
		if(seat > 0){
			traj.getProposal().setAvailableSeats(seat-1);
			traj.getProposal().save();
			traj.save();
			
			ITimer timer = new Timer();
			timer.WakeAtDate(new Date(traj.getArrivalPP().getTime().getTime() - 600), new ReminderHandler(traj));
			
		}
		else{
			throw new Exception("Il n'y a plus de place dans la voiture");
		}
		
	}

	/**
	 * Supprime un trajet de la base de donnee,
	 * notifie le conducteur de l'annulation du passager
	 */
	public void cancelTraject(Traject traj) {
		
		ICommunication.requestCancelled(traj.getUser(), traj);
		traj.delete();

	}
	
	@Override
	public void cancelTraject(User driver, List<Traject> trajects) {
				
		for(Traject t : trajects){
			ICommunication.proposalCancelled(t.getUser(), t);
			t.delete();
		}
		
		

	}

	@Override
	public void arrivalNotification(Traject traj, short rating) {

		traj.getProposal().getUser().getAssessment().setRating(traj.getProposal().getUser().getAssessment().getRating() + rating);
		traj.delete();
	}
	
	public class ReminderHandler implements IHandler{

		private Traject traject;

		public ReminderHandler(Traject traject){
			this.traject = traject;
		}

		public void execute(){
			//Communicate
			ICommunication.trajectReminder(traject);
		}

		@Override
		public void execute(Object... objs) {
			// TODO Auto-generated method stub
			
		}
	}

}
