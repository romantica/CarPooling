package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import play.db.ebean.Model;

import models.objects.*;

public class Matching
{
	/**
	 * Sur base d'une requete et en accedant a la base de donnees, renvoie une
	 * liste de trajets qui seront propose au passager pour qu'il fasse son
	 * choix. Les trajets sont tries sur un critere de temps.
	 */
	public static ArrayList<Traject> match(Request request)
	{
		if (request.getToleranceWalkDistance() >= distance(request.getDepartureCoordinates(), request.getArrivalCoordinates()))
			return null;

		ArrayList<Proposal> proposals = getProposals();
		ArrayList<Traject> result = new ArrayList<Traject>();

		for (Proposal proposal : proposals)
		{
			PickupPoint[] pickupPoints = null;
			if (isAMatch(proposal, request, pickupPoints))
			{
				result.add(createTraject(proposal, request, pickupPoints));
			}
		}

		return sortByTime(result);
	}

	/**
	 * Renvoie la liste des toutes les proposals contenues dans la base de
	 * donnees
	 */
	private static ArrayList<Proposal> getProposals()
	{
		List<Proposal> proposals = new Model.Finder<Integer,Proposal>(Integer.class,Proposal.class).all();
		return  new ArrayList<Proposal>(proposals);
	}

	/**
	 * Determine si une proposal est compatible avec une requete et donc si un
	 * trajet peut etre cree a partir de celles-ci
	 */
	private static boolean isAMatch(Proposal proposal, Request request, PickupPoint[] pickupPoints)
	{
		if ( !isSeatsRequestAMatch(proposal, request) || !isArrivalTimeAMatch(proposal, request) )
			return false;
		else
		{
			pickupPoints = getPickupPoints(proposal, request);
			if ( !isTimingAMatch(proposal, request, pickupPoints) || !isPriceAMatch(proposal, request, pickupPoints))
				return false;
			else 
				return true;
		}
	}

	/**
	 * Determine si l'heure d'arrivee (+/- la tolerance de temps) d'une requete
	 * est comprise dans la "fenetre" temporelle d'une offre
	 */
	private static boolean isArrivalTimeAMatch(Proposal proposal, Request request)
	{
		if (proposal.getItinerary().getFirst().getDepartureTime().after(getDatePlusTolerance(request.getArrivalTime(), request.getToleranceTime())) || proposal.getItinerary().getLast().getArrivalTime().before(getDateMinusTolerance(request.getArrivalTime(), request.getToleranceTime())))
			return false;
		else
			return true;
	}

	/**
	 * Determine si un prix est inferieur a la tolerance de la requete
	 */
	private static boolean isPriceAMatch(Proposal proposal, Request request, PickupPoint[] pickupPoints)
	{
		return request.getTolerancePrice() >= proposal.getKmCost() * getTrajectLength(proposal.getItinerary(), pickupPoints[0], pickupPoints[1]);
	}

	/**
	 * Determine si le nombre de sieges demandes dans une requete est disponible
	 * dans une offre
	 */
	private static boolean isSeatsRequestAMatch(Proposal proposal, Request request)
	{
		return proposal.getAvailableSeats() >= request.getNecessarySeats();
	}

	/**
	 * Renvoie un objet Date incremente d'un certain temps
	 */
	private static Date getDatePlusTolerance(Date date, int timeTolerance)
	{
		date.setTime(date.getTime() + timeTolerance);
		return date;
	}

	/**
	 * Renvoie un objet Date decremente d'un certain temps
	 */
	private static Date getDateMinusTolerance(Date date, int timeTolerance)
	{
		date.setTime(date.getTime() - timeTolerance);
		return date;
	}
	
	/**
	 * Determine si l'arrivee reelle se trouve dans la tolerance demandee
	 */
	private static boolean isTimingAMatch(Proposal proposal, Request request, PickupPoint[] pickupPoints)
	{
		Date realArrival = proposal.getItinerary(pickupPoints[1]).getArrivalTime();
		realArrival.setTime(realArrival.getTime() + distance(pickupPoints[1].getCoordinates(), request.getArrivalCoordinates()) * 1000);
		
		if (getDatePlusTolerance(request.getArrivalTime(), request.getToleranceTime()).before(realArrival)
				|| getDateMinusTolerance(request.getArrivalTime(), request.getToleranceTime()).after(realArrival))		
			return false;
		else
			return true;
	}

	/**
	 * Cree un trajet a partir d'une proposal correspondant a une requete
	 */
	private static Traject createTraject(Proposal proposal, Request request, PickupPoint[] pickupPoints)
	{
		return new Traject(request.getNecessarySeats(),
									getTrajectLength(proposal.getItinerary(), pickupPoints[0], pickupPoints[1]) * proposal.getKmCost() / (float)1000.0,
									request,
									request.getUser(),
									new Composition(true, proposal.getItinerary(pickupPoints[0]).getDepartureTime(), pickupPoints[0]),
									new Composition(false, proposal.getItinerary(pickupPoints[1]).getArrivalTime(), pickupPoints[1]),
									proposal);
	}

