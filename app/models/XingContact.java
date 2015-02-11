package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class XingContact extends Model {
	
	@Required
	public String name;
	
	@Required
	public String firstName;
	
	@Required
	public String xingId;
	
	@ManyToMany
	public List<PrototypeUser> prototypeUsers;
	
	public String active_email;
	
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
