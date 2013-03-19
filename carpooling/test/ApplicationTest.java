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

			Proposal propTest = new Proposal(float kmCost, int availableSeats, carTest, userTest);


			// TEST GLOBAUX 
			// ============
			// -ProposalManager
			// => utiliser proposalManager.recordProposal(propTest) ; getProposalList(propTest.user) ;  vérifier que 
			//		la liste retournée est ok = Itérer 1 fois et assertEqual() puis tenter de voir l'élément suivant et 
			//		assertNull()
			// 

			// -TrajectManager
			//
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
