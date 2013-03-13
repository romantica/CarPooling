package controllers;

import models.objects.Coordinate;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;

import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

import views.html.*;

/**
 * User: sdefauw
 * Date: 13/03/13
 */
public class ProposalUI {

    /**
     * @return Initial page to create a new proposal
     */
    public static Result create() {
        Login session = new Login();
        if (!session.isLogged())
            return redirect("/");
        return ok(proposalcreate.render(session.getUsername(),null));
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
        return ok(proposalselectpp.render(session.getUsername()));
    }
}
