package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        Login login = new Login();
        login.isLogged();
        return ok(index.render(login.getUsername()));
    }
  
}
