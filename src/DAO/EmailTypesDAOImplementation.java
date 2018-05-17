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

public class EmailTypesDAOImplementation implements EmailTypesDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public SortedMap<Integer, String> getAllEmailTypes() throws SQLException {

		SortedMap<Integer, String> emailTypes = new TreeMap<Integer, String>();

		String query = "SELECT * FROM email_types";

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				emailTypes.put(rs.getInt("EmailID"), rs.getString("EmailType"));
			}
		}
		return emailTypes;
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void printAllEmailTypes() throws SQLException {

		SortedMap<Integer, String> emailTypes = getAllEmailTypes();

		for (SortedMap.Entry<Integer, String> entry : emailTypes.entrySet()) {
			System.out.println("\t" + entry.getKey() + " => " + entry.getValue());
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void addEmailType() throws SQLException {

		System.out.print("\nEnter new email type name: ");
		String newEmailType = ScannerValidation.getString();

		String query = "INSERT INTO email_types(EmailType) VALUES (?)";

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setString(1, newEmailType);

			statement.executeUpdate();
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void editEmailType() throws SQLException {

		System.out.print("Enter EmailID to edit email type: ");
		int emailID = ScannerValidation.getInt();

		System.out.print("Enter new email type name: ");
		String emailType = ScannerValidation.getString();

		String query = "UPDATE email_types SET EmailType='" + emailType + "' WHERE EmailID = " + emailID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate(query);
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void deleteEmailType(Contact activeContact) throws SQLException {

		System.out.print("\nEnter Email ID to delete email type for it: ");
		int emailID = ScannerValidation.getInt();

		String query = "DELETE FROM email_types WHERE EmailID = " + emailID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Selected email type cannot be deleted. Some contacts use this type. ");

			System.out.println("\nDo you want to change e-mail type for all contacts that use this type?");
			System.out.println("    1) Yes");
			System.out.println("    2) No");

			boolean exitLoop2 = false;

			while (!exitLoop2) {

				System.out.print("\nEnter option: ");
				int option = ScannerValidation.getInt();

				switch (option) {
				case 1:
					changeContactsEmailTypes();

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
	public void changeContactsEmailTypes() throws SQLException {

		System.out.print("\nEnter Email ID you want to change from: ");
		int emailIDToChangeFrom = ScannerValidation.getInt();

		System.out.print("\nEnter Email ID you want to change to: ");
		int emailIDToChangeTo = ScannerValidation.getInt();

		String query = "SELECT * FROM email WHERE EmailID = " + emailIDToChangeFrom;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {

				String query1 = "UPDATE email SET EmailID=" + emailIDToChangeTo + " WHERE EmailID = "
						+ emailIDToChangeFrom;
				try (PreparedStatement statement1 = connection.prepareStatement(query1);) {

					statement1.executeUpdate(query1);

				}
			}
		}
	}

}
