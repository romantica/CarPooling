package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.interfaces.IMatching;
import controllers.interfaces.IRequestManager;
import controllers.interfaces.ITimer;
import controllers.interfaces.IHandler;
import controllers.interfaces.ICommunication;

import models.objects.Request;
import models.objects.Traject;

public class RequestManager implements IRequestManager{
	
	private ArrayList<MatchLaterHandler> timers;

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
		MatchLaterHandler r = null;
		for(MatchLaterHandler t : timers){
			if(t.getRequest() == request){
				t.stop();
				timers.remove(t);
				break;
			}
		}
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
		timers.add(mlh);
		mlh.execute();
	}
	
	

	public class MatchLaterHandler implements IHandler{

		private Request request;
		private boolean stop;

		public MatchLaterHandler(Request request){
			this.request = request;
			stop = false;
		}

		public void execute(){
			if(stop) return;
			if( IMatching.match(request) == null){
				//restart
				long timeOut = (request.getArrivalTime().getTime() - new Date().getTime() > 24*60*60) ? 24*60*60 : 2*60*60;
				ITimer timer = new TimerCP();
				timer.wakeInTime(timeOut, this);
			} 
			else{
				//Communicate
				ICommunication.trajectFound(request.getUser());
			}
		}
		
		public Request getRequest(){ return request; }
		
		public void stop(){
			stop = true;
		}

	}
}

