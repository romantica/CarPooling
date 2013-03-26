import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.objects.Assessment;
import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import controllers.*;
import models.objects.*;
import views.*;

import java.util.Date;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {
    
		@Test 
    public void carpoolingCheck() {
				Assessment assessTest = new Assessment(3,"Test",true);
				assertThat(assessTest.getRating()).isEqualTo(3);
				assertThat(assessTest.getComment()).isEqualTo("Test");
				assertThat(assessTest.isType()).isEqualTo(true);

				User userTest = new User("loginTest", "Mister", "Nobody", "test@test.com",
						"0123-456", 0, assessTest,
						null, null, null,
						null);
				assertThat(userTest.getLogin()).isEqualTo("loginTest");
				assertThat(userTest.getFirstName()).isEqualTo("Mister");
				assertThat(userTest.getName()).isEqualTo("Nobody");
				assertThat(userTest.getEmail()).isEqualTo("test@test.com");
				assertThat(userTest.getPhoneNumber()).isEqualTo("0123-456");
				assertThat(userTest.getBalance()).isEqualTo(0);

				Car carTest = new Car("Test-Plate", "model1", "red", userTest);
				assertThat(carTest.getPlateNumber()).isEqualTo("Test-Plate");
				assertThat(carTest.getModel()).isEqualTo("model1");
				assertThat(carTest.getColor()).isEqualTo("red");

				Proposal propTest = new Proposal(1, 2, carTest, userTest);
				assertThat(propTest.getKmCost()).isEqualTo(1.2);
				assertThat(propTest.getAvailableSeats()).isEqualTo(2);
				assertThat(propTest.getCar()).isEqualTo(carTest);
				assertThat(propTest.getUser()).isEqualTo(userTest);

				Coordinate coorATest = new Coordinate(1.5,2.5);
				assertThat(coorATest.getX()).isEqualTo(1.5);
				assertThat(coorATest.getY()).isEqualTo(2.5);
				Coordinate coorBTest = new Coordinate(3.5,4.5);

				PickupPoint pickupTest = new PickupPoint("LLN", "LLN Descr", "1348 Ottignies",coorATest);
				assertThat(pickupTest.getName()).isEqualTo("LLN");
				assertThat(pickupTest.getDescription()).isEqualTo("LLN Descr");
				assertThat(pickupTest.getAddress()).isEqualTo("1348 Ottignies");
				assertThat(pickupTest.getCoordinates()).isEqualTo(coorATest);

				Itinerary iterTest = new Itinerary(new Date(20,03,2013), new Date(20,03,2013),pickupTest);//1 seul pickup point ?
				assertThat(iterTest.getDepartureTime()).isEqualTo(new Date(20,03,2013));
				assertThat(iterTest.getArrivalTime()).isEqualTo(new Date(20,03,2013));
				assertThat(iterTest.getPickupPoint()).isEqualTo(new Date(20,03,2013));

				
			// TESTS GLOBAUX 
			// =============
			// -ProposalManager
			ProposalManager.recordProposal(propTest);
			List<Proposal> ret = ProposalManager.getProposalList(propTest.getUser());
			if(ret.getItemCount() > 1){
				Assert.fail("Item count of list of proposal for the user is greater than 1");
				}
			else if (ret.getItem(0) != propTest){
				Assert.fail("Returned proposal not = to initially inserted proposal");
				}
    }	

		@Test
		public void matchingBBTest(){
		
		/*
		====================================================
		BLACK BOX TEST  : SEE /Z/report/report.pdf,  page 16
		====================================================
		*/

		Coordinate coorATest = new Coordinate(50.669715,4.602624);
		Coordinate coorBTest = new Coordinate(50.667892,4.619073);
		User userTest = new User("loginTest", "Mister", "Nobody", "test@test.com",
															"0123-456", 0, null,
															null, null, null,
															null);
		Date arrTimeTest = new Date(2013,01,04,15,00);
		Traject trajectTest = new Traject(1, //Seats
				15, //TotalCost
				null, // Request
				userTest, //User
				null, //Dep PP
				null, //Arr PP
				null); // Proposal
		Request reqTest = new Request(
				coorATest, //Coor departure
				coorBTest, //Coor arrival
				"Place Galilee 6 1348 Louvain-La-Neuve", // Adress departure
				"Cour des Fleurets 1348 Louvain-La-Neuve",// Adress arrival 	
				arrTimeTest, //Arrival time
				1, //Seats
				60, //Time tolerance
				3000, //Walk tolerance in m
				5,//Price tolerance
				userTest, //User
				trajectTest);//Traject
		trajectTest.setRequest(reqTest);


		// BB Test : msg-1
		ArrayList<Traject> response = controllers.Matching.match(reqTest);
		for(Traject traj : response){
		    Assert.fail("Test failed : la requete n est pas dans la base de donnees mais matching retourne un trajet");
		}
		RequestManager.recordRequest(reqTest);

		// BB Test : msg-2
		ArrayList<Traject> response2 = controllers.Matching.match(reqTest);
		for(Traject traj : response2){
		    List<Proposal> result = Proposal.find.where().eq("id",traj.getProposal().getId());
		    if(result.getItemCount() < 1){
					Assert.fail("Test failed : la proposal n est pas dans la base de donnees mais matching retourne un trajet");}
		}

		// BB Test : msg-3 + msg-4
		for(Traject traj : response2){
		    PickupPoint depPP = traj.getDeparturePP().getPickupPoint();
		    PickupPoint arrPP = traj.getArrivalPP().getPickupPoint();
		    if(PickupPoint.find.where().eq("id",depPP.getId()) != depPP){
					Assert.fail("Departure PickUpPoint pas dans la base de donnees");}
		    if(PickupPoint.find.where().eq("id",arrPP.getId()) != arrPP){
					Assert.fail("Arrival PickUpPoint pas dans la base de donnees");}
		}

		for(Traject traj : response){
				// BB Test : msg-5	
				int walkBegin = Matching.distance(traj.getDeparturePP(),traj.getArrivalPP());
				if(walkBegin + walkEnd > 3000){Assert.fail("La tolerance de distance de marche n est pas respectee");}

				// BB Test : msg-6
				Date maxiTime = new Date(2013,01,04,16,00); 
				if(traj.getArrivalPP().getTime() > maxiTime){Assert.fail("L heure d arrivee du passager depasse la tolerance d heure d arrivee de la requete");}

				// BB Test : msg-7
				if(traj.getReservedSeats() - 1 < 0){Assert.fail("Il n�y a pas assez de sieges libres dans la proposition pour satisfaire la requete");}

				// BB Test : msg-8
				if(traj.getTotalCost() > 5){Assert.fail("Tolerance en cout non respectee.");} 
			}

		//Encore a tester : pickup point arrival + departure IN PP de la DB

		}



    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }
  
   
}
