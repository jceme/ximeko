package util;

import java.util.Iterator;

import models.JsonContactsWrapper;
import models.User;
import models.XingContact;

public class XingPersistenceService {
	/**
	 * Extracts and saves the contacts of the contactsWrapper to the correspondent user into the database
	 * @param currentUser
	 * @param contactsWrapper
	 */
	public void persistContactsFromJsonContactsWrapper ( User currentUser, JsonContactsWrapper contactsWrapper) {
		XingContact checkForExistendId;
		User checkForExistendUser;
		
		Iterator<XingContact> iterator = contactsWrapper.contacts.xingContactList
				.iterator();
		if (contactsWrapper.contacts.xingContactList != null) {
			
			while (iterator.hasNext()) {
				XingContact newContact = new XingContact();
				newContact = iterator.next();
				checkForExistendId = XingContact.find("byXingId",
						newContact.xingId).first();
				
				checkForExistendUser = User.find("byXingId",
						newContact.xingId).first();
				
				if (checkForExistendId == null) {
					if (checkForExistendUser != null) {
						newContact.user = checkForExistendUser;
					}
					newContact.save();
					currentUser.xingContacts.add(newContact);
					currentUser.save();
				}
			}
		}
	}
}
