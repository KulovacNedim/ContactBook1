package validation;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.ContactDAOImplementation;
import DTO.Contact;

public class NewContactValidation {
	
	ContactDAOImplementation contactDAO = new ContactDAOImplementation();
	
	public boolean isNickNameAvailable(String nickName) throws SQLException {

		ArrayList<Contact> allContacts = contactDAO.getContacts();
		
		for (int i = 0; i < allContacts.size(); i++) {
			Contact contact = allContacts.get(i);
			
			if (contact.getNickName().equals(nickName)) {
				return false;
			}
		}
		
		return true;
	}

}
