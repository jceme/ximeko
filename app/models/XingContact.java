package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

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
	
	@ManyToOne
	public User user;
	
	public String active_email;
	
	public XingContact () {
		// XXX Again this seems odd, see User()
	}
	
	public XingContact (String name, String firstName, String xingId, User user) {
		// XXX Just delegate:
		// this(name, firstName, xingId, user, null);
		this.name = name;
		this.firstName = firstName;
		this.xingId = xingId;
		this.user = user;
	}

	public XingContact(String name, String firstName, String xingId,
			User user, String active_email) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.xingId = xingId;
		this.user = user;
		this.active_email = active_email;
	}
}
