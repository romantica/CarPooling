package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Field;
import models.FormUI;
import models.objects.Car;
import models.objects.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;
import views.html.user.*;
import views.html.user.car.addcar;

/**
 * User: sdefauw
 * Dtoaddate: 12/03/13
 */
public class UserUI extends Controller {
	
	public static Result main() {
		Login session = new Login();
		if(!session.isLogged()) return redirect("/user/login");
    	User user = User.find.where().like("login", session.getUsername()).findUnique();
        if (user == null) return redirect("/user/login");
        //Print the data
        return(ok(home.render(session.getUsername(), user)));

	}
	public static Result create() {
		Login session = new Login();
		//If logged, can't create a new user
		if(session.isLogged()) return redirect("/");
		//From creation
		FormUI form = createForm();
		
		return ok(usercreate.render(session.getUsername(), null, form));
	}
	
	public static Result createSubmit() {
	
		Login session = new Login();
	    if (session.isLogged())
			//If logged, can't create a new user
	        return redirect("/");
	    FormUI form = createForm();
        form.completeForm(Form.form().bindFromRequest());
        //user already use
        User testUser = User.find.where().like("login", form.getStringField("login")).findUnique();
        if (testUser != null){
        	form.getField("login").error = "Login already use";
        	form.getField("login").isError = true;
        }
        if (form.isError()){
            return ok(usercreate.render(session.getUsername(), null, form));
        }

        	
        //Correct form
        //creation of the user
        User user = new User();
        user.setLogin(form.getStringField("login"));
        user.setFirstName(form.getStringField("first"));
        user.setName(form.getStringField("last"));
        user.setEmail(form.getStringField("email"));
        user.setPhoneNumber(form.getStringField("phone"));
        user.setPassword(Login.encoderMD5(form.getStringField("pwd")));

        //TODO : call User manager
        
        user.save();
        return redirect("/login");
        		
        
	}
	
	private static FormUI createForm(){
		FormUI form = new FormUI("/user/create");
		form.id = "newUser";
		form.addField(new Field("text", "login", "login", true, null, null));
		form.addField(new Field("password", "password", "pwd", true, null, null));
		form.addField(new Field("text", "first name", "first", true, null, null));
		form.addField(new Field("text", "last name", "last", true, null, null));
		form.addField(new Field("text", "email", "email", true, null, null));//"Wrong type of email address", "[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}"));
		form.addField(new Field("text", "phone number", "phone", true, null, null));
		Field nextButton = new Field();
        nextButton.value = "submit";
        nextButton.id = "submit";
        nextButton.typeinput = "submit";
        form.addField(nextButton);
        
        return form;
	}

    public static Result loginPage() {
        //already logged
        Login session = new Login();
        if (session.isLogged())
            return redirect("/");
        return ok(loginpage.render("", null));
    }

    public static Result login() {
        DynamicForm requestData = Form.form().bindFromRequest();
        //No session
        String username = requestData.get("username");
        String pwd = requestData.get("password");
        Login session = new Login(username, pwd);
        //Is good username and pwd -> index
        if (session.isValid()) {
            session.createSession();
            return redirect("/");
        }
        //Bad username or pwd
        return ok(loginpage.render(username, "Incorrect username or password."));

    }

    public static Result logout(){
        Login session = new Login();
        session.logout();
        return redirect("/");
    }
    
    
    //Car manager
    public static Result addCar(){
    	Login session = new Login();
		if(!session.isLogged()) return redirect("/user/login");
    	return ok(addcar.render(session.getUsername(), null, carForm()));
    }
    
    public static Result recordCar(){
    	Login session = new Login();
	    if (!session.isLogged())
	        return redirect("/");
	    FormUI form = carForm();
        form.completeForm(Form.form().bindFromRequest());
        if (form.isError()){
            return ok(addcar.render(session.getUsername(), null, form));
        }
        //Correct form
        //create car
        Car car = new Car();
        car.setColor(form.getStringField("color"));
        car.setModel(form.getStringField("model"));
        car.setPlateNumber(form.getStringField("plate"));
        car.save();
        //TODO: use user manager
        User user = User.find.where().like("login", session.getUsername()).findUnique();
        user.addCar(car);
        user.save();
        return redirect("/user");
    }
    
    private static FormUI carForm(){
    	FormUI form = new FormUI("/user/car");
    	form.id = "carForm";
   		form.addField(new Field("text", "model", "model", true, null, null));
   		form.addField(new Field("text", "color", "color", true, "Wrong color", "[a-z]+"));
   		form.addField(new Field("text", "Plate Number", "plate", true, "Wrong plate number", "[A-Z][A-Z][A-Z]\\-[0-9][0-9][0-9]"));
   		Field nextButton = new Field();
        nextButton.value = "submit";
        nextButton.id = "submit";
        nextButton.typeinput = "submit";
        form.addField(nextButton);
    	return form;
    }
}
