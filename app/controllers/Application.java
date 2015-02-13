package controllers;

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
            Contacts.index();
        }
        render();
    }
    
    public static void register() {
        render();
    }
    
    public static void saveUser(@Valid PrototypeUser user, String verifyPassword) {
        validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@register", user, verifyPassword);
        }
        user.create();
        session.put("user", user.email);
        flash.success("Welcome, " + user.email);
        Contacts.index();
    }
    
    public static void login(String useremail, String password) {
        PrototypeUser user = PrototypeUser.find("byEmailAndPassword", useremail, password).first();
        if(user != null) {
            session.put("user", user.email);
            flash.success("Willkommen, " + user.email);
            Contacts.index();         
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