package controllers;

import play.db.DB;

import java.sql.*;

import static play.mvc.Controller.session;

/**
 * User: sdefauw
 * Date: 12/03/13
 */
public class Login {

    String username;
    String pwd;

    public Login() {
        this.username = null;
        this.pwd = null;
    }

    public Login(String username, String password) {
        this.username = username;
        this.pwd = password;
    }
    public String getUsername() {
        return username;
    }

    public boolean isValid() {
        if (this.username == null || this.pwd == null || this.username == "" || this.pwd == "") return false;
        Connection conn = DB.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Password FROM User WHERE Login='" + this.username + "'");
            if (!rs.first()) return false;
            return rs.getString("Password").equals(this.pwd);
        } catch (SQLException e) {
            return false;
        }
    }

    public void logout(){
        session().remove("username");
        session().remove("password");
    }

    public void createSession() {
        if (!isValid()) return;
        session("username", this.username);
        session("pwd", this.pwd);
    }

    public boolean isLogged() {
        this.username = session("username");
        this.pwd = session("pwd");
        if (this.username == null || this.pwd == null) return false;
        return isValid();
    }
}
