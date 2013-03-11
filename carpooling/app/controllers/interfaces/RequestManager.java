package controllers.interfaces;

import java.util.List;

import models.objects.Request;
import models.objects.Traject;

public interface RequestManager{
	/**
	 * Enregistre la requete dans la base de donnees
	 */
	public void recordRequest(Request request);

	/**
	 * Lance le matching pour la requete et renvoie
	 * une liste de trajets
	 */
	public List<Traject> findTrajects(Request request);
	
	/**
	 * Lance un timer pour faire un matching plus tard.
	 */
	public void matchLater(Request request);
}
