package validation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SortedMap;

import DAO.AddressDAOImplementation;
import DAO.AddressTypesDAOImplementation;
import DAO.ContactDAOImplementation;
import DAO.EmailDAOImplementation;
import DAO.EmailTypesDAOImplementation;
import DAO.GroupsDAOImplementation;
import DAO.GroupsTypesDAOImplementation;
import DAO.PhoneDAOImplementation;
import DAO.PhoneTypesDAOImplementation;
import DTO.Address;
import DTO.Contact;
import DTO.Mail;
import DTO.Phone;

public class InputValidation {

	PhoneDAOImplementation phoneTableDAO = new PhoneDAOImplementation();
	PhoneTypesDAOImplementation phoneTypesDAO = new PhoneTypesDAOImplementation();
	EmailDAOImplementation emailTableDAO = new EmailDAOImplementation();
	EmailTypesDAOImplementation emailTypesDAO = new EmailTypesDAOImplementation();
	AddressDAOImplementation addressTableDAO = new AddressDAOImplementation();
	AddressTypesDAOImplementation addressTypesDAO = new AddressTypesDAOImplementation();
	ContactDAOImplementation contactDAO = new ContactDAOImplementation();
	GroupsTypesDAOImplementation groupTypesDAO = new GroupsTypesDAOImplementation();
	GroupsDAOImplementation groupsTableDAO = new GroupsDAOImplementation();

	public boolean validatePhoneType(int phoneType) throws SQLException {

		SortedMap<Integer, String> phoneTypes = phoneTypesDAO.getAllPhoneTypes();

		for (SortedMap.Entry<Integer, String> entry : phoneTypes.entrySet()) {
			if (entry.getKey() == phoneType) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidPhoneRecord(int contactID, int recordID) throws SQLException {

		ArrayList<Phone> phones = phoneTableDAO.getContactsPhones(contactID);

		for (int i = 0; i < phones.size(); i++) {
			if (phones.get(i).getRecordID() == recordID) {
				return true;
			}
		}
		return false;
	}

	public boolean validateEmailType(int emailType) throws SQLException {
		SortedMap<Integer, String> emailTypes = emailTypesDAO.getAllEmailTypes();

		for (SortedMap.Entry<Integer, String> entry : emailTypes.entrySet()) {
			if (entry.getKey() == emailType) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidMailRecord(int contactID, int recordID) throws SQLException {

		ArrayList<Mail> mails = emailTableDAO.getContactsEmails(contactID);

		for (int i = 0; i < mails.size(); i++) {
			if (mails.get(i).getRecordID() == recordID) {
				return true;
			}
		}
		return false;
	}

	public boolean validateHomeAddressType(int addressType) throws SQLException {

		SortedMap<Integer, String> addressTypes = addressTypesDAO.getAllAddressTypes();

		for (SortedMap.Entry<Integer, String> entry : addressTypes.entrySet()) {
			if (entry.getKey() == addressType) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidHomeAddressRecord(int contactID, int recordID) throws SQLException {

		ArrayList<Address> addresses = addressTableDAO.getContactsAddresses(contactID);

		for (int i = 0; i < addresses.size(); i++) {
			if (addresses.get(i).getRecordID() == recordID) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidContactID(int contactID) throws SQLException {

		ArrayList<Contact> contacts = contactDAO.getContacts();

		for (int i = 0; i < contacts.size(); i++) {
			if (contacts.get(i).getContactID() == contactID) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidGroupType(int groupID) throws SQLException {

		SortedMap<Integer, String> groupTypes = groupTypesDAO.getAllGroupTypes();

		for (SortedMap.Entry<Integer, String> entry : groupTypes.entrySet()) {
			if (entry.getKey() == groupID) {
				return true;
			}
		}
		return false;
	}

}
