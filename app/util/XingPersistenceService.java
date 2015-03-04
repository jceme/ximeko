package util;

import java.util.Iterator;

import models.JsonContactsWrapper;
import models.User;
import models.XingContact;

public class XingPersistenceService {
	// XXX You should avoid empty @params ... either comment on them or leave them out
	/**
	 * Extracts and saves the contacts of the contactsWrapper to the correspondent user into the database
	 * @param currentUser
	 * @param contactsWrapper
	 */
	public static void persistContactsFromJsonContactsWrapper ( User currentUser, JsonContactsWrapper contactsWrapper) {
		//Iterator<XingContact> iterator = contactsWrapper.contacts.xingContactList.iterator();
		// XXX That doesn't make sense to first access contactlist above with .iterator and check below if it is not null, do that before
		if (contactsWrapper.contacts.xingContactList != null) {
			
			// XXX Better use for loop for iteration
			for (XingContact newContact : contactsWrapper.contacts.xingContactList) {
			//while (iterator.hasNext()) {
				// XXX Creating new XingContact first and immediately discarding it by using iterator.next()? ;)
				//XingContact newContact = new XingContact();
				//newContact = iterator.next();
				XingContact checkForExistentId = XingContact.find("byXingId",
						newContact.xingId).first();
				
				User checkForExistentUser = User.find("byXingId",
						newContact.xingId).first();
				
				// XXX In this constellation move second find() into first if to avoid finding if xing id != null
				if (checkForExistentId == null) {
					if (checkForExistentUser != null) {
						newContact.user = checkForExistentUser;
					}
					newContact.save();
					currentUser.xingContacts.add(newContact);
					currentUser.save();
				}
			}
			// XXX A lot of saves esp by looping -> use batch update?
		}
	}
}
