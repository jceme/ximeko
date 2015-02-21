package models;

import java.util.List;

import javax.persistence.ManyToMany;

import play.data.validation.Required;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactForJson {
	
	@Required
	@JsonProperty("id")
	public String xingId;
	
	@Required
	@JsonProperty("last_name")
	public String name;
	
	@Required
	@JsonProperty("first_name")
	public String firstName;
	
	public String display_name;
	
	@ManyToMany
	public List<PrototypeUser> prototypeUsers;
	
	public String active_email;
	
	public ContactForJson() {
	}
}
