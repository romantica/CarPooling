package controllers;

import java.util.ArrayList;

import models.objects.Request;
import models.objects.Traject;
import models.objects.Proposal;

public class Matching
{
	/**
	 * Sur base d'une requete et en accedant a la base de donnees, 
	 * renvoie une liste de trajets qui seront propose au 
	 * passager pour qu'il fasse son choix.
	 * Les trajets sont tries sur un critere de temps.
	 */
	public static ArrayList<Traject> match(Request request)
	{
		ArrayList<Proposal> proposals = getProposals();
		ArrayList<Traject> result = new ArrayList<Traject>();
		
		for (Proposal proposal : proposals)
		{
			if (isAMatch(proposal, request))
			{
				result.add(createTraject(proposal, request));
			}	
		}
		
		return sortByTime(result);
	}
	
	/**
	 * Renvoie la liste des toutes les proposals contenues dans la base de donnees
	 */
	private static ArrayList<Proposal> getProposals()
	{
		//TODO
		return null;
	}
	
	/**
	 * Determine si une proposal est compatible avec une requete
	 * et donc si un trajet peut etre cree a partir de celles-ci
	 */
	private static boolean isAMatch(Proposal proposal, Request request)
	{
		//TODO
		if (proposal.getAvailableSeats() <= request.getNecessarySeats()
				|| proposal.getKmCost() >= request.getTolerancePrice())
			return false;
		
		return true;
	}
	
	/**
	 * Cree un trajet a partir d'une proposal correspondant a
	 * une requete
	 */
	private static Traject createTraject(Proposal proposal, Request request)
	{
		//TODO
		return null;
	}
	
	/**
	 * Trie une liste de trajets selon le critère de temps
	 * (le plus proche dans le temps en premier)
	 */
	public static ArrayList<Traject> sortByTime(ArrayList<Traject> list)
	{
		//TODO
		return null;
	}
	
	/**
	 * Trie une liste de trajets selon le critère de distance de marche
	 * (la plus courte distance en premier)
	 */
	public static ArrayList<Traject> sortByDistance(ArrayList<Traject> list)
	{
		//TODO
		return null;
	}
	
	/**
	 * Trie une liste de trajets selon le critère de prix
	 * (le moins cher en premier)
	 */
	public static ArrayList<Traject> sortByPrice(ArrayList<Traject> list)
	{
		//TODO
		return null;
	}
	
	/**
	 * Fait appel a Google Maps et renvoie la distance a pied entre deux points
	 */
	 public static int gmDistanceWalk(double latitudeStart, double longitudeStart,
	 					double latitudeEnd, double longitudeEnd)
	 {
	 	//TODO
	 	return 0;
	 }
	 
	 /**
	 * Fait appel a Google Maps et renvoie la distance en voiture entre deux points 
	 */
	 public static int gmDistanceCar(double latitudeStart, double longitudeStart,
	 					double latitudeEnd, double longitudeEnd)
	 {
	 	//TODO
	 	return 0;
	 }
}
