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
		FormUI form = new FormUI("requestselecttraject");
		form.addField(new Field("address", "From", "fromadd", true, "Incorrect from address", null));
		form.addField(new Field("hidden", "", "fromcoord", false, "incorrect from address", null));
		form.addField(new Field("address", "To", "toadd", true, "Incorrect to address", null));
		form.addField(new Field("hidden", "", "tocoord", false, "Incorrect to address", null));
		form.addField(new Field("datetime-local", "Wished arrival hour", "arrivalTime", true, "Incorrect arrival time", null));
		Field seatsField = new Field("number", "Number of requested seats", "seats", true, "Incorrect required seats field", null);
		seatsField.value = "1";
		form.addField(seatsField);
		form.addField(new Field("number", "Tolerance time (min)", "toleranceTime", false, "Invalid tolerance time", null));
		form.addField(new Field("number", "Maximum walking distance (km)", "maxWalkingDistance", false, "Invalid walking distance", null));
		form.addField(new Field("number", "Maximum price allowed (&euro;)", "maxPrice", false, "Invalid maximum price", null));
		form.addField(new Field("submit", "Next step", "", false, "Invalid submit button (!!)", null));
		
		return ok(requestcreate.render(sess.getUsername(), null, form));
	}
	
	public static Result selectTraject() {
		Login session = new Login();
        // if (!session.isLogged())
            // return redirect("/");
		
		DynamicForm data = Form.form().bindFromRequest();
		
		
		return ok("Data received: time:"+data.get("arrivalTime"));
	}
}