package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact {
	
	public int total;
	
	@JsonProperty("users")
	public XingContact [] xingContactList;
	
	public Contact () {}

}
