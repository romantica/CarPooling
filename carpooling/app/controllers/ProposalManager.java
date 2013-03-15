package controllers;


import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import models.objects.Coordinate;
import models.objects.PickupPoint;
import models.objects.Proposal;
import models.objects.User;
import play.db.*;

/**
 * User: gbriot
 * Date: 13/03/13
 */
public class ProposalManager implements controllers.interfaces.IProposalManager{

	@Override
	public List<PickupPoint> getPickupPoints(Coordinate start, Coordinate end) {
        // TEST
        List<PickupPoint> list= new ArrayList<PickupPoint>();
        list.add(
                new PickupPoint(
                        1,
                        "namefrom",
                        "descfrom",
                        "Addressfrom",
                        start
                ));
        list.add(
                new PickupPoint(
                        2,
                        "nameto",
                        "descto",
                        "Addressto",
                        end
                ));
        list.add(
                new PickupPoint(
                        3,
                        "name",
                        "desc",
                        "Address",
                        new Coordinate(50.715897,4.7128073)
                ));
        list.add(
                new PickupPoint(
                        4,
                        "name2",
                        "desc2",
                        "Address2",
                        new Coordinate(50.717897,4.6138073)
                ));
        return list;
	}
	
	@Override
	public void recordProposal(Proposal proposal) {
		// TODO Soit via SQL comme getProposalList => executeUpdate !!!
		// Soit via ebean et faire une méthode addDB dans Proposal qui appelera save de Model!
		Connection conn = DB.getConnection();
		try {
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate("INSERT INTO proposals (kmcost, availableSeats, car, user, traject, itinerary) VALUES ('" + proposal.getKmCost() + "', '" + proposal.getAvailableSeats() + "','" + proposal.getCar() + "','" + proposal.getUser() + "','" + proposal.getTraject() + "','" + proposal.getItinerary() + "')");
			// TODO ? ajout de la proposal à user ou fait implicitement ?
			conn.close();
		} catch (SQLException e) {
			// TODO
		}
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
		Connection conn = DB.getConnection();
		try {
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate("DELETE FROM proposals WHERE user = '" + oldProposal.getUser() + "' AND ");
			conn.close();
		} catch (SQLException e) {
			// TODO
		}
		recordProposal(newProposal);
		// TODO utiliser communication pour prévenir les users etc !!
		// TODO appeler cancelTraject pour TOUS les trajets liés
	}

}
