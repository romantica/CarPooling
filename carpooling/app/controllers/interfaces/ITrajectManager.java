package controllers.interfaces;

import java.util.List;

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
	public void cancelTraject(Traject traject);
	
	
	/**
	 * Supprime des trajets de la base de donnee,
	 * notifie les passager de l'annulation du conducteur
	 */
	public void cancelTraject(User driver, List<Traject> trajects);
	
	/**
	 * Un utilisateur a envoye un rating pour le trajet 
	 * traj qu'il a effectue
	 */
	public void arrivalNotification(Traject traj, short rating);
}