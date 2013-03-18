package controllers.interfaces;

import java.util.List;

import models.objects.Traject;
import models.objects.User;

public abstract class ITrajectManager{
	
	/**
	 * Enregistre un trajet dans la base de donnees
	 * @throws Exception 
	 */
	public static void recordTraject(Traject traj, User user) throws Exception{
		
	}

	/**
	 * Supprime un trajet de la base de donnee,
	 * notifie le conducteur de l'annulation du passager
	 */
	public static void cancelTraject(Traject traject) {
	}
	
	
	/**
	 * Supprime des trajets de la base de donnee,
	 * notifie les passager de l'annulation du conducteur
	 */
	public static void cancelTraject(User driver, List<Traject> trajects) {
	}
	
	/**
	 * Un utilisateur a envoye un rating pour le trajet 
	 * traj qu'il a effectue
	 */
	public static void arrivalNotification(Traject traj, short rating) {
	}
}