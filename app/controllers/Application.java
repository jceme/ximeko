package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	
    	//Get XING contacts 
    	
    	
    	//match Xing contacts with registered prototype users
    	
    	//display only contacts who are users
    	
    	PrototypeUser protoUser = PrototypeUser.find("order by email desc").first();
        render(protoUser);
    }

}