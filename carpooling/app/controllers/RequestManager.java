package controllers;

import java.util.Date;
import java.util.List;
import java.util.Timer;

import controllers.interfaces.IMatching;
import controllers.interfaces.IRequestManager;
import controllers.interfaces.ITimer;
import controllers.interfaces.IHandler;
import controllers.interfaces.ICommunication;

import models.objects.Request;
import models.objects.Traject;

public class RequestManager implements IRequestManager{

	/**
	 * Enregistre la requete dans la base de donnees
	 */
	public void recordRequest(Request request){
		request.save();
	}
	
	/**
	 * Supprime la requete dans la base de donnees
	 */
	public void deleteRequest(Request request){
		request.delete();
	}

	/**
	 * Lance le matching pour la requete et renvoie
	 * une liste de trajets
	 */
	public List<Traject> findTrajects(Request request){
		return IMatching.match(request);
	}

	/**
	 * Lance un timer pour faire un matching plus tard.
	 */
	public void matchLater(Request request){
		MatchLaterHandler mlh = new MatchLaterHandler(request);
		mlh.execute();
	}
	
	

	public class MatchLaterHandler implements IHandler{

		private Request request;

		public MatchLaterHandler(Request request){
			this.request = request;
		}

		public void execute(){
			if( IMatching.match(request) == null){
				//restart
				int timeOut = (request.getArrivalTime().getTime() - new Date().getTime() > 24*60*60) ? 24*60*60 : 2*60*60;
				ITimer timer = new Timer();
				timer.WakeInTime(timeOut, this);
			} 
			else{
				//Communicate
				ICommunication.trajectFound(request.getUser());
			}
		}

	}
}

