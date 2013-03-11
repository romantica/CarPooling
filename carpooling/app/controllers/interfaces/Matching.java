package controllers.interfaces;

import java.util.List;

import models.objects.Request;
import models.objects.Traject;

public interface Matching{
	/**
	 * Sur base d'une requete et en accedant a la base de donnees, 
	 * renvoie une liste de trajets qui seront propose au 
	 * passager pour qu'il fasse son choix.
	 */
	public List<Traject> match(Request r);
}