import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import models.JsonContactsWrapper;
import models.XingContact;
import models.JsonContacts;

import org.scribe.model.OAuthRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import controllers.ContactsView;

public class JSONReader {

	public static void main(String[] args) {
		JSONReader reader = new JSONReader();
		reader.readJSON();
	}

	public void readJSON() {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Gson gson = new Gson();

//		try {

			String JSON = readFile("test/res/response.json");
			System.out.println(JSON);
			
			// <- Array of User (XingContact.class) objects: ->
			
//			XINGUserContactList userList = new XINGUserContactList();
//			userList = mapper.readValue("test/res/usersResponse.json", XINGUserContactList.class);
//			System.out.println(userList.contactList);
			
			//Contacts contactsWrapper = new Contacts();
			//XingContact contactWrapper = new XingContact();
			//XINGUserContactList contactsWrapper = null;
			
			JsonContactsWrapper contactswrapper = new JsonContactsWrapper();
			contactswrapper = gson.fromJson(JSON, JsonContactsWrapper.class);
			System.out.println(contactswrapper.contacts.xingContactList.get(1).xingId);
			
			
//			JsonContactsWrapper xingContactsWrapper= new JsonContactsWrapper();
			
//
//			contactsWrapper = mapper.readValue(JSON,
//					XINGUserContactList.class);
//			System.out.println(contactsWrapper.contactList.get(0).xingId);
//			System.out.println(contactsWrapper.contactList.get(1).xingId);
			
			
			
//			System.out.println(xingContactsWrapper.contactWrapper.total);
//			System.out.println(xingContactsWrapper.contactWrapper.xingUserList.contactList.get(0).xingId);
			
			//contactsWrapper=mapper.readValue(JSON, XingContact[].class);
			
			

//			if (contactsWrapper != null) {
//				for (XingContact xingContact : contactsWrapper.xingContactList) {
//					System.out.println(xingContact.active_email);
//
//				}

//			}
			
			
			
			
//
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public String readFile(String filename) {
		String body = "";
		File file = new File(filename);

		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				body = body + line;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return body;

	}
}
