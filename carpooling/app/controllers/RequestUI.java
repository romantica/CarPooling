package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.interfaces.IRequestManager;
import controllers.TrajectManager;
import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.request.*;

import models.*;
import models.objects.Composition;
import models.objects.Coordinate;
import models.objects.PickupPoint;
import models.objects.Proposal;
import models.objects.Request;
import models.objects.Traject;

public class RequestUI extends Controller {
	
	// TODO: enable the following line and disable the next to use DB instead of hard-coded samples
	private static IRequestManager requestManager = new controllers.RequestManager();
	/*private static IRequestManager requestManager = new IRequestManager() {
		@Override public void recordRequest(Request request) {
			
		}
		@Override public void matchLater(Request request) {
			
		}
		@Override public List<Traject> findTrajects(Request request) {
			List<Traject> temp = new ArrayList<Traject>();
			temp.add(new Traject(2, 12, request, request.getUser(), 
					new Composition(true, null, new PickupPoint("Louvain plein centre", "Super point de pick up", "Louvain", new Coordinate(4.704328000000032, 50.877571))), 
					new Composition(true, null, new PickupPoint("Bruxelles plein centre", "Super point de pick up", "Bruxelles", new Coordinate(4.351710300000036, 50.8503396))), 
					new Proposal((float) 0.2, 4, null, request.getUser())));
			temp.add(new Traject(1, 15, request, request.getUser(), 
					new Composition(true, null, new PickupPoint("Liège plein centre", "Super point de pick up", "Liège", new Coordinate(5.57966620000002, 50.6325574))), 
					new Composition(true, null, new PickupPoint("Arlon plein centre", "Super point de pick up", "Arlon", new Coordinate(5.816666700000042, 49.6833333))), 
					new Proposal((float) 0.2, 4, null, request.getUser())));
			return temp;
		}
	};*/

	public static Result create() {
		
		// Check if user is connected
		Login sess = new Login();
		if (!sess.isLogged())
			return redirect("/");
		
		FormUI form = getForm(sess);
		if (form.isError()) {
			Cache.remove("FRM_"+sess.getUsername());
			form = getForm(null);
		}
		
		return create(form, sess);
	}
	
	private static Result create(FormUI form, Login sess) {
		return ok(requestcreate.render(sess.getUsername(), null, form));
	}
	
	private static FormUI getForm(Login sess) {
		
		FormUI form;
		
		// Check if there is a form in cache
		if (sess != null) {
			form = (FormUI) Cache.get("FRM_"+sess.getUsername());
			if (form != null)
				return form;
		}
		
		// Create form
		form = new FormUI("requestselecttraject");
		
		// Create form fields
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
		
		// Create buttons
		Field submitButton = new Field("button", "Next step", "", false, "Invalid submit button (!!)", null);
		submitButton.attr = "onclick=\"submitForm()\"";
		form.addField(submitButton);
		
		return form;
	}
	
	public static Result selectTraject() {
		
		// Check if user is connected
		Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
		
        // Get all form data
		FormUI form = getForm(null);
		DynamicForm data = Form.form().bindFromRequest();
		form.completeForm(data);
		
		// Check form data
		if (form.isError())
			return create(form, sess);
		
		
		// Parse date string
		String[] dateStr = form.getStringField("arrivalTime").split("T");
		String[] timeStr = dateStr[1].split(":");
		dateStr = dateStr[0].split("-");
		
		// Create departure and arrival point
		Coordinate from = new Coordinate(form.getFloatField("fromcoordX"), form.getFloatField("fromcoordY"));
		Coordinate to	= new Coordinate(form.getFloatField("tocoordX"), form.getFloatField("tocoordY"));
		
		@SuppressWarnings("deprecation")
		Date arrivalTime = new Date(Integer.parseInt(dateStr[0]) - 1900, Integer.parseInt(dateStr[1]) - 1, Integer.parseInt(dateStr[2]), Integer.parseInt(timeStr[0]), Integer.parseInt(timeStr[1]), 0);
		
		// Forge request and perform match
		Request request = new Request(new Coordinate(form.getFloatField("fromcoordX"), form.getFloatField("fromcoordY")), new Coordinate(form.getFloatField("tocoordX"), form.getFloatField("tocoordY")), 
				form.getStringField("fromadd"), form.getStringField("toadd"), 
				arrivalTime, form.getIntField("seats"), form.getIntField("toleranceTime"), form.getIntField("maxWalkingDistance"), form.getFloatField("maxPrice"), 
				UserManager.getUserLogged(), null);
		List<Traject> trajects = requestManager.findTrajects(request);
		
		// Save matched trajects and request
		if (trajects != null && trajects.size() != 0)
			Cache.set("TRJ_"+sess.getUsername(), trajects);
		Cache.set("REQ_"+sess.getUsername(), request);
		Cache.set("FRM_"+sess.getUsername(), form);
		
		// And redirect to the appropriate page
		if (trajects == null || trajects.size() == 0)
			return redirect("/pendingrequest");
		else return ok(requestselecttraject.render(sess.getUsername(), null, trajects, from, to));//"Data received: time:"+form.getStringField("arrivalTime"));
	}
	
	@SuppressWarnings("unchecked")
	public static Result trajectSelected(String selected) {
		
		// Check if user is connected
		Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
        
        // Get cached data (selected traject and forged request)
        Traject selectedTraject;
        try { selectedTraject = ((ArrayList<Traject>) Cache.get("TRJ_"+sess.getUsername())).get(Integer.parseInt(selected)); } catch (NumberFormatException ex) { return redirect("/"); }
        Request request = (Request) Cache.get("REQ_"+sess.getUsername());
        
        // Clean the cache
        Cache.remove("TRJ_"+sess.getUsername());
        Cache.remove("REQ_"+sess.getUsername());
        Cache.remove("FRM_"+sess.getUsername());
        
		try {
			TrajectManager.recordTraject(selectedTraject, UserManager.getUserLogged());
		} catch (Exception e) {
			e.printStackTrace();
		}
		requestManager.recordRequest(request);
        
		return redirect("/traject");
	}
	
	public static Result pendingRequest() {
		
		// Check if user is connected
		Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
        
		return ok(pendingrequest.render(sess.getUsername(), null));
	}
	
	public static Result recordingPendingRequest() {
		
		// Check if user is connected
		Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
        
        // Get user request from cache
        Request request = (Request) Cache.get("REQ_"+sess.getUsername());
        Cache.remove("REQ_"+sess.getUsername());
        Cache.remove("FRM_"+sess.getUsername());
        
        if (request == null)
        	return ok("An error occured: cached data have expired. Please try to encode again your request");
        
        // And save it to postpone the matching
        requestManager.matchLater(request);
		
		return redirect("/traject"); 
	}
}