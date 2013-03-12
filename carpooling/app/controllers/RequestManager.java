package controllers;

import java.util.List;
import java.util.Timer;

import models.objects.Request;
import models.objects.Traject;

public class RequestManager{

	/**
	 * Enregistre la requete dans la base de donnees
	 */
	public void recordRequest(Request request){

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
            MatchLaterHandler mlh = new MatchLaterHandler(request);
            mlh.execute();
        }

        public class MatchLaterHandler extends Handler{

            private Request request;

            public MatchLaterHandler(Request request){
                this.request = request;
            }

           public void execute(){
                if( Matching.match(request) == null){
                    //restart
                    int timeOut = (request.getArrivalTime() - Date.getTime() > 24*60*60) ? 24*60*60 : 2*60*60;
                    Timer timer = new Timer();
                    timer.WakeInTime(timeOut, this);
                } 
                else{
                    //Communicate
                    Communication.TrajectFound(request.getUser(), null);
                }
           }
        }
}

