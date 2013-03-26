package controllers.interfaces;

import java.util.List;

import models.objects.Request;
import models.objects.Traject;

public interface IRequestManager{
	/**
	 * Enregistre la requete dans la base de donnees
	 * @throws Exception 
	 */
	public void recordRequest(Request request) throws Exception;

	/**
	 * Lance le matching pour la requete et renvoie
	 * une liste de trajets
	 */
	public List<Traject> findTrajects(Request request);
	
	/**
	 * Lance un timer pour faire un matching plus tard.
	 * @throws Exception 
	 */
	public void matchLater(Request request) throws Exception;
}
