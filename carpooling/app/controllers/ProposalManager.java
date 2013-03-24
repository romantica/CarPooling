package controllers;


import java.util.*;

import java.sql.*;
import java.util.Date;

import models.objects.*;
import controllers.interfaces.ICommunication;
import controllers.TrajectManager;
import play.db.*;

/**
 * User: gbriot
 * Date: 13/03/13
 */
public class ProposalManager implements controllers.interfaces.IProposalManager{

	@Override
	public List<PickupPoint> getPickupPoints(Coordinate start, Coordinate end) {
	    List<PickupPoint> search = PickupPoint.findAll();
	    List<PickupPoint> result = new ArrayList<PickupPoint>();
//	    System.out.println(start + " et " + end);
//	    System.out.println("Size of list: "+search.size());
	    double c = distance(start, end);
	    double a = 1.3*c; // valeur prise "au hazard" => definit la forme de l'ellipse
		for(int i = 0; i < search.size(); i++){
			Coordinate x = new Coordinate(search.get(i).getCoordinateX(), search.get(i).getCoordinateY());
			// verifier de ne pas ajouter le pp correspondant a start et end
			if((distance(x, start) + distance(x, end)) <= (2*a) && start.getX() != x.getX() && start.getY() != x.getY()){
				result.add(search.get(i));
			}
		}
		
        return result;
	}
	
	private double distance(Coordinate x1, Coordinate x2) {
//		System.out.println(x1 + " et " + x2);
		return Math.sqrt(Math.pow(x1.getX() - x2.getX(), 2) + Math.pow(x1.getY() - x2.getY(), 2));
	}
	
	@Override
	public void recordProposal(Proposal proposal) {
		// Attention, les PP qui sont déjà en DB ne doivent pas être ajouté (ils n'ont pas d'id), les autres oui !
		List<Itinerary> listIti = proposal.getItinerary();
		for(int i = 0; i < listIti.size(); i++){
			PickupPoint.create(listIti.get(i).getPickupPoint());
		}
		Proposal.create(proposal);
		
//		Connection conn = DB.getConnection();
//		try {
//			Statement stmt = conn.createStatement();
//			int result = stmt.executeUpdate("INSERT INTO proposals (kmcost, availableSeats, car, user, traject, itinerary) VALUES ('" + proposal.getKmCost() + "', '" + proposal.getAvailableSeats() + "','" + proposal.getCar() + "','" + proposal.getUser() + "','" + proposal.getTraject() + "','" + proposal.getItinerary() + "')");
//			// TODO ? ajout de la proposal à user ou fait implicitement ?
//			conn.close();
//		} catch (SQLException e) {
//			// TODO
//		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Proposal> getProposalList(User user) {
        /* TODO: NE FONCTIONNE PAS
          Il faut avoir tt les information de toutes les proposals de l'utilisateur
          Donc il faut qu'il y ai les objects, Car, User, Initenray, PickupPoint, Trajects pour chaque proposal
         */
        /*
		Connection conn = DB.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT proposals FROM User WHERE Login='" + user.getLogin() + "'");
            if (!rs.first()) return null; // TODO check if null is OK or create an empty List<Proposal> ?
            conn.close();
            return (List<Proposal>) rs.getObject("proposals");
        } catch (SQLException e) {
            return null;
        } catch (ClassCastException e) {
        	return null;
        }*/

        List<Proposal> l = new ArrayList<Proposal>();
        User u = UserManager.getUserLogged();
        Car c = new Car();
        Proposal p = new Proposal(1,1.0F,2,c,u);
        p.addItinerary(new Itinerary(
                new Date(),
                new Date(),
                new PickupPoint(
                        "name",
                        "desc",
                        "Address",
                        new Coordinate(50.715897,4.7128073)
                )
        ));
        p.addItinerary(new Itinerary(
                new Date(),
                new Date(),
                new PickupPoint(
                        "name2",
                        "desc2",
                        "Address2",
                        new Coordinate(50.725897,4.7228073)
                )
        ));
        l.add(p);
        Proposal p2 = new Proposal(2,3.0F,4,c,u);
        p2.addItinerary(new Itinerary(
                new Date(),
                new Date(),
                new PickupPoint(
                        "name",
                        "desc",
                        "Address",
                        new Coordinate(50.715897,4.7128073)
                )
        ));
        p2.addItinerary(new Itinerary(
                new Date(),
                new Date(),
                new PickupPoint(
                        "name2",
                        "desc2",
                        "Address2",
                        new Coordinate(50.725897,4.7228073)
                )
        ));
        p2.addItinerary(new Itinerary(
                new Date(),
                new Date(),
                new PickupPoint(
                        "name",
                        "desc",
                        "Address",
                        new Coordinate(50.715897,4.7128073)
                )
        ));
        p2.addItinerary(new Itinerary(
                new Date(),
                new Date(),
                new PickupPoint(
                        "name2",
                        "desc2",
                        "Address2",
                        new Coordinate(50.725897,4.7228073)
                )
        ));
        l.add(p2);
        Proposal p3 = new Proposal(3,4.0F,5,c,u);
        p3.addItinerary(new Itinerary(
                new Date(),
                new Date(),
                new PickupPoint(
                        "name",
                        "desc",
                        "Address",
                        new Coordinate(50.715897,4.7128073)
                )
        ));
        p3.addItinerary(new Itinerary(
                new Date(),
                new Date(),
                new PickupPoint(
                        "name2",
                        "desc2",
                        "Address2",
                        new Coordinate(50.725897,4.7228073)
                )
        ));
        l.add(p3);
        return l;
	}

	@Override
	public void modifyProposal(Proposal oldProposal, Proposal newProposal) {
//		Connection conn = DB.getConnection();
//		try {
//			Statement stmt = conn.createStatement();
//			int result = stmt.executeUpdate("DELETE FROM proposals WHERE user = '" + oldProposal.getUser() + "' AND ");
//			conn.close();
//		} catch (SQLException e) {
//			// TODO
//		}
//		recordProposal(newProposal);
		
		deleteProposal(oldProposal);
		recordProposal(newProposal);
	}
	
	@Override
	public void deleteProposal(Proposal prop) {
		// utilise communication pour prevenir les users.
		// appele cancelTraject pour TOUS les trajets liés
		List<Traject> traj = Traject.find.where().eq("proposal", prop).findList();
		for(int i = 0; i < traj.size(); i++){
			//ICommunication.ProposalCancelled(traj.get(i).getUser(), traj.get(i));
			TrajectManager.cancelTraject(traj.get(i));
		}
		// supprimer oldProposal
		Proposal.delete(prop);
	}

}
