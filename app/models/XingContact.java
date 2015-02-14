package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class XingContact extends Model {
	
	@Required
	@JsonProperty("last_name")
	public String name;
	
	@Required
	@JsonProperty("first_name")
	public String firstName;
	
	public String display_name;
	
	@Required
	@JsonProperty("id")
	public String xingId;
	
	@ManyToMany
	public List<PrototypeUser> prototypeUsers;
	
	public String active_email;
	
	public XingContact () {}
	
	public XingContact (XingContact sourceContact) { 
		this.active_email = sourceContact.active_email;
		this.name	= sourceContact.name;
		this.xingId = sourceContact.xingId;
		this.firstName = sourceContact.firstName;
		this.display_name = sourceContact.display_name;
		this.prototypeUsers = new ArrayList();
	}
	
	public XingContact (String name, String firstName, String xingId, PrototypeUser prototypeUser) {
		this.name = name;
		this.firstName = firstName;
		this.xingId = xingId;
		this.prototypeUsers = new ArrayList();
		this.prototypeUsers.add(prototypeUser);
	}

	public XingContact(String name, String firstName, String xingId,
			PrototypeUser prototypeUser, String active_email) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.xingId = xingId;
		this.prototypeUsers = new ArrayList();
		this.prototypeUsers.add(prototypeUser);
		this.active_email = active_email;
	}
}
