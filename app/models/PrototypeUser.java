package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class PrototypeUser extends Model {
	
	@Required
	@Email
	public String email;
	
	@Required
	@MinSize(5)
	@MaxSize(25)
	public String password;
	
	@ManyToMany
	public List<XingContact> xingContacts;
	
	public PrototypeUser (String email, String password) {
		this.email = email;
		this.password = password;
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
