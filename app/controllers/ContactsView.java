package controllers;

import play.*;
import play.mvc.*;
import play.cache.Cache;
import play.data.validation.*;
import util.GetXingContactsAndSaveToDatabase;

import java.util.*;

import models.*;
import util.*;

public class ContactsView extends Application {
    
    @Before
    static void checkUser() {
        if(connected() == null) {
            flash.error("Bitte loggen Sie sich zunächst ein.");
            Application.index();
        }
    }
    
    // ~~~
    
    public static void list() {
    	List<XingContact> contacts = connected().xingContacts;
        render(contacts);
    }
    
    
    public static void start() {
    	List<XingContact> contacts = connected().xingContacts;
    	List<XingContact> presentContacts = getPresentXingContacts.ofUser(connected());
        render(contacts, presentContacts);
    }
    
    //mit List zusammenführen?
    public static void listPresentContacts() {
    	PrototypeUser currentUser = connected();
    	List<XingContact> presentContacts = getPresentXingContacts.ofUser(currentUser);
    	render(presentContacts);
    }
    
    public static void connectWithXing() {
    	//entfernen?
    	byte numberOfExistendContacts = GetXingContactsAndSaveToDatabase.getContactsForUser(connected());
    	start();
    }

    public static void settings() {
        render();
    }

    public static void saveSettings(String password, String verifyPassword) {
    	PrototypeUser connected = connected();
    	connected.password = password;
    	validation.valid(connected);
    	validation.required(verifyPassword);
    	validation.equals(verifyPassword, password).message("Die Passwörter stimmen nicht überein");
    	if(validation.hasErrors()) {
    		render("@settings", connected, verifyPassword);
    	}
    	connected.save();
    	flash.success("Passwort erfolgreich geändert");
    	start();
    }
}

