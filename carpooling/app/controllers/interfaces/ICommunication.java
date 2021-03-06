package controllers.interfaces;

import java.util.*;
import models.objects.*;
import com.typesafe.plugin.*;

public abstract class ICommunication{

    private static String sender = "noreply@carpooling.uclouvain.be";

    /**
     * Si une offre de trajet est supprimee, alors une notification
     * d'annulation de trajet est envoyee au passager de ce
     * trajet. Cette methode devra etre appelee pour chaque
     * passager concerne
     */
    public static void requestCancelled(User driver, Traject t){
        String dest = driver.getEmail();
        String subject = "[UCL Carpooling] Request cancelled";
        String message = "Hello,\n\nThe user "+ driver.getLogin() +" cancelled its request for the following proposal :\n\nDeparture address : "
                +t.getDeparturePP().getPickupPoint().getAddress()+"\nArrival address : "+t.getArrivalPP().getPickupPoint().getAddress()
                +"\nDeparture time : "+t.getProposal().getItinerary(t.getDeparturePP().getPickupPoint()).getDepartureTime().toString()
                +"\n\nThe UCL Carpooling team";
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject(subject);
        mail.addRecipient(dest);
        mail.addFrom(sender);
        mail.send(message);
    }

    public static void proposalCancelled(User passenger, User driver, Traject t){
        String dest = passenger.getEmail();
        String subject = "[UCL Carpooling] Proposal cancelled";
        String message = "Hello,\n\nThe user "+ driver.getLogin() +" cancelled the following proposal :\n\nDeparture address : "
                +t.getDeparturePP().getPickupPoint().getAddress()+"\nArrival address : "+t.getArrivalPP().getPickupPoint().getAddress()
                +"\nDeparture time : "+t.getProposal().getItinerary(t.getDeparturePP().getPickupPoint()).getDepartureTime().toString()
                +"\n\nThe corresponding traject is cancelled.\n\nThe UCL Carpooling team";
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject(subject);
        mail.addRecipient(dest);
        mail.addFrom(sender);
        mail.send(message);
    }

    /**
     * 10 minutes avant le debut du trajet, les passagers et le
     * conducteur recoivent un rappel contenant les infos du
     * trajet.
     */
    public static void trajectReminder(Proposal prop){
	List<Traject> traject = prop.getTraject();
	String subject = "[UCL Carpooling] Traject Reminder";
	for(Traject t: traject){
            String message = "Hello,\n\nThe following traject is about to start :\n\nDeparture address : "
                    +t.getDeparturePP().getPickupPoint().getAddress()+"\nArrival address : "+t.getArrivalPP().getPickupPoint().getAddress()
                    +"\nDeparture time : "+prop.getItinerary(t.getDeparturePP().getPickupPoint()).getDepartureTime().toString()
                    +"\nArrival time : "+prop.getItinerary(t.getArrivalPP().getPickupPoint()).getArrivalTime().toString()
                    +"\n\nThe UCL Carpooling team";
            MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
            mail.addRecipient(t.getUser().getEmail());
            mail.setSubject(subject);
            mail.addFrom(sender);
            mail.send(message);
	}
	String messageDriver = "Hello,\n\nThe traject corresponding to your proposal is about to start. Please be on your way as soon as possible"
                    		+"\n\nThe UCL Carpooling team";
	MailerAPI mailDriver = play.Play.application().plugin(MailerPlugin.class).email();
	mailDriver.addRecipient(prop.getUser().getEmail());
	mailDriver.setSubject(subject);
	mailDriver.addFrom(sender);
	mailDriver.send(messageDriver);
    }

    /**
     * Si une requete etait restee sans resultat et qu'un trajet
     * est finalement trouve plus tard, alors l'utilisateur est
     * prevenu.
     */
    public static void trajectFound(User user){
        String dest = user.getEmail();
        String subject = "[UCL Carpooling] Traject found";
        String message = "Hello,\n\nA new traject corresponding to your request has been created. Please go to www.uclcarpooling.be to get all details.\n\nThe UCL Carpooling team";
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject(subject);
        mail.addRecipient(dest);
        mail.addFrom(sender);
        mail.send(message);
    }

    /**
     * Lorsqu'un passager selectionne un trajet, le conducteur
     * recoit les informations qui permettront le ramassage.
     */
    public static void newPassengerNotification(Traject t){
        String dest = t.getUser().getEmail();
        String subject = "[UCL Carpooling] New passenger registered";
        String message = "Hello,\n\nA new passenger has been added to the following traject :\n\nDeparture address : "
                +t.getDeparturePP().getPickupPoint().getAddress()+"\nArrival address : "+t.getArrivalPP().getPickupPoint().getAddress()
                +"\nReserved seats : "+t.getReservedSeats()
                +"\nDeparture time : "+t.getProposal().getItinerary(t.getDeparturePP().getPickupPoint()).getDepartureTime().toString()
                +"\nArrival time : "+t.getProposal().getItinerary(t.getArrivalPP().getPickupPoint()).getArrivalTime().toString()
                +"\n\nThe UCL Carpooling team";
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject(subject);
        mail.addRecipient(dest);
        mail.addFrom(sender);
        mail.send(message);
    }

    /**
     * Notifie un utilisateur lors de la creation de son compte.
     */
    public static void newUser(User u){
        String dest = u.getEmail();
        String subject = "[UCL Carpooling] Account creation";
        String message = "Hello, \n\nA new account has been created on www.uclcarpooling.be with the following information :\n\nLogin : "
                +u.getLogin()+"\nEmail address : "+u.getEmail()+"\n\nThe UCL Carpooling team";
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject(subject);
        mail.addRecipient(dest);
        mail.addFrom(sender);
        mail.send(message);
    }

    /**
     * Contact le staff en cas de rating negatif.
     */
    public static void helpMeStaff(Traject t){
        String dest = "staff@carpooling.uclouvain.be";
        String subject = "[UCL Carpooling] Traject conflict";
        String message = "Hello,\n\nThere is a conflict for the following traject :\n\nDeparture address : "
                +t.getDeparturePP().getPickupPoint().getAddress()+"\nArrival address : "+t.getArrivalPP().getPickupPoint().getAddress()
                +"\nReserved seats : "+t.getReservedSeats()
                +"\nDeparture time : "+t.getProposal().getItinerary(t.getDeparturePP().getPickupPoint()).getDepartureTime().toString()
                +"\nArrival time : "+t.getProposal().getItinerary(t.getArrivalPP().getPickupPoint()).getArrivalTime().toString()
                +"\n\nThe UCL Carpooling team";
        MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
        mail.setSubject(subject);
        mail.addRecipient(dest);
        mail.addFrom(sender);
        mail.send(message);
    }
}