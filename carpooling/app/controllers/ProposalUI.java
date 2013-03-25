package controllers;

import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


import views.html.proposal.*;
import views.html.*;
import models.*;
import models.objects.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: sdefauw
 * Date: 13/03/13
 */
public class ProposalUI extends Controller {

    /**
     * @return Initial page to create a new proposal
     */
    public static Result create() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        FormUI form = formCreate("/proposal/create/submit");
        return ok(create.render(session.getUsername(), null, form));
    }

    /**
     * Put proposal object un cache with information coming from step 1
     */
    public static Result createSubmit() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        FormUI form = formCreate("/proposal/create/submit");
        form.completeForm(Form.form().bindFromRequest());
        if (form.isError()){
            return ok(create.render(session.getUsername(), null, form));
        }
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
        try {
            startdate = dateFormat.parse(form.getStringField("starthour").replace('T', ' '));
            arrivaldate = dateFormat.parse(form.getStringField("arrivalhour").replace('T', ' '));
        } catch (Exception e) {
        }
        Itinerary depItin = new Itinerary(startdate, startdate, departure);
        Itinerary arrItin = new Itinerary(arrivaldate, arrivaldate, arrival);
        prop.addItinerary(depItin);
        prop.addItinerary(arrItin);
        Cache.set("proposal#" + user.getLogin(), prop, 60 * 60); //Cache available 1hour
        return redirect("/proposal/selectpp");
    }

    /**
     * @return a page with possibility to select pickup point
     *         in google map but also add new pickup point.
     */
    public static Result selectPP() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        //Get coord start en arrival PP
        Proposal prop = (Proposal) Cache.get("proposal#" + session("username"));
        List<Itinerary> itiList = prop.getItinerary();
        Coordinate fromCoord = itiList.get(0).getPickupPoint().getCoordinates();
        Coordinate toCoord = itiList.get(1).getPickupPoint().getCoordinates();
        //Get PickupPoint form manager
        ProposalManager pm = new ProposalManager();
        List<PickupPoint> listpp = pm.getPickupPoints(fromCoord, toCoord);
        Cache.set("listpp#" + session("username"), listpp, 60 * 60); //Cache available 1hour
        return ok(selectpp.render(session.getUsername(), listpp, fromCoord, toCoord));
    }

    /**
     * Add pickup point of itinary in proposal coming from form.
     */
    public static Result selectPPSubmit() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        Proposal prop = (Proposal) Cache.get("proposal#" + session("username"));
        if (prop == null) return redirect("/proposal");
        //Form
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String[] e = entry.getKey().split("_");
            if (e.length == 1) {
                String id = entry.getKey();
                //Check if date-hour are wrong
                String regex = "^20[0-9]{2}-[01][0-9]-[0-3][0-9]T[0-2][0-9]:[0-5][0-9]$";
                if (!form.get(id + "_starttime").matches(regex) ||
                        !form.get(id + "_arrivaltime").matches(regex))
                    return redirect("/proposal/selectpp");
                //Itinerary Time
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date startdate = null;
                Date arrivaldate = null;
                try {
                    startdate = dateFormat.parse(form.get(id + "_starttime").replace('T', ' '));
                    arrivaldate = dateFormat.parse(form.get(id + "_arrivaltime").replace('T', ' '));
                } catch (Exception except) {}
                //Pickup point object
                if (entry.getValue().equals("new")) { //New PP
                    //Create new PickupPoint
                    PickupPoint p = new PickupPoint();
                    p.setName(form.get(id + "_name"));
                    p.setCoordinates(new Coordinate(form.get(id + "_coord")));
                    p.setAddress(form.get(id + "_address"));
                    //Add Pickup Point to Itinerary to Proposal
                    Itinerary Itin = new Itinerary(startdate, arrivaldate, p);
                    prop.addItinerary(Itin);
                } else { //PP in database
                    //Search pp in list in cache coming form db
                    List<PickupPoint> listpp = (List<PickupPoint>) Cache.get("listpp#" + session("username"));
                    PickupPoint ppselected = null;
                    for(PickupPoint c: listpp) {
                        if (c.getId() == Integer.parseInt(id)){
                            ppselected = c;
                            break;
                        }
                    }
                    //Add Pickup Point to Itinerary to Proposal
                    Itinerary Itin = new Itinerary(startdate, arrivaldate, ppselected);
                    prop.addItinerary(Itin);
                }
            }
        }
        //Change position of arrival in array
        Itinerary arrival = prop.getItineraryList().get(1);
        prop.getItineraryList().remove(1);
        prop.getItineraryList().add(arrival);
        //Put new Information in cache
        Cache.set("proposal#" + session("username"), prop, 60 * 60); //Cache available 1hour
        return redirect("../summary");
    }

    /**
     * Summary all data coming form forms in previous
     * step before sending in data base
     */
    public static Result summary() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        Proposal prop = (Proposal) Cache.get("proposal#" + session("username"));
        if (prop == null) return redirect("/proposal");
        return ok(summary.render(session.getUsername(), prop));
    }

    /**
     * Send Proposal object (in cache) to data base
     * via Proposal Manger
     */
    public static Result recording() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        Proposal prop = (Proposal) Cache.get("proposal#" + session("username"));
        if (prop == null) return ok("Timeout :(");
        ProposalManager pm = new ProposalManager();
        pm.recordProposal(prop);
        return ok(recorded.render(session.getUsername(), "Proposal Recorded"));
    }


    /**
     * @return Form of step 1 (proposal information)
     */
    private static FormUI formCreate(String sendDirectory) {
        FormUI form = new FormUI(sendDirectory);
        form.id = "new";
        form.addField(new Field("address", "From", "fromadd", true, null, null));
        form.addField(new Field("hidden", null, "fromcoord", true, null, null));
        form.addField(new Field("address", "To", "toadd", true, null, null));
        form.addField(new Field("hidden", null, "tocoord", true, null, null));
        form.addField(new Field("datetime-local", "Start Hour", "starthour", true, "Incorrect start time", "^20[0-9]{2}-[01][0-9]-[0-3][0-9]T[0-2][0-9]:[0-5][0-9]$"));
        form.addField(new Field("datetime-local", "Arrival Hour", "arrivalhour", true, "Incorrect arrival time", "^20[0-9]{2}-[01][0-9]-[0-3][0-9]T[0-2][0-9]:[0-5][0-9]$"));
        Field car = new Field();
        car.typeinput = "select";
        car.name = "Car";
        car.id = "car";
        car.selection  = new String[]{"Unknown"};
        form.addField(car);
        form.addField(new Field("number", "Available seats", "seats", true, "Invalid format", "[0-9]{1,2}"));
        form.addField(new Field("number", "Cost in km", "kmcost", true, "Invalid format", "[0-9]{1,2}"));
        Field nextButton = new Field();
        nextButton.value = "Next step";
        nextButton.id = "nextstep";
        nextButton.typeinput = "button";
        nextButton.attr = "onclick=\"nextStep()\"";
        form.addField(nextButton);

        return form;
    }





    public static Result driverList(){
        Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
        ProposalManager PM = new ProposalManager();
        User user = UserManager.getUserLogged();
        List<Proposal> propList = PM.getProposalList(user);
        Cache.set("proplist#" + user.getLogin(), propList, 10 * 60);
        return ok(list.render(sess.getUsername(), propList));
    }

    public static Result remove(){
        Login sess = new Login();
        if (!sess.isLogged())
            return redirect("/");
        //Get id coming from get
        DynamicForm form = Form.form().bindFromRequest();
        int id = Integer.parseInt(form.get("id"));
        //Get cache
        List<Proposal> propList = (List<Proposal>) Cache.get("proplist#" + session("username"));
        //Search proposal to remove in cache with ID
        Proposal thisprop = null;
        for(Proposal prop: propList){
            if(prop.getId() == id){
                thisprop = prop;
            }
        }
        //If id is not in cache so it's a fake remove propsal
        if (thisprop == null)
            return ok("FAKE");
        //Remove proposal
        ProposalManager PM = new ProposalManager();
        PM.deleteProposal(thisprop);
        return redirect("/traject/driver");
    }
}
