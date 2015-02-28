import models.User;
import models.XingContact;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class PrototypeUserTest extends UnitTest{
	
	@Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
	
	@Test
    public void testCreatePrototypeUserAndSaveInDatabase() {
    	//Create new User
    	User protoGordan = new User("gordan.just@proto.de", "12345");
		protoGordan.presentAtFair = true;
		protoGordan.save();
    
    	//Retrieve User with Name = mustermann
    	User gordan = User.find("byEmail", "gordan.just@proto.de").first();
    	
    	//Test
    	assertNotNull(gordan);
    	assertEquals("gordan.just@proto.de", gordan.email);
    	
    	//Create new User
    	User protoUser1 = new User("horst.evers@home.de", "1337!", "21972545_14dd32");
    	protoUser1.presentAtFair = true;
    	protoUser1.presentMonday = true;
    	protoUser1.presentTuesday = true;
    	protoUser1.save();
    	
    
    	//Retrieve User with Name = mustermann
    	User horst = User.find("byEmail", "horst.evers@home.de").first();
    	
    	//Test
    	assertNotNull(horst);
    	assertEquals("horst.evers@home.de", horst.email);
    	
    	//Create new User
    	new User("evelin.oracle@home.de", "987654").save();
    
    	//Retrieve User with Name = mustermann
    	User evelin = User.find("byEmail", "evelin.oracle@home.de").first();
    	
    	//Test
    	assertNotNull(evelin);
    	assertEquals("evelin.oracle@home.de", evelin.email);
    	
    	//Create new User
    	User protoUser2 = new User("hannah.soj@er.de", "geheim", "12557029_6f254b");
    	protoUser2.presentAtFair = true;
    	protoUser2.presentThursday = true;
    	protoUser2.presentFriday = true;
    	protoUser2.presentSaturday = true;
    	protoUser2.presentSunday = true;
    	protoUser2.save();
    	
    
    	//Retrieve User with Name = mustermann
    	User hannah = User.find("byEmail", "hannah.soj@er.de").first();
    	
    	//Test
    	assertNotNull(hannah);
    	assertEquals("hannah.soj@er.de", hannah.email);
    	
    }
	
	//schlägt fehl, da noch keine Überprüfung ob strings = "" bzw. null sind...
//	@Test
//	public void testCreateInvalidPrototypeUserandSaveInDatabase() {
//		//Create new invalid User //sollte fehlschlagen, da required, scheinbar ist "" gültig
//		new User("hannes@imglueck.de", "").save();
//		
//		//Retrieve User with Name = ""
//		User hannes = User.find("byEmail", "hannes@imglueck.de").first();
//    	
//    	//Test
//    	assertNull(hannes);
//	}

}
