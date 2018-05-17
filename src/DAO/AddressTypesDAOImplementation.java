package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedMap;
import java.util.TreeMap;

import DTO.Contact;
import validation.ScannerValidation;

public class AddressTypesDAOImplementation implements AddressTypesTableDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public SortedMap<Integer, String> getAllAddressTypes() throws SQLException {

		SortedMap<Integer, String> addressTypes = new TreeMap<Integer, String>();

		String query = "SELECT * FROM address_types";

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				addressTypes.put(rs.getInt("AddressID"), rs.getString("AddressType"));
			}
		}
		return addressTypes;
	}

	@Override
	public void printAllAddressTypes() throws SQLException {

		SortedMap<Integer, String> addressTypes = getAllAddressTypes();

		for (SortedMap.Entry<Integer, String> entry : addressTypes.entrySet()) {
			System.out.println("\t" + entry.getKey() + " => " + entry.getValue());
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void addAddressType() throws SQLException {

		System.out.print("Enter new address type name: ");
		String newAddressType = ScannerValidation.getString();

		String query = "INSERT INTO address_types(AddressType) VALUES (?)";

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setString(1, newAddressType);

			statement.executeUpdate();
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void editAddressType() throws SQLException {

		System.out.print("Enter AddressID to edit address type: ");
		int addressID = ScannerValidation.getInt();

		System.out.print("Enter new address type name: ");
		String addressType = ScannerValidation.getString();

		String query = "UPDATE address_types SET AddressType='" + addressType + "' WHERE AddressID = " + addressID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate(query);
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void deleteAddressType(Contact activeContact) throws SQLException {

		System.out.print("Enter AddressID to delete address type for it: ");
		int addressID = ScannerValidation.getInt();

		String query = "DELETE FROM address_types WHERE AddressID = " + addressID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Selected address type cannot be deleted. Some contacts use this type. ");

			System.out.println("\nDo you want to change address type for all contacts that use this type?");
			System.out.println("    1) Yes");
			System.out.println("    2) No");

			boolean exitLoop2 = false;

			while (!exitLoop2) {

				System.out.print("\nEnter option: ");
				int option = ScannerValidation.getInt();

				switch (option) {
				case 1:
					changeContactsAddressTypes();

					exitLoop2 = true;
					break;
				case 2:
					exitLoop2 = true;
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
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	public void changeContactsAddressTypes() throws SQLException {

		System.out.print("\nEnter Address ID you want to change from: ");
		int addressIDToChangeFrom = ScannerValidation.getInt();

		System.out.print("\nEnter Address ID you want to change to: ");
		int addressIDToChangeTo = ScannerValidation.getInt();

		String query = "SELECT * FROM address WHERE AddressID = " + addressIDToChangeFrom;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {

				String query1 = "UPDATE address SET AddressID=" + addressIDToChangeTo + " WHERE AddressID = "
						+ addressIDToChangeFrom;

				try (PreparedStatement statement1 = connection.prepareStatement(query1);) {

					statement1.executeUpdate(query1);
				}
			}
		}
	}

}
