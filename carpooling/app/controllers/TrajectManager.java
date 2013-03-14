package controllers;

import java.util.ArrayList;
import java.util.List;

import models.objects.Traject;
import models.objects.User;
import controllers.interfaces.ICommunication;
import controllers.interfaces.ITrajectManager;

public class TrajectManager implements ITrajectManager {

	@Override
	public void recordTraject(Traject traj, User user) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Supprime un trajet de la base de donnee,
	 * notifie le conducteur de l'annulation du passager
	 */
	public void cancelTraject(Traject traj) {
		// TODO Delete in database
		
		ICommunication.requestCancelled(traj.getUser(), traj);
		

	}
	
	@Override
	public void cancelTraject(User driver, List<Traject> trajects) {
		// TODO 
		
		for(Traject t : trajects){
			ICommunication.proposalCancelled(t.getUser(), t);
		}

	}

	@Override
	public void arrivalNotification(Traject traj, short rating) {
		// TODO Auto-generated method stub

		traj.getProposal().getUser().getAssessment().setRating(traj.getProposal().getUser().getAssessment().getRating() + rating);
	}

}
