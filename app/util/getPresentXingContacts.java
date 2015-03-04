package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import models.*;

public class getPresentXingContacts {
	
	public static List<XingContact> ofUser(User protoUser) {
		
		//Copy Test Beginn
		XingContact[] listArray = protoUser.xingContacts.toArray(new XingContact[protoUser.xingContacts.size()]);
		List<XingContact> xingUserCopy = new ArrayList(Arrays.asList(listArray));
		//Copy Test Ende
		
		
		//was ist mit Duplikaten?
		// XXX Ja was ist damit?
		List<User> protoList = User.findAll();
		List<String> protoIds = new ArrayList<String>();
		
		//ids der registrierten protoUser auslesen und in protoIds speichern
		for (User user : protoList) {
			protoIds.add(user.xingId);
		}
		
		//die XingKontakte löschen, deren ID in keinem ProtoypUser wieder zu finden sind
		// XXX Wouldn't it be more reasonable to leave this task on the database??
		boolean needToDelete = true;
		XingContact currentContact;
		Iterator<XingContact> iterator = xingUserCopy.iterator();
		
		while (iterator.hasNext()) {
			currentContact = iterator.next();
			// XXX Why use external variable and not initialize here? Much safer
			needToDelete = true;
			for (String protoTypeUserId : protoIds) {
				if (currentContact.xingId.equals(protoTypeUserId)) {
					needToDelete = false;
					break;
				}
			}
			
			// XXX A boolean var is already a boolean expression like var == true ;)
			if (needToDelete == true) {
				iterator.remove();
			}
		}		
		return xingUserCopy;
	}
}
