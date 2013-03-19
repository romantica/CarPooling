package controllers;


import java.util.ArrayList;
import java.util.List;

import java.sql.*;

import models.objects.Coordinate;
import models.objects.PickupPoint;
import models.objects.Proposal;
import models.objects.Traject;
import models.objects.User;
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
	    System.out.println(start + " et " + end);
	    System.out.println("Size of list: "+search.size());
	    double c = distance(start, end);
	    double a = 1.3*c; // valeur prise "au hazard" => definit la forme de l'ellipse
		for(int i = 0; i < search.size(); i++){
			Coordinate x1 = search.get(i).getCoordinates();
			Coordinate x2 = search.get(i).getCoordinates();
			if((distance(x1, start) + distance(x2, end)) <= (2*a)){
				result.add(search.get(i));
			}
		}
		
        return result;
	}
	
	private double distance(Coordinate x1, Coordinate x2) {
		System.out.println(x1 + " et " + x2);
		return Math.sqrt(Math.pow(x1.getX() - x2.getX(), 2) + Math.pow(x1.getY() - x2.getY(), 2));
	}
	
	@Override
	public void recordProposal(Proposal proposal) {
		// Attention, les PP qui sont déjà en DB ne doivent pas être ajouté (ils n'ont pas d'id), les autres oui !
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
        }
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
		
		// utilise communication pour prevenir les users.
		// appele cancelTraject pour TOUS les trajets liés
		List<Traject> traj = Traject.find.where().eq("proposal", oldProposal).findList();
		for(int i = 0; i < traj.size(); i++){
			ICommunication.ProposalCancelled(traj.get(i).getUser(), traj.get(i));
			TrajectManager.cancelTraject(traj.get(i));
		}
		// supprimer oldProposal
		Proposal.delete(oldProposal);
		recordProposal(newProposal);
	}

}
