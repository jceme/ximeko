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

import play.Logger;
import play.mvc.Controller;

import com.google.gson.Gson;

import controllers.ContactsView;
import models.*;

//XXX In addition to your important comments you could also add debug/trace logging to trace the execution path in log file for debugging purpose
public class GetXingContactsAndSaveToDatabase {
	
    public static void forUser( User currentUser ) {
		final Token readAccessToken;
		// looking for existing access token in file system
		try {
			readAccessToken = TokenPersistenceService.getTokenForUser( currentUser );
		} catch ( IOException e ) {
			Logger.error( "Error wihle trying to read token ", e );
			return;
		}
		
		// XXX if-else nesting gets complicated for one method -> you might use separate methods to separate concerns
		if ( readAccessToken != null ) {

			// check if users xing id is already in database
			if ( currentUser.xingId == null ) {

				// Send Api request for users xing id if not already present database
				try {
					User xingUser;
					xingUser = XingApiCallService
							.getUserWithActiveMailAndId( readAccessToken );
					currentUser.active_email = xingUser.active_email;
					currentUser.xingId = xingUser.xingId;
					currentUser.save();
				} catch ( IOException e ) {
					Logger.error( "Failed to get Xing user", e );
					// XXX Return?
				}
			}
			// XXX Is that really an else?
			else {
				//Send Api request for users xing contacts
				JsonContactsWrapper contactsWrapper;
				try {
					contactsWrapper = XingApiCallService
							.getJsonContactsWrapperForToken( readAccessToken );
					XingPersistenceService.persistContactsFromJsonContactsWrapper(
							currentUser, contactsWrapper );
				} catch ( IOException e ) {
					// XXX No need for printing stack trace and logging, use logging solely
					//e.printStackTrace();
					// XXX Always use error level for errors!
					Logger.error( "Error receiving response of request", e );
				}
			}
		// No token stored for user, redirect user to auth page and get token
		} else {
			String AuthorizationUrl = XingApiCallService.getAuthorizationUrl();
			ContactsView.authPage( AuthorizationUrl );
		}
		
		// XXX Really use that here?? authPage above already calls start()
		ContactsView.start();
	}

    
	public static void saveToken( User currentUser, Verifier verifier ) {
		// Trade the Request Token and Verfier for the Access Token
		// XXX Trade?
		
		// XXX Put that into try block too? What exceptions can occur?
		Token accessToken = XingApiCallService.getAccessToken( verifier );
		try {
			TokenPersistenceService.saveTokenForUser( currentUser, accessToken );
		} catch ( IOException e ) {
			// XXX Check if Logger can take multiple arguments like Logger.error("my message: {}", email, e)
			Logger.error("Error while saving token for user: "
					+ currentUser.email, e);
		}
	} 		
}
