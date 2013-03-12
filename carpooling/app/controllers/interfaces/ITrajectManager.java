package controllers.interfaces;

import models.objects.Traject;
import models.objects.User;

public interface ITrajectManager{
	
	/**
	 * Enregistre un trajet dans la base de donnees
	 */
	public void recordTraject(Traject traj, User user);

	/**
	 * Supprime un trajet de la base de donnee,
	 * notifie le conducteur de l'annulation du passager
	 */
	public void cancelTraject(Traject traj);
	
	/**
	 * Un utilisateur a envoye un rating pour le trajet 
	 * traj qu'il a effectue
	 */
	public void arrivalNotification(Traject traj, short rating);
}