package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.XingApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import play.mvc.Controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.gson.Gson;

import controllers.ContactsView;
import models.*;

public class GetXingContactsAndSaveToDatabase {
	
	private static final String PROTECTED_RESOURCE_URL = "https://api.xing.com/v1/users/me";
	private static final String GET_ID_EMAIL = "https://api.xing.com/v1/users/me.json?fields=id,active_email";
	private static final String GET_ID = "https://api.xing.com/v1/users/me.json?fields=id";
	private static final String GET_CONTACTS = "https://api.xing.com/v1/users/me/contacts.json?user_fields=id,first_name,last_name,display_name,permalink";
	
    public static byte getContactsForUser(PrototypeUser currentUser ) {
    
    	final String TOKEN_PATH = "C:/ximeko/userKeys/"+currentUser.email+"Token.ser";
		
		OAuthService service = new ServiceBuilder().
				provider(XingApi.class)
				.apiKey("891c8ed2c53864fb97c6")
				.apiSecret("e3d5b2450ab6cd84e76c19286267e2e61038337e")
				.build();
		
		Scanner in = new Scanner(System.in);
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		Token readAccessToken = null;
		
		// looking for existing access token in file system
		try {
			fis = new FileInputStream(TOKEN_PATH);
			ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			if (obj instanceof Token) {
				readAccessToken = (Token) obj;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if (ois != null)
				try {
					ois.close();
				}
				catch(IOException e) {}
			if (fis != null)
				try {
					fis.close();
				}
				catch (IOException e) {}
		}
		if (readAccessToken != null) {
			
				//Send Api request for users xing id
				OAuthRequest request = new OAuthRequest(Verb.GET, GET_ID_EMAIL);
				service.signRequest(readAccessToken, request);
				Response response = request.send();
				Gson gson = new Gson();
				JsonUsers helpUser = gson.fromJson(response.getBody(), JsonUsers.class);
				currentUser.active_email = helpUser.usersList.get(0).active_email;
				currentUser.xingId = helpUser.usersList.get(0).xingId;
				currentUser.save();
				
				//Send Api request for contacts
				request = new OAuthRequest(Verb.GET, GET_CONTACTS);
				service.signRequest(readAccessToken, request);

				//checken ob response NICHT null...
				response = request.send();
				
				gson = new Gson();
				JsonContactsWrapper contactsWrapper = gson.fromJson(
						response.getBody(), JsonContactsWrapper.class);
				XingContact checkForExistendId;
				byte numberOfExistendContacts = 0;
				Iterator<XingContact> iterator = contactsWrapper.contacts.xingContactList
						.iterator();
				if (contactsWrapper.contacts.xingContactList != null) {
					while (iterator.hasNext()) {
						XingContact newContact = new XingContact();
						newContact = iterator.next();
						checkForExistendId = XingContact.find("byXingId",
								newContact.xingId).first();
						if (checkForExistendId == null) {
							newContact.prototypeUsers = new ArrayList();
							newContact.prototypeUsers.add(currentUser);
							newContact.save();
							currentUser.xingContacts.add(newContact);
							currentUser.save();
						} else {
							numberOfExistendContacts++;
						}
					}
				}
				in.close();
				return numberOfExistendContacts;
		}
		
		else {
			// Obtain the Request Token
			Token requestToken = service.getRequestToken();
			System.out.println(service.getAuthorizationUrl(requestToken));
			System.out.println("Please paste the verifier here");
			System.out.print(">>");
			Verifier verifier = new Verifier(in.nextLine());
			in.close();

			// Trade the Request Token and Verfier for the Access Token
			Token accessToken = service.getAccessToken(requestToken, verifier);

			// save access token
			try {
				fos = new FileOutputStream(TOKEN_PATH);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(accessToken);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if (oos != null)
					try {
						oos.close(); 
					}
				catch (IOException ex) {}
				if (fos != null)
					try {
						fos.close();
					}
				catch (IOException ex2) {}
			}
			ContactsView.start();
		}
		return 0;
    }
}
