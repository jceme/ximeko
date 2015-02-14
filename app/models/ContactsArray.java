package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactsArray {
	
	@JsonProperty("contacts")
	public Contact [] contactList;
	
	public ContactsArray () {}

}
