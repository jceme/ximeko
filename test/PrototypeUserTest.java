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
    	PrototypeUser testUser = new PrototypeUser("max@mustermann.com", "pswd").save();
    
    	//Retrieve PrototypeUser with Name = mustermann
    	PrototypeUser max = PrototypeUser.find("byEmail", "max@mustermann.com").first();
    	
    	//Test
    	assertNotNull(max);
    	assertEquals("max@mustermann.com", max.email);
    }
	
	@Test
	public void testCreateInvalidPrototypeUserandSaveInDatabase() {
		//Create new invalid PrototypeUser //sollte fehlschlagen, da required, scheinbar ist "" gültig
		new PrototypeUser("hannes@imglueck.de", "").save();
		
		//Retrieve PrototypeUser with Name = ""
		PrototypeUser hannes = PrototypeUser.find("byEmail", "hannes@imglueck.de").first();
    	
    	//Test
    	assertNull(hannes);
	}

}
