package validation;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.ContactDAOImplementation;
import DTO.Contact;

public class LoginValidation {

	ContactDAOImplementation contactDAO = new ContactDAOImplementation();

	public boolean validationLogin(String nickName, String password) throws SQLException {

		if (isUserPasswordCombinationRight(nickName, password)) {
			return true;
		} else {
			return false;
		}

	}

	private boolean isUserPasswordCombinationRight(String nickName, String password) throws SQLException {

		ArrayList<Contact> allContacts = contactDAO.getContacts();

		for (int i = 0; i < allContacts.size(); i++) {
			Contact contact = allContacts.get(i);

			if (contact.getNickName().equals(nickName) && contact.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

}
