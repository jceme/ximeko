package controllers;

import javax.persistence.PersistenceException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import play.mvc.*;
import play.data.validation.*;
import models.*;

public class Application extends Controller {
    
    @Before
    static void addUser() {
        PrototypeUser user = connected();
        if(user != null) {
            renderArgs.put("user", user);
        }
    }
    
    static PrototypeUser connected() {
        if(renderArgs.get("user") != null) {
            return renderArgs.get("user", PrototypeUser.class);
        }
        String username = session.get("user");
        if(username != null) {
            return PrototypeUser.find("byEmail", username).first();
        } 
        return null;
    }
    
    // ~~

    public static void index() {
        if(connected() != null) {
        	ContactsView.start();
        }
        render();
    }
    
    public static void register() {
        render();
    }
    
    public static void saveUser(@Valid PrototypeUser user, String verifyPassword) {
        validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Die Passwörter stimmen nicht überein");
        if(validation.hasErrors()) {
            render("@register", user, verifyPassword);
        }
        try {
        	user.create();
//        	PrototypeUser sameEmailUser = PrototypeUser.find("byEmail",user.email).first();
//        	if (sameEmailUser == null) {
        		//unterschied create und save?
        		session.put("user", user.email);
        		flash.success("Willkommen, " + user.email);
        		ContactsView.start();
        	
        } catch (PersistenceException ex) {
        	flash.put("user.email", user.email);
    		flash.error("Email-Adresse \""+user.email+"\" ist bereits registriert.");
    		register();
        	}
    }
    
    public static void login(String useremail, String password) {
        PrototypeUser user = PrototypeUser.find("byEmailAndPassword", useremail, password).first();
        if(user != null) {
            session.put("user", user.email);
            flash.success("Willkommen, " + user.email);
            ContactsView.start();         
        }
        // Oops
        flash.put("username", useremail);
        flash.error("Fehlerhafte Email oder falsches Passwort.");
        index();
    }
    
    public static void logout() {
        session.clear();
        index();
    }

}