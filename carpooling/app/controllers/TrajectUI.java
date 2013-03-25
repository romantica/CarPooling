package controllers;

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
            TrajectManager.recordTraject(thistrj,user);
        }catch (Exception e){}
        return redirect("/traject/passanger");
    }
}
