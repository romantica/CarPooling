import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

				Car carTest = new Car("Test-Plate", "model1", "red");
				assertThat(carTest.getPlateNumber()).isEqualTo("Test-Plate");
				assertThat(carTest.getModel()).isEqualTo("model1");
				assertThat(carTest.getColor()).isEqualTo("red");

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
				assertThat(userTest.getAssessment()).isEqualTo(assessTest);

				Proposal propTest = new Proposal(1.2, 2, carTest, userTest);
				assertThat(propTest.getkmCost()).isEqualTo(1.2);
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
			proposalManager.recordProposal(propTest);
			List<Proposal> ret = proposalManager.getProposalList(propTest.getUser());
			if(ret.getItemCount() > 1){fail("Item count of list of proposal for the user is greater than 1");}
			else if (ret.getItem(0) != propTest){fail("Returned proposal not = to initially inserted proposal");}

			// -TrajectManager
			// => 
			//
			//
			//

    }	



    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }
  
   
}
