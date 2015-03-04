package models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import models.*;

public class JsonContacts {
	
	// XXX Usually you would have a private field with getters+setters instead of public fields ;)
	public int total;
	
	@SerializedName("users")
	public List <XingContact> xingContactList = new ArrayList<XingContact>();
	
	// XXX Leave implicit
	public JsonContacts () {}

}
