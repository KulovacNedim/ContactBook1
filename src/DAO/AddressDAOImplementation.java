package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Address;
import DTO.Contact;
import validation.InputValidation;
import validation.ScannerValidation;

public class AddressDAOImplementation implements AddressDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public ArrayList<Address> getContactsAddresses(int contactID) throws SQLException {

		ArrayList<Address> addresses = new ArrayList<>();

		String query = "SELECT * FROM address WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {
			rs = statement.executeQuery(query);
			while (rs.next()) {
				Address address = new Address(rs.getInt("RecordID"), rs.getInt("ContactID"), rs.getInt("AddressID"),
						rs.getString("State"), rs.getString("City"), rs.getString("Street"), rs.getString("Zip"));
				addresses.add(address);
			}
		}

		return addresses;
	}

	@Override
	public void updateContactsAddress(int contactID) throws SQLException {

		InputValidation valid = new InputValidation();

		System.out.println("\nChoose which home address you want to edit: ");

		printContactsAddresses(contactID);

		int recordID = -1;
		do {
			System.out.print("Enter your choice: ");
			recordID = ScannerValidation.getInt();

			if (recordID == -1) {
				ScannerValidation.getString();
			} else if (!valid.isValidHomeAddressRecord(contactID, recordID)) {
				System.out.println("There is no such record ID like " + recordID + ". Try again.");
			}

		} while (!valid.isValidHomeAddressRecord(contactID, recordID));

		System.out.print("Enter new contact\'s state: ");
		String state = ScannerValidation.getString();

		System.out.print("Enter new contact\'s city: ");
		String city = ScannerValidation.getString();

		System.out.print("Enter new contact\'s street: ");
		String street = ScannerValidation.getString();

		System.out.print("Enter new contact\'s zip code: ");
		String zipCode = ScannerValidation.getString();

		String query = "UPDATE address SET State = ?, City = ?, Street = ?, Zip = ? WHERE ContactID = " + contactID
				+ " AND RecordID = " + recordID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setString(1, state);
			statement.setString(2, city);
			statement.setString(3, street);
			statement.setString(4, zipCode);

			statement.executeUpdate();
		}

	}

	@Override
	public void deleteContactsAddress(Contact contact) throws SQLException {

		InputValidation valid = new InputValidation();

		String query = "DELETE FROM address WHERE ContactID = ? AND recordID = ?";

		System.out.println("\nChoose which address you want to delete: ");

		printContactsAddresses(contact.getContactID());

		int recordID = -1;
		do {
			System.out.print("Enter address ID you want to delete: ");
			recordID = ScannerValidation.getInt();

			if (recordID == -1) {
				ScannerValidation.getString();
			} else if (!valid.isValidHomeAddressRecord(contact.getContactID(), recordID)) {
				System.out.println("There is no such record ID like " + recordID + ". Try again.");
			}

		} while (!valid.isValidHomeAddressRecord(contact.getContactID(), recordID));

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setInt(1, contact.getContactID());
			statement.setInt(2, recordID);

			statement.executeUpdate();
		}
	}

	@Override
	public void addAddressToContact(Contact contact) throws SQLException {

		EmailTypesDAOImplementation emailTypeDAO = new EmailTypesDAOImplementation();
		InputValidation typesValidation = new InputValidation();

		System.out.println("\nSelect what home address type you want to add to contact: \n");

		emailTypeDAO.printAllEmailTypes();

		int addressType = 0;
		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter your choice: ");
			addressType = ScannerValidation.getInt();

			if (typesValidation.validateHomeAddressType(addressType)) {
				exitLoop = true;
			} else if (addressType == -1) {
				ScannerValidation.getString();
			} else if (!exitLoop) {
				System.out.println("There is no such phone type like " + addressType + ". Try again.");
			}
		}

		System.out.print("Enter contact\'s state: ");
		String state = ScannerValidation.getString();

		System.out.print("Enter contact\'s city: ");
		String city = ScannerValidation.getString();

		System.out.print("Enter contact\'s street: ");
		String street = ScannerValidation.getString();

		System.out.print("Enter contact\'s zip code: ");
		String zipCode = ScannerValidation.getString();

		String query = "INSERT INTO address(ContactID, AddressID, State, City, Street, Zip) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setInt(1, contact.getContactID());
			statement.setInt(2, addressType);
			statement.setString(3, state);
			statement.setString(4, city);
			statement.setString(5, street);
			statement.setString(6, zipCode);

			statement.executeUpdate();
		}

	}

	@Override
	public void printContactsAddresses(int contactID) throws SQLException {

		ArrayList<Address> addresses = getContactsAddresses(contactID);

		for (int i = 0; i < addresses.size(); i++) {
			System.out.println("\t Phone " + addresses.get(i).getRecordID() + " " + addresses.get(i).toString());
		}

	}

}
