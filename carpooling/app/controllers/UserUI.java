package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;

/**
 * User: sdefauw
 * Date: 12/03/13
 */
public class UserUI extends Controller {

    public static Result loginPage() {
        //already logged
        Login session = new Login();
        if (session.isLogged())
            return redirect("/");
        return ok(loginpage.render("", null));
    }

    public static Result login() {
        DynamicForm requestData = Form.form().bindFromRequest();
        //No session
        String username = requestData.get("username");
        String pwd = requestData.get("password");
        Login session = new Login(username, pwd);
        //Is good username and pwd -> index
        if (session.isValid()) {
            session.createSession();
            return redirect("/");
        }
        //Bad username or pwd
        return ok(loginpage.render(username, "Incorrect username or password."));

    }

    public static Result logout(){
        Login session = new Login();
        session.logout();
        return redirect("/");
    }
}
