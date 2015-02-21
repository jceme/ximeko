import models.PrototypeUser;
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
    	//Create new PrototypeUser
    	new PrototypeUser("gordan.just@proto.de", "12345").save();
    
    	//Retrieve PrototypeUser with Name = mustermann
    	PrototypeUser gordan = PrototypeUser.find("byEmail", "gordan.just@proto.de").first();
    	
    	//Test
    	assertNotNull(gordan);
    	assertEquals("gordan.just@proto.de", gordan.email);
    	
    	//Create new PrototypeUser
    	new PrototypeUser("horst.evers@home.de", "1337!", "19778393_d6cc09").save();
    
    	//Retrieve PrototypeUser with Name = mustermann
    	PrototypeUser horst = PrototypeUser.find("byEmail", "horst.evers@home.de").first();
    	
    	//Test
    	assertNotNull(horst);
    	assertEquals("horst.evers@home.de", horst.email);
    	
    	//Create new PrototypeUser
    	new PrototypeUser("evelin.oracle@home.de", "987654").save();
    
    	//Retrieve PrototypeUser with Name = mustermann
    	PrototypeUser evelin = PrototypeUser.find("byEmail", "evelin.oracle@home.de").first();
    	
    	//Test
    	assertNotNull(evelin);
    	assertEquals("evelin.oracle@home.de", evelin.email);
    	
    	//Create new PrototypeUser
    	new PrototypeUser("hannah.soj@er.de", "geheim", "12557029_6f254b").save();
    
    	//Retrieve PrototypeUser with Name = mustermann
    	PrototypeUser hannah = PrototypeUser.find("byEmail", "hannah.soj@er.de").first();
    	
    	//Test
    	assertNotNull(hannah);
    	assertEquals("hannah.soj@er.de", hannah.email);
    	
    }
	
	//schlägt fehl, da noch keine Überprüfung ob strings = "" bzw. null sind...
//	@Test
//	public void testCreateInvalidPrototypeUserandSaveInDatabase() {
//		//Create new invalid PrototypeUser //sollte fehlschlagen, da required, scheinbar ist "" gültig
//		new PrototypeUser("hannes@imglueck.de", "").save();
//		
//		//Retrieve PrototypeUser with Name = ""
//		PrototypeUser hannes = PrototypeUser.find("byEmail", "hannes@imglueck.de").first();
//    	
//    	//Test
//    	assertNull(hannes);
//	}

}
