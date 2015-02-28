package controllers;

import play.*;
import play.mvc.*;
import play.cache.Cache;
import play.data.validation.*;
import sun.util.locale.provider.AuxLocaleProviderAdapter;
import util.GetXingContactsAndSaveToDatabase;

import java.util.*;

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
		start();
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

	public static void authPage(String authorizationUrl) {
		render(authorizationUrl);
	}
}
