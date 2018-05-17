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

public class PhoneTypesDAOImplementation implements PhoneTypesDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public SortedMap<Integer, String> getAllPhoneTypes() throws SQLException {

		SortedMap<Integer, String> phoneTypes = new TreeMap<Integer, String>();

		String query = "SELECT * FROM phone_types";

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				phoneTypes.put(rs.getInt("PhoneID"), rs.getString("PhoneType"));
			}
		}
		return phoneTypes;
	}

	@Override
	public void printAllPhoneTypes() throws SQLException {

		SortedMap<Integer, String> phoneTypes = getAllPhoneTypes();

		for (SortedMap.Entry<Integer, String> entry : phoneTypes.entrySet()) {
			System.out.println("\t" + entry.getKey() + " => " + entry.getValue());
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void addPhoneType() throws SQLException {

		System.out.print("Enter new phone type name: ");
		String newPhoneType = ScannerValidation.getString();

		String query = "INSERT INTO phone_types(PhoneType) VALUES (?)";

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setString(1, newPhoneType);

			statement.executeUpdate();
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void editPhoneType() throws SQLException {

		System.out.print("\nEnter PhoneID to edit phone type: ");
		int phoneID = ScannerValidation.getInt();

		System.out.print("Enter new phone type name: ");
		String phoneType = ScannerValidation.getString();

		String query = "UPDATE phone_types SET PhoneType='" + phoneType + "' WHERE PhoneID = " + phoneID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate(query);
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void deletePhoneType(Contact activeContact) throws SQLException {

		System.out.print("Enter PhoneID to delete phone type for it: ");
		int phoneID = ScannerValidation.getInt();

		String query = "DELETE FROM phone_types WHERE PhoneID = " + phoneID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();

		} catch (SQLException e) {

			System.out.println("Selected phone type cannot be deleted. Some contacts use this type. ");

			System.out.println("\nDo you want to change phone type for all contacts that use this type?");
			System.out.println("    1) Yes");
			System.out.println("    2) No");

			boolean exitLoop2 = false;

			while (!exitLoop2) {

				System.out.print("\nEnter option: ");
				int option = ScannerValidation.getInt();

				switch (option) {
				case 1:
					changeContactsPhoneTypes();

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
	@Override
	public void changeContactsPhoneTypes() throws SQLException {

		System.out.print("\nEnter PhoneID you want to change from: ");
		int phoneIDToChangeFrom = ScannerValidation.getInt();

		System.out.print("\nEnter PhoneID you want to change to: ");
		int phoneIDToChangeTo = ScannerValidation.getInt();

		String query = "SELECT * FROM phone WHERE PhoneID = " + phoneIDToChangeFrom;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {

				String query1 = "UPDATE phone SET PhoneID=" + phoneIDToChangeTo + " WHERE PhoneID = "
						+ phoneIDToChangeFrom;

				try (PreparedStatement statement1 = connection.prepareStatement(query1);) {

					statement1.executeUpdate(query1);
				}
			}
		}
	}
}
