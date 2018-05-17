package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import DTO.Address;
import DTO.Contact;
import DTO.Group;
import DTO.Mail;
import DTO.Note;
import DTO.Phone;
import utils.MenusAndHeaders;
import validation.ScannerValidation;

public class ContactDAOImplementation implements ContactDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public void addContact() throws SQLException {
		ContactTableDAOImplementation contactTableDAO = new ContactTableDAOImplementation();

		contactTableDAO.addContactTableInfo();
	}

	@Override
	public Contact getActiveContactObject(String nickName, String password) throws SQLException {

		ArrayList<Contact> contacts = getContacts();

		for (int i = 0; i < contacts.size(); i++) {

			Contact contact = contacts.get(i);

			if (contact.getNickName().equals(nickName) && contact.getPassword().equals(password)) {
				return contact;
			}
		}
		return null;
	}

	@Override
	public void updateContact(Contact contact) throws SQLException, ParseException {

		ContactTableDAOImplementation ContactTblDAO = new ContactTableDAOImplementation();

		MenusAndHeaders menus = new MenusAndHeaders();

		// update contact table
		ContactTblDAO.updateContactTableInfo(contact);

		// update phone table
		menus.updatePhoneMenu(contact);

		// update email table
		menus.updateEmailMenu(contact);

		// update home address table
		menus.updateHomeAddressTable(contact);

		// update groups table
		menus.updateGroupsTable(contact);

		// update birthdate table
		menus.updateBirthdateTable(contact);

		// update notes table
		menus.updateNotesTable(contact);

		// update company table
		menus.setContactsCompany(contact);
	}

	@Override
	public void searchForContact() throws SQLException {

		System.out.println("\nHow do you want to search for contact? ");
		System.out.println("    1) By first name");
		System.out.println("    2) By last name");

		boolean exitLoop1 = false;

		int option = -2;

		while (!exitLoop1) {

			System.out.print("\nEnter option: ");
			option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				exitLoop1 = true;
				break;
			case 2:
				exitLoop1 = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");
					System.out.println("*****************************************************************\n");
				} else {
					ScannerValidation.getString();
					System.out.println("*****************************************************************\n");
				}
			}
		}

		System.out.print("Enter keyword to search: ");
		String keyword = ScannerValidation.getString();

		ArrayList<Contact> contacts = searchForContactResultList(option, keyword);

		System.out.println("\nSearch results: ");

		int counter = 1;
		for (int i = 0; i < contacts.size(); i++) {
			System.out.println("Result No. " + counter);
			System.out.println("---------------------------");
			System.out.println(contacts.get(i).toString());
			System.out.println();
			counter++;
		}
	}

	private ArrayList<Contact> searchForContactResultList(int ch, String keyword) throws SQLException {

		ArrayList<Contact> contacts = new ArrayList<>();
		Contact contact = null;
		String query = "";

		switch (ch) {
		case 1:
			query = "SELECT * FROM contacts WHERE FirstName LIKE '%" + keyword + "%'";
			break;
		case 2:
			query = "SELECT * FROM contacts WHERE LastName LIKE '%" + keyword + "%'";
			break;
		default:
			System.out.println("Wrong input. Ha - ha :)");
		}

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);
			while (rs.next()) {
				contact = getContactObject(rs.getInt("ContactID"));
				contacts.add(contact);
			}
		}
		return contacts;
	}

	@Override
	public void printAllContacts() throws SQLException {

		ArrayList<Contact> contacts = getContacts();

		System.out.println("\tID     First Name    Last Name");
		System.out.println("\t------------------------------");
		for (int i = 0; i < contacts.size(); i++) {
			Contact contact = contacts.get(i);

			System.out.printf("\t%-3s    %-10s    %-10s\n", contact.getContactID(), contact.getFirstName(),
					contact.getLastName());
		}
	}

	@Override
	public Contact getContactObject(int ContactID) throws SQLException {

		SetContactObject setContactObject = new SetContactObject();
		Contact contact = new Contact();

		final int contactID = ContactID;
		final String firstName = setContactObject.getContactsFirstName(ContactID);
		final String lastName = setContactObject.getContactsLastName(ContactID);
		final String nickName = setContactObject.getContactsNickName(ContactID);
		final String password = setContactObject.getContactsPassword(ContactID);
		final ArrayList<Phone> phoneNumbers = setContactObject.getPhoneNumbersForContact(contactID);
		final ArrayList<Mail> mailAdresses = setContactObject.getEmailsForContact(contactID);
		final ArrayList<Address> homeAddresses = setContactObject.getAddresesForContact(contactID);
		final ArrayList<Group> groups = setContactObject.getClientsGruopsForContact(contactID);

		try {
			final Date birthdate = setContactObject.getBirthDateForContact(contactID);
			contact.setBirthdate(birthdate);
		} catch (SQLException e) {
		}

		final ArrayList<Note> notes = setContactObject.getNotesForClient(contactID);

		try {
			final String company = setContactObject.getCompanyForClient(contactID);
			contact.setCompany(company);
		} catch (Exception e) {
		}

		// set contact
		contact.setContactID(contactID);
		contact.setFirstName(firstName);
		contact.setLastName(lastName);
		contact.setNickName(nickName);
		contact.setPassword(password);
		contact.setPhoneNumbers(phoneNumbers);
		contact.setMailAdresses(mailAdresses);
		contact.setHomeAddresses(homeAddresses);
		contact.setGroups(groups);
		contact.setNotes(notes);

		return contact;
	}

	@Override
	public void deleteContact(int contactID, Contact activeContact) throws SQLException {

		// delete contact's info from phone table
		deleteContactsPhoneInformations(contactID);
		// delete contact's info from email table
		deleteContactsEmailInformations(contactID);
		// delete contact's info from address table
		deleteContactsAddressInformations(contactID);
		// delete contact's info from groups table
		deleteContactsGroupsInformations(contactID, activeContact);
		// delete contact's info from birthdate table
		deleteContactsBirthdateInformations(contactID);
		// delete contact's info from notes table
		deleteContactsNotesInformations(contactID);
		// delete contact's info from company table
		deleteContactsCompanyInformations(contactID);
		// delete contact's info from contacts table
		deleteContactsTabeleInformations(contactID);

		System.out.println("\nContact deleted from the database.");
	}

	@Override
	public void deleteContact(Contact contact, Contact activeContact) throws SQLException {

		if (contact != null) {

			// delete contact's info from phone table
			deleteContactsPhoneInformations(contact.getContactID());
			// delete contact's info from email table
			deleteContactsEmailInformations(contact.getContactID());
			// delete contact's info from address table
			deleteContactsAddressInformations(contact.getContactID());
			// delete contact's info from groups table
			deleteContactsGroupsInformations(contact.getContactID(), activeContact);
			// delete contact's info from birthdate table
			deleteContactsBirthdateInformations(contact.getContactID());
			// delete contact's info from notes table
			deleteContactsNotesInformations(contact.getContactID());
			// delete contact's info from company table
			deleteContactsCompanyInformations(contact.getContactID());
			// delete contact's info from contacts table
			deleteContactsTabeleInformations(contact.getContactID());

			System.out.println("\nContact deleted from the database.");
		}
	}

	private void deleteContactsPhoneInformations(int contactID) throws SQLException {
		String query = "DELETE FROM phone WHERE ContactID = " + contactID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();

		}
	}

	private void deleteContactsEmailInformations(int contactID) throws SQLException {

		String query = "DELETE FROM email WHERE ContactID = " + contactID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();
		}
	}

	private void deleteContactsAddressInformations(int contactID) throws SQLException {

		String query = "DELETE FROM address WHERE ContactID = " + contactID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();
		}
	}

	private void deleteContactsGroupsInformations(int contactID, Contact activeContact) throws SQLException {

		String query = "DELETE FROM groups WHERE ContactID = " + contactID + " AND ActiveContactID = "
				+ activeContact.getContactID();

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();
		}
	}

	private void deleteContactsBirthdateInformations(int contactID) throws SQLException {

		String query = "DELETE FROM birthdate WHERE ContactID = " + contactID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();
		}
	}

	private void deleteContactsNotesInformations(int contactID) throws SQLException {

		String query = "DELETE FROM notes WHERE ContactID = " + contactID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();
		}
	}

	private void deleteContactsCompanyInformations(int contactID) throws SQLException {

		String query = "DELETE FROM company WHERE ContactID = " + contactID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();
		}
	}

	private void deleteContactsTabeleInformations(int contactID) throws SQLException {

		String query = "DELETE FROM contacts WHERE contactID = " + contactID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();
		}
	}

	@Override
	public ArrayList<Contact> getContacts() throws SQLException {

		ArrayList<Contact> contacts = new ArrayList<>();

		String query = "SELECT * FROM contacts";

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {

				Contact contact = getContactObject(rs.getInt("ContactID"));

				contacts.add(contact);
			}
		}
		return contacts;
	}

}
