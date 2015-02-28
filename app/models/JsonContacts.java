package models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import models.*;

public class JsonContacts {
	
	public int total;
	
	@SerializedName("users")
	public List <XingContact> xingContactList = new ArrayList<XingContact>();
	
	public JsonContacts () {}

}
