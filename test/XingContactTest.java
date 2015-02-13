import models.PrototypeUser;
import models.XingContact;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class XingContactTest extends UnitTest{
	
	@Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
	
	@Test
    public void testCreatePrototypeUsersAndXingContactsAndSaveInDatabase() {
    	//Create new PrototypeUser and  XingContact
    	PrototypeUser testUser1 = new PrototypeUser("max@mustermann.com", "pswd12").save();
    	XingContact testXing1 = new XingContact("mueller", "thomas", "xyz123", testUser1 , "thommy.mueller@jast.com").save();
    	testUser1.xingContacts.add(testXing1);
    	testUser1.save();
    	
    	
    	//Retrieve PrototypeUser with email = mustermann
    	PrototypeUser max = PrototypeUser.find("byEmail" , "max@mustermann.com").first();
    	XingContact thomas = XingContact.find("byActive_email" , "thommy.mueller@jast.com").first();
    	
    	//Test
    	assertNotNull(max);
    	assertEquals("max@mustermann.com", max.email);
    	assertNotNull(thomas);
    	assertEquals("thommy.mueller@jast.com", thomas.active_email);
    	
    	//more datasets
    	XingContact testXing2 = new XingContact("heinrich", "urst", "xyz124", testUser1 , "urst.wurst@fleisch.com").save();
    	testUser1.xingContacts.add(testXing2);
    	testUser1.save();
    	
    	XingContact testXing3 = new XingContact("fiedeldumm", "annet", "xyz125", testUser1 , "annet_f@kochen.de").save();
    	testUser1.xingContacts.add(testXing3);
    	testUser1.save();
    	
    	XingContact testXing4 = new XingContact("kurits", "zabbata", "xyz126", testUser1).save();
    	testUser1.xingContacts.add(testXing4);
    	testUser1.save();
    	
    }

}