	/**
	 * Trie une liste de trajets selon le critère de temps (le plus proche dans
	 * le temps en premier)
	 */
	public static ArrayList<Traject> sortByTime(ArrayList<Traject> list)
	{
		ArrayList<Traject> result = new ArrayList<Traject>();
		Traject temp = list.get(0);
		
		while (!list.isEmpty())
		{
			for (Traject traject : list)
			{
				if (traject.getDeparturePP().getTime().before(temp.getDeparturePP().getTime()))
				{
					temp = traject;
				}
			}
			result.add(temp);
			list.remove(temp);
		}
		
		return result;
	}

	/**
	 * Trie une liste de trajets selon le critère de distance de marche (la plus
	 * courte distance en premier)
	 */
	public static ArrayList<Traject> sortByDistance(ArrayList<Traject> list)
	{
		// TODO
		return null;
	}

	/**
	 * Trie une liste de trajets selon le critère de prix (le moins cher en
	 * premier)
	 */
	public static ArrayList<Traject> sortByPrice(ArrayList<Traject> list)
	{
		// TODO
		return null;
	}

	/**
	 * Fait appel a Google Maps et renvoie la distance en metres a pied entre
	 * deux points
	 */
	private static int distance(Coordinate start, Coordinate end)
	{
		// conversion en radian
		double lat1 = Math.toRadians(start.getX());
		double long1 = Math.toRadians(start.getY());
		double lat2 = Math.toRadians(end.getX());
		double long2 = Math.toRadians(end.getY());
		int R = 6371000; // rayon de la terre en metre

		return (int) (Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(long1 - long2)) * R);
	}

	/**
	 * Renvoie la longueur d'un trajet en metres en fonction d'un itineraire
	 */
	private static int getTrajectLength(LinkedList<Itinerary> itinerary, PickupPoint start, PickupPoint end)
	{
		int length = 0;
		PickupPoint p1 = null;
		PickupPoint p2 = null;
		ListIterator<Itinerary> iterator = itinerary.listIterator();

		while (iterator.hasNext() && !p1.equals(start))
		{
			p1 = iterator.next().getPickupPoint();
		}

		while (iterator.hasNext() && !p2.equals(end))
		{
			p2 = iterator.next().getPickupPoint();
			length += distance(p1.getCoordinates(), p2.getCoordinates());
			p1 = p2;
		}

		return length;
	}

	/**
	 * Renvoie les deux PickupPoint de depart et d'arrivee pour un trajet, null
	 * si il n'en existe pas dans les criteres de recherche
	 */
	private static PickupPoint[] getPickupPoints(Proposal proposal, Request request)
	{
		PickupPoint[] result = new PickupPoint[2];
		result[0] = null;
		result[1] = null;
		LinkedList<PickupPoint> startList = new LinkedList<PickupPoint>();
		ArrayList<Integer> startDistance = new ArrayList<Integer>();
		LinkedList<PickupPoint> endList = new LinkedList<PickupPoint>();
		ArrayList<Integer> endDistance = new ArrayList<Integer>();
		int distance;

		for (Itinerary itinerary : proposal.getItinerary())
		{
			distance = distance(itinerary.getPickupPoint().getCoordinates(), request.getDepartureCoordinates());
			if (distance <= request.getToleranceWalkDistance())
			{
				startList.add(itinerary.getPickupPoint());
				startDistance.add(distance);
			}
			distance = distance(itinerary.getPickupPoint().getCoordinates(), request.getArrivalCoordinates());
			if (distance <= request.getToleranceWalkDistance())
			{
				endList.add(itinerary.getPickupPoint());
				startDistance.add(distance);
			}
		}

		if (startList.isEmpty() || endList.isEmpty())
			return null;
		else
		{
			ListIterator<PickupPoint> startIterator = startList.listIterator(startList.size());
			ListIterator<PickupPoint> endIterator = endList.listIterator();
			PickupPoint start;
			PickupPoint end;

			while (startIterator.hasPrevious())
			{
				start = startIterator.previous();
				endIterator = endList.listIterator();
				while (endIterator.hasNext())
				{
					end = endIterator.next();
					distance = getTrajectLength(proposal.getItinerary(), start, end);
					if (startDistance.get(startList.lastIndexOf(start)) + endDistance.get(endList.indexOf(end)) <= request.getToleranceWalkDistance())
					{
						if (result[0] == null || distance < getTrajectLength(proposal.getItinerary(), result[0], result[1]))
						{
							result[0] = start;
							result[1] = end;
						}
						break;
					}
				}
			}
		}

		if (result[0] == null)
			return null;
		else
			return result;
	}
}
