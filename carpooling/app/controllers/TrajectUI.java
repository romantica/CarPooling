package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.traject.*;

public class TrajectUI  extends Controller {

    public static Result main(){
        Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
        return redirect("/traject/driver");
    }

    public static Result passanger(){
        Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
        return ok(list.render(sess.getUsername(),null));
    }
}
