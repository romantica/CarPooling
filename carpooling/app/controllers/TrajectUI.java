package controllers;

import models.objects.Assessment;
import models.objects.Proposal;
import models.objects.Traject;
import models.objects.User;
import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.traject.*;

import java.util.List;

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
        User user = UserManager.getUserLogged();
        List<Traject> l = TrajectManager.getTrajects(user);
        Cache.set("trjlist#" + user.getLogin(), l, 10 * 60);
        return ok(list.render(sess.getUsername(),l));
    }

    public static Result remove(){
        Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
        User user = UserManager.getUserLogged();
        //Get id coming from get
        DynamicForm form = Form.form().bindFromRequest();
        int id = Integer.parseInt(form.get("id"));
        //Get cache
        List<Traject> trjList = (List<Traject>) Cache.get("trjlist#" + session("username"));
        //Search traject to remove in cache with ID
        Traject thistrj = null;
        for(Traject trj: trjList){
            if(trj.getId() == id){
                thistrj = trj;
            }
        }
        //If id is not in cache so it's a fake remove traject
        if (thistrj == null)
            return ok("FAKE");
        //Remove traject
        try{
            TrajectManager.cancelTraject(thistrj);
        }catch (Exception e){}
        return redirect("/traject/passanger");
    }
    
    public static Result rate() {
    	
    	// Get parameters
    	DynamicForm form = Form.form().bindFromRequest();
    	int tid = Integer.parseInt(form.get("id"));
    	int trate = Integer.parseInt(form.get("rate"));
    	String comment = form.get("comment");
    	boolean isPassenger = Boolean.parseBoolean(form.get("isPassenger"));
    	
    	// Get concerned driver
    	User driver;
    	if (isPassenger) {
    		for (Traject t : (List<Traject>) Cache.get("trjlist#" + session("username")))
    			if (t.getId() == tid) {
    				driver = t.getProposal().getUser();
    				break;
    			}
    	} else {
    		// Not very useful for now if driver cannot rate passengers
    		for (Proposal p : (List<Proposal>) Cache.get("proplist#" + session("username")))
    			if (p.getId() == tid) {
    				driver = p.getUser();
    				break;
    			}
    	}
    	
    	// And add a new assesment
    	//driver.getAssessment().add(new Assessment(tid, comment, isPassenger)); // TODO: implement assessment as list of Assesment in user
    	
    	return isPassenger ? redirect("/traject/passanger") : redirect("/traject/driver");
    }
}
