package models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.*;

public class XingContactArray {
	
	@JsonProperty("users")
	public XingContact [] xingContactList;
	
	public XingContactArray () {}

}
