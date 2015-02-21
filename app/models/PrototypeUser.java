package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

@Entity
public class PrototypeUser extends Model {
	
	@Required
	@Email
	@Column(unique = true, nullable = false)
	public String email;
	
	public String active_email;
	
	@SerializedName("id")
	public String xingId;
	
	@Required
	@MinSize(5)
	@MaxSize(25)
	public String password;
	
	@ManyToMany
	public List<XingContact> xingContacts;
	
	public boolean presentAtFair;
	public boolean presentMonday;
	public boolean presentTuesday;
	public boolean presentWednesday;
	public boolean presentThursday;
	public boolean presentFriday;
	public boolean presentSaturday;
	public boolean presentSunday;
	
	public PrototypeUser () {};
	
	public PrototypeUser (String email, String password) {
		this.email = email;
		this.password = password;
		this.xingContacts= new ArrayList();
	}
	public PrototypeUser (String email, String password, String xingId) {
		this.email = email;
		this.password = password;
		this.xingId = xingId;
		this.xingContacts= new ArrayList();
	}
	
	public PrototypeUser (String email, String password, XingContact xingContact) {
		this.email = email;
		this.password = password;
		this.xingContacts= new ArrayList();
		this.xingContacts.add(xingContact);
	}
	
//	public static List<XingContact> getContacts (PrototypeUser user) {
//		if (user == null | user.xingContacts == null)
//			return null;
//		else {
//			return user.xingContacts;
//		}
//	}

}
