package controllers;

import models.objects.Coordinate;
import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;


import views.html.proposal.*;
import models.*;
import models.objects.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: sdefauw
 * Date: 13/03/13
 */
public class ProposalUI  extends Controller {

    /**
     * @return Initial page to create a new proposal
     */
    public static Result create() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        FormUI form = formCreate();
        return ok(create.render(session.getUsername(), null, form));
    }

    /**
     * Put proposal object un cache with information coming from step 1
     */
    public static Result createSubmit(){
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        FormUI form = formCreate();
        form.completeForm(Form.form().bindFromRequest());
        if (form.isError())
            return ok(create.render(session.getUsername(), null, form));
        //No error
        //Create proposal object
        //TODO: assiation correcte de la car
        Car car = new Car();
        User user = UserManager.getUserLogged();
        Proposal prop = new Proposal(
                form.getFloatField("kmcost"),
                form.getIntField("seats"),
                car,
                user
        );
        //Create PP object for departure en arrival
        PickupPoint departure = new PickupPoint(
                null,
                null,
                form.getStringField("fromadd"),
                new Coordinate(form.getStringField("fromcoord"))
        );
        PickupPoint arrival = new PickupPoint(
                null,
                null,
                form.getStringField("toadd"),
                new Coordinate(form.getStringField("tocoord"))
        );
        //Create Itinerary object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startdate = null;
        Date arrivaldate = null;
        try{
            startdate = dateFormat.parse(form.getStringField("starthour").replace('T',' '));
            arrivaldate = dateFormat.parse(form.getStringField("arrivalhour").replace('T',' '));
        }catch (Exception e){}
        Itinerary depItin = new Itinerary(startdate, startdate, departure);
        Itinerary arrItin = new Itinerary(arrivaldate, arrivaldate, arrival);
        prop.addItinerary(depItin);
        prop.addItinerary(arrItin);
        //Put in cache
        Cache.set("proposal#"+user.getLogin(), prop, 60*60); //Cache available 1hour
        return redirect("/proposal/selectpp");
    }

    /**
     * @return a page with possibility to select pickup point
     * in google map but also add new pickup point.
     */
    public static Result selectPP() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        //Get coord start en arrival PP
        Proposal prop = (Proposal) Cache.get("proposal#"+session("username"));
        List<Itinerary> itiList = prop.getItinerary();
        Coordinate fromCoord = itiList.get(0).getPickupPoint().getCoordinates();
        Coordinate toCoord = itiList.get(1).getPickupPoint().getCoordinates();
        //Get PickupPoint form manager
        ProposalManager pm = new ProposalManager();
        List<PickupPoint> listpp =  pm.getPickupPoints(fromCoord, toCoord);
        System.out.println(listpp);
        return ok(selectpp.render(session.getUsername(),listpp));
    }

    /**
     * @return  Form of step 1 (proposal information)
     */
    private static FormUI formCreate(){
        FormUI form =  new FormUI("proposal/create/submit");
        form.id = "new";
        form.addField(new Field("address", "From", "fromadd", true, null, null));
        form.addField(new Field("hidden", null , "fromcoord", true, null, null));
        form.addField(new Field("address", "To", "toadd", true, null, null));
        form.addField(new Field("hidden", null , "tocoord", true, null, null));
        Field car = new Field();
        car.typeinput = "select";
        car.name = "Car";
        car.id = "car";
        car.value = "<option value=\"unknown\" selected>Unknown</option>";
        form.addField(car);
        form.addField(new Field("number", "Cost in km" , "kmcost", true, "Invalid format", "[0-9]{0,2}"));
        form.addField(new Field("number", "Available seats" , "seats", true, "Invalid format", "[0-9]{0,2}"));
        form.addField(new Field("datetime-local", "Start Hour", "starthour", true, "Incorrect start time", null));
        form.addField(new Field("datetime-local", "Arrival Hour", "arrivalhour", true, "Incorrect arrival time", null));
        Field nextButton = new Field();
        nextButton.value =  "Next step";
        nextButton.id =  "nextstep";
        nextButton.typeinput = "button";
        nextButton.attr = "onclick=\"nextStep()\"";
        form.addField(nextButton);

        return form;
    }
}
