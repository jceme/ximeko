package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

import play.data.validation.Required;

@Entity
public class XingContact extends Model {
	
	@Required
	@Column(unique = true, nullable = false)
	@SerializedName("id")
	public String xingId;
	
	
	@Required
	@SerializedName("last_name")
	public String name;
	
	@Required
	@SerializedName("first_name")
	public String firstName;
	
	public String permalink;
	
	public String display_name;

	
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
		this.permalink = sourceContact.permalink;
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
