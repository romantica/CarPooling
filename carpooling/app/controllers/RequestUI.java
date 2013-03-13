package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;

/**
 * User: olivierdeh
 * Date: 13/03/13
 */
public class RequestUI extends Controller {
	public static Result create() {
		Login sess = new Login();
		// if (!session.isLogged())
           // return redirect("/");
		return ok(requestcreate.render(sess.getUsername(), null));
	}
}