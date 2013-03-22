package controllers;

import play.db.DB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    /**
     * Encrypt a item in MD5
     *
     * @param elem item to encrypt
     * @return item encrypted
     */
    private static String encoderMD5(String elem) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] elemlocked = digest.digest(elem.getBytes());

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < elemlocked.length; i++) {
            String hex = Integer.toHexString(elemlocked[i]);
            if (hex.length() == 1) {
                code.append('0');
                code.append(hex.charAt(hex.length() - 1));
            } else
                code.append(hex.substring(hex.length() - 2));
        }

        return code.toString();
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
            return rs.getString("Password").equals(encoderMD5(this.pwd));
        } catch (SQLException e) {
            return false;
        }
    }

    public void logout() {
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
