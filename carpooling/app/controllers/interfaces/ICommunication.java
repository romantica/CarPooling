package controllers.interfaces;

import models.objects.*;
//import com.typesafe.plugin.*;

public abstract class ICommunication{
	
	private static String sender = "noreply@carpooling.uclouvain.be";
	
	/**
	 * Si une offre de trajet est supprimee, alors une notification 
	 * d'annulation de trajet est envoyee au passager de ce 
	 * trajet. Cette methode devra etre appelee pour chaque  
	 * passager concerne 
	 */
	public static void requestCancelled(User driver, Traject t){
        /*
		String dest = driver.getEmail();
		String subject = "[UCL Carpooling] Request cancelled";
		String message = "";
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		mail.setSubject(subject);
		mail.addRecipient(dest);
		mail.addFrom(sender);
		mail.send(message);
		*/
	}
	
	public static void proposalCancelled(User passenger, Traject t){
        /*
		String dest = passenger.getEmail();
		String subject = "[UCL Carpooling] Proposal cancelled";
		String message = "";
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		mail.setSubject(subject);
		mail.addRecipient(dest);
		mail.addFrom(sender);
		mail.send(message);
		*/
	}
	
	/**
	 * 10 minutes avant le debut du trajet, les passagers et le
	 * conducteur recoivent un rappel contenant les infos du 
	 * trajet.
	 */
	public static void trajectReminder(Proposal prop){
        /*
		String subject = "[UCL Carpooling] Traject Reminder";
		String message = "";
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		for(int i = 0; i < passenger.length; i++){
			mail.addRecipient(passenger[i].getEmail());
		}
		mail.addRecipient(driver.getEmail());
		mail.setSubject(subject);
		mail.addFrom(sender);
		mail.send("");
		*/
	}
	
	/**
	 * Si une requete etait restee sans resultat et qu'un trajet
	 * est finalement trouve plus tard, alors l'utilisateur est 
	 * prevenu.
	 */
	public static void trajectFound(User user){
        /*
		String dest = user.getEmail();
		String subject = "[UCL Carpooling] Traject found";
		String message = "";
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		mail.setSubject(subject);
		mail.addRecipient(dest);
		mail.addFrom(sender);
		mail.send(message);
		*/
	}
	
	/**
	 * Lorsqu'un passager selectionne un trajet, le conducteur
	 * recoit les informations qui permettront le ramassage.
	 */
	public static void newPassengerNotification(Traject t){
        /*
		String dest = t.getProposal().getUser().getEmail();
		String subject = "[UCL Carpooling] New passenger registered";
		String message = "";
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		mail.setSubject(subject);
		mail.addRecipient(dest);
		mail.addFrom(sender);
		mail.send(message);
		*/
	}
	
	/**
	 * Notifie un utilisateur lors de la creation de son compte.
	 */
	public static void newUser(User u){
        /*
		String dest = u.getEmail();
		String subject = "[UCL Carpooling] Account creation";
		String message = "";
		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		mail.setSubject(subject);
		mail.addRecipient(dest);
		mail.addFrom(sender);
		mail.send(message);
		*/
	}
	
	/**
	 * Contact le satff en cas de rating negatif.
	 */
	public static void helpMeStaff(Traject t){
		
	}
}