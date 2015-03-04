package models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import models.*;

public class JsonUsers {
	
	@SerializedName("users")
	public List <User> usersList = new ArrayList<User>();
	
	// XXX No need to explicitly state empty constructor
	// Only use it together with other constructors and when actually doing anything in it ;)
	public JsonUsers () {}

}
