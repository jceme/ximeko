package models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import models.*;

public class JsonUsers {
	
	@SerializedName("users")
	public List <PrototypeUser> usersList = new ArrayList<PrototypeUser>();
	
	public JsonUsers () {}

}
