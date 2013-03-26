package controllers;


import java.util.ArrayList;
import java.util.List;

import models.objects.Car;
import models.objects.Coordinate;
import models.objects.Itinerary;
import models.objects.PickupPoint;
import models.objects.Proposal;
import models.objects.Request;
import models.objects.Traject;
import models.objects.User;
import controllers.interfaces.ICommunication;
import controllers.TrajectManager;

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
		for(Itinerary i: listIti){
			PickupPoint pp = i.getPickupPoint();
			PickupPoint realPP = PickupPoint.find.where().like("address", pp.getAddress()).findUnique(); 
			if(realPP == null) PickupPoint.create(i.getPickupPoint());
			else i.setPickupPoint(realPP);
			Itinerary.create(i);
		}
		proposal.getUser().addProposal(proposal);
		Proposal.create(proposal);
		proposal.getUser().save();
		
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

	@Override
	public List<Proposal> getProposalList(User user) {
		List<Proposal> result = Proposal.find.where().eq("user_id", user.getId()).findList();
		int i = 0;
		for(Proposal j: result){
			j.setCar(Car.find.where().eq("plate_number", result.get(i).getCar().getPlateNumber()).findUnique());
			j.setUser(user);
			result.set(i, j);
			i++;
		}
		return result;
		
		
//		Connection conn = DB.getConnection();
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT proposals FROM User WHERE Login='" + user.getLogin() + "'");
//            if (!rs.first()) return null; // TODO check if null is OK or create an empty List<Proposal> ?
//            conn.close();
//            return (List<Proposal>) rs.getObject("proposals");
//        } catch (SQLException e) {
//            return null;
//        } catch (ClassCastException e) {
//        	return null;
//        }
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
		List<Traject> traj = null;
		try{
			traj = Traject.find.where().eq("proposal", prop).findList();
		} catch(NullPointerException e) {}
		if(traj != null) {
			for(int i = 0; i < traj.size(); i++){
				ICommunication.proposalCancelled(traj.get(i).getUser(), traj.get(i));
				TrajectManager.cancelTraject(traj.get(i));
			}
		}
		// supprimer oldProposal
		Proposal.delete(prop);
	}

}
