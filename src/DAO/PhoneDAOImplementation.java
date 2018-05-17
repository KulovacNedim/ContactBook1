package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Contact;
import DTO.Phone;
import validation.InputValidation;
import validation.ScannerValidation;

public class PhoneDAOImplementation implements PhoneDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public ArrayList<Phone> getContactsPhones(int contactID) throws SQLException {

		ArrayList<Phone> phones = new ArrayList<>();

		String query = "SELECT * FROM phone WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				Phone phone = new Phone(rs.getInt("RecordID"), rs.getInt("ContactID"), rs.getInt("PhoneID"),
						rs.getString("PhoneName"), rs.getString("PhoneNumber"));
				phones.add(phone);
			}
		}
		return phones;
	}

	@Override
	public void updatePhoneTableInfo(int contactID) throws SQLException {

		InputValidation valid = new InputValidation();

		System.out.println("\nChoose which phone you want to edit: ");

		printContactsPhones(contactID);

		int recordID = -1;
		do {
			System.out.print("Enter phone ID you want to edit: ");
			recordID = ScannerValidation.getInt();

			if (recordID == -1) {
				ScannerValidation.getString();
			} else if (!valid.isValidPhoneRecord(contactID, recordID)) {
				System.out.println("There is no such record ID like " + recordID + ". Try again.");
			}

		} while (!valid.isValidPhoneRecord(contactID, recordID));

		System.out.print("Enter new phone name:");
		String newPhoneName = ScannerValidation.getString();

		System.out.print("Enter new phone number:");
		String newPhoneNumber = ScannerValidation.getString();

		String query = "UPDATE phone SET PhoneName = ?, PhoneNumber = ? WHERE ContactID = " + contactID
				+ " AND RecordID = " + recordID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setString(1, newPhoneName);
			statement.setString(2, newPhoneNumber);

			statement.executeUpdate();
		}
	}

	@Override
	public void deleteContactsPhone(Contact contact) throws SQLException {

		InputValidation valid = new InputValidation();

		String query = "DELETE FROM phone WHERE ContactID = ? AND recordID = ?";

		System.out.println("\nChoose which phone you want to delete: ");

		printContactsPhones(contact.getContactID());

		int recordID = -1;
		do {
			System.out.print("Enter phone ID you want to delete: ");
			recordID = ScannerValidation.getInt();

			if (recordID == -1) {
				ScannerValidation.getString();
			} else if (!valid.isValidPhoneRecord(contact.getContactID(), recordID)) {
				System.out.println("There is no such record ID like " + recordID + ". Try again.");
			}

		} while (!valid.isValidPhoneRecord(contact.getContactID(), recordID));

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setInt(1, contact.getContactID());
			statement.setInt(2, recordID);

			statement.executeUpdate();
		}
	}

	@Override
	public void addPhoneTableInfo(Contact contact) throws SQLException {

		PhoneTypesDAOImplementation phoneTypeDAO = new PhoneTypesDAOImplementation();
		InputValidation typesValidation = new InputValidation();

		System.out.println("\nSelect what phone type you want to add to contact: \n");

		phoneTypeDAO.printAllPhoneTypes();

		int phoneType = 0;
		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter your choice: ");
			phoneType = ScannerValidation.getInt();

			if (typesValidation.validatePhoneType(phoneType)) {
				exitLoop = true;
			} else if (phoneType == -1) {
				ScannerValidation.getString();
			} else if (!exitLoop) {
				System.out.println("There is no such phone type like " + phoneType + ". Try again.");
			}
		}

		System.out.print("Enter contact\'s phone name - hint: ");
		String phoneName = ScannerValidation.getString();

		System.out.print("Enter contact\'s phone number: ");
		String phoneNumber = ScannerValidation.getString();

		String query = "INSERT INTO phone(ContactID, PhoneID, PhoneName, PhoneNumber) VALUES (?, ?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setInt(1, contact.getContactID());
			statement.setInt(2, phoneType);
			statement.setString(3, phoneName);
			statement.setString(4, phoneNumber);

			statement.executeUpdate();
		}
	}

	@Override
	public void printContactsPhones(int contactID) throws SQLException {

		ArrayList<Phone> phones = getContactsPhones(contactID);

		for (int i = 0; i < phones.size(); i++) {
			System.out.println("\t Phone " + phones.get(i).getRecordID() + " " + phones.get(i).toString());
		}
	}
}
