package controllers;

import static play.mvc.Controller.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.interfaces.IRequestManager;
import controllers.interfaces.ITimer;
import controllers.interfaces.IHandler;
import controllers.interfaces.ICommunication;

import models.objects.Coordinate;
import models.objects.Request;
import models.objects.Traject;
import models.objects.User;

public class RequestManager implements IRequestManager{
	
	private ArrayList<MatchLaterHandler> timers;

    //Timeout (real)
/*
    private final int timeout24 = 24*60*60;
    private final int timeout2  = 2*60*60;
*/
    //Timeout (simulation)
    private final int timeout24 = 1*60;
    private final int timeout2  = 30;

    public RequestManager(){
        timers = new ArrayList<MatchLaterHandler>();
    }

	/**
	 * Enregistre la requete dans la base de donnees
	 */
	public void recordRequest(Request request){
		request.getUser().addRequest(request);
		request.save();
		request.getUser().save();
	}

    /**
     * retourne la liste de toutes les request qui
     * sont sur la base de donnée
     */
    public List<Request> getlist(){
        User user = User.find.where().like("login", session("username")).findUnique();
    	return user.getRequest();
    }
	
	/**
	 * Supprime la requete dans la base de donnees
	 */
	public void deleteRequest(Request request){
		for(MatchLaterHandler t : timers){
			if(t.getRequest() == request){
				t.stop();
				timers.remove(t);
				break;
			}
		}
		request.getUser().getRequest().remove(request);
		request.getUser().save();
		request.delete();
	}

	/**
	 * Lance le matching pour la requete et renvoie
	 * une liste de trajets
	 */
	public List<Traject> findTrajects(Request request){
		return Matching.match(request);
	}

	/**
	 * Lance un timer pour faire un matching plus tard.
	 */
	public void matchLater(Request request){
		this.recordRequest(request);
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
			if(stop || request.getArrivalTime().after(new Date())) return;
			ArrayList<Traject> ps = Matching.match(request);
			if( ps == null || ps.size() == 0){
				//restart
				long timeOut = (request.getArrivalTime().getTime() - new Date().getTime() > timeout24) ? timeout24 : timeout2;
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

