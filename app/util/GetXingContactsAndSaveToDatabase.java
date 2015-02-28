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

public class GetXingContactsAndSaveToDatabase {
	
	private static TokenPersistenceService topeService = new TokenPersistenceService();
	private static XingApiCallService xingService = new XingApiCallService();
	private static XingPersistenceService xingPersistService = new XingPersistenceService();
	
    public static void forUser( User currentUser ) {
    
		Token readAccessToken = null;
		// looking for existing access token in file system
		try {
			readAccessToken = topeService.getTokenForUser( currentUser );
		} catch ( IOException e1 ) {
			e1.printStackTrace();
			Logger.debug( "Error wihle trying to read token ",e1 );
		}
		if ( readAccessToken != null ) {

			// check if users xing id is already in database
			if ( currentUser.xingId == null ) {

				// Send Api request for users xing id if not already present database
				try {
					User xingUser;
					xingUser = xingService
							.getUserWithActiveMailAndId( readAccessToken );
					currentUser.active_email = xingUser.active_email;
					currentUser.xingId = xingUser.xingId;
					currentUser.save();
				} catch ( IOException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				//Send Api request for users xing contacts
				JsonContactsWrapper contactsWrapper;
				try {
					contactsWrapper = xingService
							.getJsonContactsWrapperForToken( readAccessToken );
					xingPersistService.persistContactsFromJsonContactsWrapper(
							currentUser, contactsWrapper );
				} catch ( IOException e ) {
					e.printStackTrace();
					Logger.debug( "Error receiving response of request", e );
				}
			}
		// No token stored for user, redirect user to auth page and get token
		} else {
			String AuthorizationUrl = xingService.getAuthorizationUrl();
			ContactsView.authPage(AuthorizationUrl);
			Verifier verifier = new Verifier( "1234" );
			Token requestToken = xingService.getRequestToken();

			// Trade the Request Token and Verfier for the Access Token
			Token accessToken = xingService.getAccessToken( requestToken, verifier );
			try {
				topeService.saveTokenForUser( currentUser, accessToken );
			} catch ( IOException e ) {
				e.printStackTrace();
				Logger.debug("Error while saving token for user: "+currentUser.email, e);
			}
		}
		ContactsView.start();
	}
}
