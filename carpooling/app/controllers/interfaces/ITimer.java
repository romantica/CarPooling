package controllers.interfaces;

import java.util.Date;

public abstract class ITimer extends java.util.Timer{

	/**
	 * Appel la methode execute de l'object handler au 
	 * moment defini par l'object date.
	 */
	public abstract void WakeAtDate(Date moment, IHandler handler);

	
	/**
	 * Appel la methode execute de l'object handler au 
	 * moment ou le compte a rebour (timout) arrive a echeance.
	 */
	public abstract void WakeInTime(long timeout, IHandler handler);

}