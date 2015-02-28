package models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import models.*;

public class JsonUsers {
	
	@SerializedName("users")
	public List <User> usersList = new ArrayList<User>();
	
	public JsonUsers () {}

}
