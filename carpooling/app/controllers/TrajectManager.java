package controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import play.db.DB;

import models.objects.Traject;
import models.objects.User;
import controllers.interfaces.ITrajectManager;

/**
 * User: gbriot
 * Date: 14/03/13
 */
public class TrajectManager extends ITrajectManager {

	public static void recordTraject(Traject traj, User user) {
		// TODO Soit via SQL comme executeUpdate !!!
		// Soit via ebean et faire une méthode addDB dans Traject qui appelera save de Model!
		Connection conn = DB.getConnection();
		try {
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate("INSERT INTO trajects (reservedSeats, totalCost, request, user, departurePP, ArrivalPP, proposal) VALUES ('" + traj.getReservedSeats() + "', '" + traj.getTotalCost() + "','" + traj.getRequest() + "','" + traj.getUser() + "','" + traj.getDeparturePP() + "','" + traj.getArrivalPP() + "','" + traj.getProposal() + "')");
			// TODO ajout du trajet à user ? ou pas besoin ?
			conn.close();
		} catch (SQLException e) {
			// TODO
		}
	}

	public static void cancelTraject(Traject traject) {
		Connection conn = DB.getConnection();
		try {
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate("DELETE FROM trajects WHERE user = '" + traject.getUser() + "'");
			conn.close();
		} catch (SQLException e) {
			// TODO
		}
		// TODO notifier le user avec communication
	}

	public static void cancelTraject(User driver, List<Traject> trajects) {
		for(int i = 0; i < trajects.size(); i++) {
			cancelTraject(trajects.get(i));
		}
		// TODO notifier les users fait par cancetTraject(traj) mais pourquoi on a Drive ?
	}

	public static void arrivalNotification(Traject traj, short rating) {
		// TODO Soit via SQL comme executeUpdate !!!
		// Soit via ebean et faire une méthode addDB dans Traject qui appelera save de Model!
		Connection conn = DB.getConnection();
		try {
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate("INSERT INTO assessments (rating) VALUES ('" + rating + "')");
			// TODO comment lier traj à rating
			conn.close();
		} catch (SQLException e) {
			// TODO
		}
	}
}
