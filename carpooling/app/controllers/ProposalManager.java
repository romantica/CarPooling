package controllers;

import java.util.ArrayList;
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
        // TEST
		List<PickupPoint> list= new ArrayList<PickupPoint>();
        list.add(
                new PickupPoint(
                        "namefrom",
                        "descfrom",
                        "Addressfrom",
                        start
                ));
        list.add(
                new PickupPoint(
                        "nameto",
                        "descto",
                        "Addressto",
                        end
                ));
        list.add(
            new PickupPoint(
                    "name",
                    "desc",
                    "Address",
                    new Coordinate(50.715897,4.7128073)
            ));
        list.add(
                new PickupPoint(
                        "name2",
                        "desc2",
                        "Address2",
                        new Coordinate(50.717897,4.6138073)
                ));
		return list;
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
