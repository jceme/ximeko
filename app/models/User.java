package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.google.gson.annotations.SerializedName;

import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

@Entity
public class User extends Model {
	
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
	
	@OneToMany 
	public List<XingContact> xingContacts;
	
	public boolean presentAtFair;
	public boolean presentMonday;
	public boolean presentTuesday;
	public boolean presentWednesday;
	public boolean presentThursday;
	public boolean presentFriday;
	public boolean presentSaturday;
	public boolean presentSunday;
	
	
	// XXX As with constructors, try to delegate all to one initializer
	
	public User () {
		// XXX This seems odd since email and password is mandatory according to the remaining constructors
		
		// XXX Delegation would be:
		// this(DEFAULT_EMAIL, DEFAULT_PASSWORD);
	}
	
	public User (String email, String password) {
		// XXX Only delegate like this:
		// this(email, password, (String) null);
		this.email = email;
		this.password = password;
		this.xingContacts= new ArrayList();
	}
	
	// XXX So either a Xing ID or a Xing contact is required to have a valid user or is this optional? Then better is setters.
	
	public User (String email, String password, String xingId) {
		this.email = email;
		this.password = password;
		this.xingId = xingId;
		this.xingContacts= new ArrayList();
	}
	
	public User (String email, String password, XingContact xingContact) {
		// XXX First delegate
		// this(email, password, (String) null);
		// Skip this
		this.email = email;
		this.password = password;
		this.xingContacts= new ArrayList();
		// XXX And then just add contact (if it's not null?)
		this.xingContacts.add(xingContact);
	}
	
//	public static List<XingContact> getContacts (User user) {
//		if (user == null | user.xingContacts == null)
//			return null;
//		else {
//			return user.xingContacts;
//		}
//	}

}
