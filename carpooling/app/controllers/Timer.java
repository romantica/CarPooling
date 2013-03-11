public interface Timer extends java.util.Timer{

	/**
	 * Appel la methode execute de l'object handler au 
	 * moment defini par l'object date.
	 */
	public void WakeAtDate(Date moment, Handler handler);

	
	/**
	 * Appel la methode execute de l'object handler au 
	 * moment ou le compte a rebour (timout) arrive a echeance.
	 */
	public void WakeInTime(Date timeout, Handler handler);

}