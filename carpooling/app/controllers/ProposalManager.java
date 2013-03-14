package controllers;


import java.util.List;

import java.sql.*;
import javax.sql.*;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recordProposal(Proposal proposal) {
		// TODO Soit via SQL comme getProposalList
		// Soit via ebean et faire une méthode addDB dans Proposal qui appelera save de Model!
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Proposal> getProposalList(User user) {
		Connection conn = DB.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT proposals FROM User WHERE Login='" + user.getLogin() + "'");
            if (!rs.first()) return null; // TODO check if null is OK or create an empty List<Proposal> ?
            return (List<Proposal>) rs.getObject("proposals");
        } catch (SQLException e) {
            return null;
        } catch (ClassCastException e) {
        	return null;
        }
	}

	@Override
	public void modifyProposal(Proposal oldProposal, Proposal newProposal) {
		// TODO Auto-generated method stub
		
	}

}
