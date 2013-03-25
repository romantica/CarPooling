package controllers;

import controllers.interfaces.IUserManager;
import models.objects.Car;
import models.objects.User;
import play.db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static play.mvc.Controller.session;

public class UserManager implements IUserManager {

    public User createUser(String login, String lastName, String firstName, String accountNumber, String phone, String email) {
        //TODO
        return null;
    }

    // TODO: A ajouter dans l'interface
    // TODO: getters: login, lastName, firstName, accountNumber, phone, email
    // TODO: setters: lastName, firstName, accountNumber, phone, email

    public User getUser(String login) {
        //TODO
        return null;
    }

    public void addCar(Car car) {
        //TODO
    }

    public void removeCar(Car car) {
        //TODO
    }

    public static User getUserLogged() {
        User user = User.find.where().like("login", session("username")).findUnique();
        return user;
        /*
        //TODO: autre acces a la bd ?
        //TODO: completer le profile.
        Connection conn = DB.getConnection();
        try {
            Statement stmt = conn.createStatement();
            String username =  session("username");
            if (username == null) return null;
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE Login='" + username + "'");
            if (!rs.first()) return null;
            User user = new User();
            user.setLogin(rs.getString("Login"));
            return user;
        } catch (SQLException e) {
            return null;
        }
        */
    }
}
