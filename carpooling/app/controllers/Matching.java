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
	 * choix. Les trajets sont tries selon un critere de temps.
	 * 
	 * @param requete : une requete pour laquelle on cherche des Traject possibles
	 * @return une arrayList de Traject triee selon le temps de depart,
	 * 			null si aucun Traject n'est trouve
	 */
	public static ArrayList<Traject> match(Request request)
	{
		if (request.getToleranceWalkDistance() >= distance(request.getDepartureCoordinates(), request.getArrivalCoordinates()))
			return null; //Renvoie null si la tolerance de marche est plus grande que la distance a couvrir

		ArrayList<Proposal> proposals = getProposals();
		ArrayList<Traject> result = new ArrayList<Traject>();

		for (Proposal proposal : proposals)
		{
			System.out.println("Proposal itinerary: " + proposal.getItinerary());
			if ( isSeatsRequestAMatch(proposal, request) && isArrivalTimeAMatch(proposal, request) )
			{
				System.out.println("111");
				PickupPoint[] pickupPoints = getPickupPoints(proposal, request);
				System.out.println("PUP : " + pickupPoints[0]);
				if (pickupPoints != null 
						&& isTimingAMatch(proposal, request, pickupPoints) 
						&& isPriceAMatch(proposal, request, pickupPoints))
				{
					System.out.println("OK");
					result.add(createTraject(proposal, request, pickupPoints));
				}
			}
		}
		
		if (result.isEmpty())
			return null;
		else
			return sortByTime(result);
	}

	/**
	 * Renvoie la liste des toutes les proposals contenues dans la base de
	 * donnees
	 * 
	 * @return un arrayList des Proposals contenues dans la DB
	 */
	public static ArrayList<Proposal> getProposals()
	{
		List<Proposal> proposals = new Model.Finder<Integer,Proposal>(Integer.class,Proposal.class).all();
		return new ArrayList<Proposal>(proposals);
	}

	/**
	 * Determine si l'heure d'arrivee (+/- la tolerance de temps) d'une requete
	 * est comprise dans la "fenetre" temporelle d'une offre
	 * 
	 * @param proposal : la Proposal que l'on evalue
	 * @param request : la Request qui donne les criteres
	 * @return un boolean qui determine si l'arrivee est dans la fenetre
	 */
	public static boolean isArrivalTimeAMatch(Proposal proposal, Request request)
	{
		return !(proposal.getItinerary().getFirst().getDepartureTime().after(getDateAndTolerance(request.getArrivalTime(), request.getToleranceTime(), true))
				|| proposal.getItinerary().getLast().getArrivalTime().before(getDateAndTolerance(request.getArrivalTime(), request.getToleranceTime(), false)));
	}

	/**
	 * Determine si un prix est inferieur a la tolerance de la requete
	 * 
	 * @param proposal : la Proposal que l'on evalue
	 * @param request : la Request qui donne les criteres
	 * @param pickupPoints : les PickupPoints de depart et d'arrivee
	 * @return un booleen qui determine si le prix d'un eventuel Traject est dans la tolerance
	 */
	public static boolean isPriceAMatch(Proposal proposal, Request request, PickupPoint[] pickupPoints)
	{
		return request.getTolerancePrice() >= proposal.getKmCost() * 0.001 * getTrajectLength(proposal.getItinerary(), pickupPoints[0], pickupPoints[1]);
	}

	/**
	 * Determine si le nombre de sieges demandes dans une requete est disponible
	 * dans une offre
	 * 
	 * @param proposal : la Proposal que l'on evalue
	 * @param request : la Request qui donne les criteres
	 * @return un boolean qui determine si le nombre de sieges requis est disponible
	 */
	public static boolean isSeatsRequestAMatch(Proposal proposal, Request request)
	{
		return proposal.getAvailableSeats() >= request.getNecessarySeats();
	}

	/**
	 * Renvoie un objet Date incremente ou decremente d'un certain temps
	 * 
	 * @param date : une Date a modifier
	 * @param timeTolerance : la tolerance de temps en millisecondes
	 * @param plusMinus : ajouter ou retirer la tolerance
	 * @return une Date modifiee
	 */
	public static Date getDateAndTolerance(Date date, long timeTolerance, boolean plusMinus)
	{
		Date result;
		
		if (plusMinus)
			result = new Date(date.getTime() + timeTolerance);
		else
			result = new Date(date.getTime() - timeTolerance);
		return result;
	}
	
	/**
	 * Determine si le temps de l'arrivee reelle se trouve dans la tolerance demandee
	 * 
	 * @param proposal : la Proposal que l'on evalue
	 * @param request : la Request qui donne les criteres
	 * @param pickupPoints : les PickupPoints de depart et d'arrivee
	 * @return un boolean qui determine si l'heure d'arrivee est dans les criteres de la requete
	 */
	public static boolean isTimingAMatch(Proposal proposal, Request request, PickupPoint[] pickupPoints)
	{
		Date realArrival = new Date(proposal.getItinerary(pickupPoints[1]).getArrivalTime().getTime()
				+ (long) distance(pickupPoints[1].getCoordinates(), request.getArrivalCoordinates()) * 1000);
		
		return !(getDateAndTolerance(request.getArrivalTime(), request.getToleranceTime(), true).before(realArrival)
					|| getDateAndTolerance(request.getArrivalTime(), request.getToleranceTime(), false).after(realArrival));
	}

	/**
	 * Cree un trajet a partir d'une proposal correspondant a une requete
	 * 
	 * @param proposal : la Proposal que l'on evalue
	 * @param request : la Request qui donne les criteres
	 * @param pickupPoints : les PickupPoints de depart et d'arrivee (null au depart, calcules en cours de methode)
	 * @return un Traject cree a partir d'une proposal selon le Request et les PickupPoints donnes
	 */
	public static Traject createTraject(Proposal proposal, Request request, PickupPoint[] pickupPoints)
	{
		return new Traject(request.getNecessarySeats(),
									(float) (getTrajectLength(proposal.getItinerary(), pickupPoints[0], pickupPoints[1]) * proposal.getKmCost() / (float)1000.0),
									request,
									request.getUser(),
									new Composition(true, proposal.getItinerary(pickupPoints[0]).getDepartureTime(), pickupPoints[0]),
									new Composition(false, proposal.getItinerary(pickupPoints[1]).getArrivalTime(), pickupPoints[1]),
									proposal);
	}

	/**
	 * Trie une liste de trajets selon le critère de temps (le plus proche dans
	 * le temps en premier)
	 * 
	 * @param list : une arrayList de Traject a trier
	 * @return une arrayList de Trajects triee selon le temps
	 */
	public static ArrayList<Traject> sortByTime(ArrayList<Traject> list)
	{
		ArrayList<Traject> result = new ArrayList<Traject>();
		Traject temp;
		
		while (!list.isEmpty())
		{
			temp = list.get(0);
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
	 * Renvoie la distance en metres entre deux points
	 * 
	 * @param start, end : les coordonnees des deux points
	 * @return une distance en metres
	 */
	public static double distance(Coordinate start, Coordinate end)
	{
		// conversion en radian
		if (start.equals(end))
			return 0.0;
		
		double lat1 = Math.toRadians(start.getX());
		double long1 = Math.toRadians(start.getY());
		double lat2 = Math.toRadians(end.getX());
		double long2 = Math.toRadians(end.getY());
		double R = 6371000.0; // rayon de la terre en metre

		return (Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(long1 - long2)) * R);
	}

	/**
	 * Renvoie la longueur d'un trajet en metres en fonction d'un itineraire donne
	 * 
	 * @param itinerary : une linkedList representant un itineraire, contenant tous les points de passage
	 * @param start, end : les PickupPoints de depart et d'arrivee
	 * @return une distance en metres
	 */
	public static double getTrajectLength(LinkedList<Itinerary> itinerary, PickupPoint start, PickupPoint end)
	{
		double length = 0;
		
		PickupPoint p1 = itinerary.getFirst().getPickupPoint();
		PickupPoint p2 = null;
		ListIterator<Itinerary> iterator = itinerary.listIterator(0);

		while (iterator.hasNext() && !p1.equals(start))
		{
			if (p1.equals(end))
				return Double.POSITIVE_INFINITY;
			p1 = iterator.next().getPickupPoint();
		}
		
		p2 = p1;
		
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
	 * 
	 * @param proposal : la Proposal que l'on evalue
	 * @param request : la Request qui donne les criteres
	 * @return un array de 2 PickupPoints si ils existent, null sinon
	 */
	public static PickupPoint[] getPickupPoints(Proposal proposal, Request request)
	{
		PickupPoint[] result = new PickupPoint[2];
		result[0] = null;
		result[1] = null;
		
		LinkedList<PickupPoint> startList = new LinkedList<PickupPoint>();
		ArrayList<Double> startDistance = new ArrayList<Double>();
		LinkedList<PickupPoint> endList = new LinkedList<PickupPoint>();
		ArrayList<Double> endDistance = new ArrayList<Double>();
		
		double distance;

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
				endDistance.add(distance);
			}
		}

		if (startList.isEmpty() || endList.isEmpty())
			return null;
		else
		{
			ListIterator<PickupPoint> startIterator = startList.listIterator(startList.size());
			ListIterator<PickupPoint> endIterator;
			PickupPoint start;
			PickupPoint end;
			
			while (startIterator.hasPrevious())
			{
				start = startIterator.previous();
				endIterator = endList.listIterator(0);
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