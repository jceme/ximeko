package controllers;

import play.*;
import play.mvc.*;
import play.cache.Cache;
import play.data.validation.*;
import util.GetXingContactsAndSaveToDatabase;
import util.TokenPersistenceService;
import util.XingApiCallService;
import util.XingPersistenceService;

import java.io.IOException;
import java.util.*;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

import models.*;
import util.*;


public class ContactsView extends Application {
	
	@Before
	static void checkUser() {
		if (connected() == null) {
			flash.error("Bitte loggen Sie sich zunächst ein.");
			Application.index();
		}
	}

	// ~~~

	public static void list() {
		List<XingContact> contacts = connected().xingContacts;
		render( contacts );
	}

	public static void start() {
		List<XingContact> contacts = connected().xingContacts;
		List<XingContact> presentContacts = getPresentXingContacts.ofUser( connected() );
		render( contacts, presentContacts );
	}

	// mit List zusammenführen?
	public static void listPresentContacts() {
		User currentUser = connected();
		List<XingContact> presentContacts = getPresentXingContacts.ofUser( currentUser );
		render( presentContacts, currentUser );
	}

	public static void connectWithXing() {
		GetXingContactsAndSaveToDatabase.forUser( connected() );
//		start();
	}

	public static void settings() {
		User currentUser = connected();
		render( currentUser );
	}

	public static void saveSettings( String password, String verifyPassword ) {
		User currentUser = connected();
		currentUser.password = password;
		validation.valid(currentUser);
		validation.required(verifyPassword);
		validation.equals(verifyPassword, password).message(
				"Die Passwörter stimmen nicht überein");
		if ( validation.hasErrors() ) {
			render("@settings", currentUser, verifyPassword);
		}
		currentUser.save();
		flash.success( "Passwort erfolgreich geändert");
		start();
	}

	public static void savePresentDays( boolean presentMonday,
			boolean presentTuesday, boolean presentWednesday,
			boolean presentThursday, boolean presentFriday,
			boolean presentSaturday, boolean presentSunday ) {
		
		User currentUser = connected();
		currentUser.presentMonday = presentMonday;
		currentUser.presentTuesday = presentTuesday;
		currentUser.presentWednesday = presentWednesday;
		currentUser.presentThursday = presentThursday;
		currentUser.presentFriday = presentFriday;
		currentUser.presentSaturday = presentSaturday;
		currentUser.presentSunday = presentSunday;
		currentUser.save();
		flash.success( "Anwesenheitstage erfolgreich geändert");
		start();
	}

	public static void authPage( String authorizationUrl ) {
		if ( authorizationUrl.isEmpty() == false ) {
			render(authorizationUrl);
		} else {
			flash.error("Ein Fehler ist aufgetreten, bitte versuchen sie es später erneut");
			start();
		}
	}
	
	public static void verifier () {
		String oauth_verifier = params.get("oauth_verifier");
		String xing_error = params.get("xing_error");
		
		if ( xing_error != null && xing_error.equals("user_abort")) {
			flash.error("Sie haben den Vorgang abgebrochen, bitte wiederholen Sie ihn und geben ihr Einverständnis");
			start();
		}
		if ( oauth_verifier != null ) { 
			Verifier verifier = new Verifier( oauth_verifier );
			GetXingContactsAndSaveToDatabase.saveToken( connected(), verifier);
			flash.success("Verbindung zu XING erfolgreich hergestellt");
			start();
		} else
			flash.error("Verbindung zu XING konnte nicht hergestellt werden, bitte versuchen Sie es später erneut");
	}
	
}
