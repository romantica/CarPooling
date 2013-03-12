package controllers.interfaces;

import models.objects.*;

abstract class Communication{
	
	/**
	 * Si une offre de trajet est supprimee, alors une notification 
	 * d'annulation de trajet est envoyee au passager de ce 
	 * trajet. Cette methode devra etre appelee pour chaque  
	 * passager concerne 
	 */
	public static void trajectCancelled(User driver, Traject[] t){
		
	}
	
	/**
	 * 10 minutes avant le debut du trajet, les passagers et le
	 * conducteur recoivent un rappel contenant les infos du 
	 * trajet.
	 */
	public static void trajectReminder(User[] passenger, User driver, Traject t){
		
	}
	
	/**
	 * Si une requete etait restee sans resultat et qu'un trajet
	 * est finalement trouve plus tard, alors l'utilisateur est 
	 * prevenu.
	 */
	public static void trajectFound(User user){
		
	}
	
	/**
	 * Lorsqu'un passager selectionne un trajet, le conducteur
	 * recoit les informations qui permettront le ramassage.
	 */
	public static void newPassengerNotification(Traject t){
		
	}
	
	/**
	 * Notifie un utilisateur lors de la creation de son compte.
	 */
	public static void newUser(User u){
		
	}
	
	/**
	 * Contact le satff en cas de rating negatif.
	 */
	public static void helpMeSatff(Traject t){
		
	}
}