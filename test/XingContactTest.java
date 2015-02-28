import models.User;
import models.XingContact;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class XingContactTest extends UnitTest{
	
	@Test
    public void testCreatePrototypeUsersAndXingContactsAndSaveInDatabase() {
    	//Create new User and  XingContact
    	User testUser1 = new User("max@mustermann.com", "pswd12", "xing4").save();
    	XingContact testXing1 = new XingContact("mueller", "thomas", "xing2", testUser1 , "thommy.mueller@jast.com").save();
    	testUser1.xingContacts.add(testXing1);
    	testUser1.save();
    	
    	
    	//Retrieve User with email = mustermann
    	User max = User.find("byEmail" , "max@mustermann.com").first();
    	XingContact thomas = XingContact.find("byActive_email" , "thommy.mueller@jast.com").first();
    	
    	//Test
    	assertNotNull(max);
    	assertEquals("max@mustermann.com", max.email);
    	assertNotNull(thomas);
    	assertEquals("thommy.mueller@jast.com", thomas.active_email);
    	
    	//more datasets
    	User gordan = User.find("byEmail", "gordan.just@proto.de").first();
    	
    	XingContact testXing2 = new XingContact("heinrich", "urst", "12557029_6f254b", gordan , "urst.wurst@fleisch.com").save();
    	gordan.xingContacts.add(testXing2);
    	gordan.save();
    	
    	XingContact testXing3 = new XingContact("fiedeldumm", "annet", "18452923_ac6bef", gordan , "annet_f@kochen.de").save();
    	gordan.xingContacts.add(testXing3);
    	gordan.save();
    	
    	XingContact testXing4 = new XingContact("kurits", "zabbata", "19778393_d6cc09", gordan).save();
    	gordan.xingContacts.add(testXing4);
    	gordan.save();
    	
    }

}
