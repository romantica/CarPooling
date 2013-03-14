package controllers;

import models.objects.Coordinate;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;


import views.html.proposal.*;
import models.*;

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
     * @return a page with possibility to select pickup point
     * in google map but also add new pickup point.
     */
    public static Result selectPP() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        //Formulaire
        DynamicForm requestData = Form.form().bindFromRequest();
        Coordinate fromCoord = new Coordinate(requestData.get("fromcoord"));
        Coordinate toCoord = new Coordinate(requestData.get("tocoord"));
        //Get PickupPoint form manager
        ProposalManager pm = new ProposalManager();
        pm.getPickupPoints(fromCoord, toCoord);
        return ok(selectpp.render(session.getUsername()));
    }

    private static FormUI formCreate(){
        FormUI form =  new FormUI("proposalselectpp");
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
        form.addField(new Field("number", "Available seats" , "seats", true, "Invalid format", "[0-9]{0,2}"));
        form.addField(new Field("datetime-local", "Start Hour", "starthour", true, "Incorrect start time", null));
        form.addField(new Field("datetime-local", "Arrival Hour", "arrivalhour", true, "Incorrect arrival time", null));

        return form;
    }
}
