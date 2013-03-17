package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.interfaces.IRequestManager;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.request.*;

import models.*;
import models.objects.Coordinate;
import models.objects.Proposal;
import models.objects.Request;
import models.objects.Traject;

public class RequestUI extends Controller {

	public static Result create() {
		return create(getForm());
	}
	
	private static Result create(FormUI form) {
		Login sess = new Login();
		if (!sess.isLogged())
           return redirect("/");
		return ok(requestcreate.render(sess.getUsername(), null, form));
	}
	
	private static FormUI getForm() {
		FormUI form = new FormUI("requestselecttraject");
		
		form.addField(new Field("address", "From", "fromadd", true, "Incorrect from address", null));
		form.addField(new Field("hidden", "", "fromcoordX", false, "incorrect from address", null));
		form.addField(new Field("hidden", "", "fromcoordY", false, "incorrect from address", null));
		form.addField(new Field("address", "To", "toadd", true, "Incorrect to address", null));
		form.addField(new Field("hidden", "", "tocoordX", false, "Incorrect to address", null));
		form.addField(new Field("hidden", "", "tocoordY", false, "Incorrect to address", null));
		form.addField(new Field("datetime-local", "Wished arrival hour", "arrivalTime", true, "Incorrect arrival time", "^20[0-9]{2}-[01][0-9]-[0-3][0-9]T[0-2][0-9]:[0-5][0-9]$"));
		Field seatsField = new Field("number", "Number of requested seats", "seats", true, "Incorrect required seats field", "[0-9]{1,2}");
		seatsField.value = "1";
		form.addField(seatsField);
		form.addField(new Field("number", "Tolerance time (min)", "toleranceTime", false, "Invalid tolerance time", "[0-9]{0,4}"));
		form.addField(new Field("number", "Maximum walking distance (km)", "maxWalkingDistance", false, "Invalid walking distance", "[0-9]{0,2}"));
		form.addField(new Field("number", "Maximum price allowed (€)", "maxPrice", false, "Invalid maximum price", "[0-9]{0,3}"));
		Field submitButton = new Field("button", "Next step", "", false, "Invalid submit button (!!)", null);
		submitButton.attr = "onclick=\"submitForm()\"";
		form.addField(submitButton);
		
		return form;
	}
	
	public static Result selectTraject() {
		Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
		
		FormUI form = getForm();
		DynamicForm data = Form.form().bindFromRequest();
		form.completeForm(data);
		
		if (form.isError())
			return create(form);
		
		// TODO: Instantiate the true request manager
		IRequestManager requestManager = new IRequestManager() {
			@Override public void recordRequest(Request request) {
				
			}
			@Override public void matchLater(Request request) {
				
			}
			@Override public List<Traject> findTrajects(Request request) {
				List<Traject> temp = new ArrayList<Traject>();
				temp.add(new Traject(2, 12, request, request.getUser(), null, null, new Proposal((float) 0.2, 4, null, request.getUser())));
				return temp;
			}
		};
		
		String[] dateStr = form.getStringField("arrivalTime").split("T");
		String[] timeStr = dateStr[1].split(":");
		dateStr = dateStr[0].split("-");
		
		Coordinate from = new Coordinate(form.getFloatField("fromcoordX"), form.getFloatField("fromcoordY"));
		Coordinate to	= new Coordinate(form.getFloatField("tocoordX"), form.getFloatField("tocoordY"));
		
		@SuppressWarnings("deprecation")
		Date arrivalTime = new Date(Integer.parseInt(dateStr[0]), Integer.parseInt(dateStr[1]), Integer.parseInt(dateStr[2]), Integer.parseInt(timeStr[0]), Integer.parseInt(timeStr[1]), 0);
		
		List<Traject> trajects = requestManager.findTrajects(new Request(new Coordinate(form.getFloatField("fromcoordX"), form.getFloatField("fromcoordY")), new Coordinate(form.getFloatField("tocoordX"), form.getFloatField("tocoordY")), 
				form.getStringField("fromadd"), form.getStringField("toadd"), 
				arrivalTime, form.getIntField("seats"), form.getIntField("toleranceTime"), form.getIntField("maxWalkingDistance"), form.getFloatField("maxPrice"), 
				UserManager.getUserLogged(), null));
		
		if (trajects.size() == 0)
			return redirect("/pendingrequest");
		else return ok(requestselecttraject.render(sess.getUsername(), null, trajects, from, to));//"Data received: time:"+form.getStringField("arrivalTime"));
	}
}