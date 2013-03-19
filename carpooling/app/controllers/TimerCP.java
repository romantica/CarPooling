package controllers;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import controllers.interfaces.IHandler;
import controllers.interfaces.ITimer;

public class TimerCP extends ITimer{
	
	/**
	 * Appel la methode execute de l'object handler au 
	 * moment defini par l'object date.
	 */
	public void WakeAtDate(Date moment, final IHandler handler){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run(){
				handler.execute();
			}
		}, moment);
	}

	
	/**
	 * Appel la methode execute de l'object handler au 
	 * moment ou le compte a rebour (timout) arrive a echeance.
	 */
	public void WakeInTime(long timeout, final IHandler handler){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run(){
				handler.execute();
			}
		}, timeout);
	}

}
