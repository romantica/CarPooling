package controllers;

import java.util.List;

import javax.sql.DataSource;

import models.objects.Coordinate;
import models.objects.PickupPoint;
import models.objects.Proposal;
import models.objects.User;
import play.db.*;

public class ProposalManager implements controllers.interfaces.IProposalManager{

	@Override
	public List<PickupPoint> getPickupPoints(Coordinate start, Coordinate end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recordProposal(Proposal proposal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Proposal> getProposalList(User user) {
		DataSource ds = DB.getDataSource();
		return null;
	}

	@Override
	public void modifyProposal(Proposal oldProposal, Proposal newProposal) {
		// TODO Auto-generated method stub
		
	}

}
