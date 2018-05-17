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

public class GroupsTypesDAOImplementation implements GroupTypesDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public SortedMap<Integer, String> getAllGroupTypes() throws SQLException {

		SortedMap<Integer, String> groupTypes = new TreeMap<Integer, String>();

		String query = "SELECT * FROM group_types";

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				groupTypes.put(rs.getInt("GroupID"), rs.getString("GroupName"));
			}
		}
		return groupTypes;
	}

	@Override
	public void printAllGroupTypes() throws SQLException {

		SortedMap<Integer, String> groupTypes = getAllGroupTypes();

		for (SortedMap.Entry<Integer, String> entry : groupTypes.entrySet()) {
			System.out.println("\t" + entry.getKey() + " => " + entry.getValue());
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void addGroup() throws SQLException {

		System.out.print("\nEnter new group name: ");
		String newGroupName = ScannerValidation.getString();

		String query = "INSERT INTO group_types(GroupName) VALUES (?)";

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setString(1, newGroupName);

			statement.executeUpdate();
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void editGroup() throws SQLException {

		System.out.print("Enter GroupID to edit group name: ");
		int groupID = ScannerValidation.getInt();

		System.out.print("Enter new group name: ");
		String groupName = ScannerValidation.getString();

		String query = "UPDATE group_types SET GroupName='" + groupName + "' WHERE GroupID = " + groupID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate(query);
		}
	}

	// THIS CODE NEEDS TO BE REWIEVED
	@Override
	public void changeGroupForContacts(Contact activeContact) throws SQLException {

		System.out.print("Enter GroupID you want to change from: ");
		int groupIDToChangeFrom = ScannerValidation.getInt();

		System.out.print("Enter GroupID you want to change to: ");
		int groupIDToChangeTo = ScannerValidation.getInt();

		String query = "SELECT * FROM groups WHERE GroupID = " + groupIDToChangeFrom + " AND ActiveContactID = "
				+ activeContact.getContactID();

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {

				String query1 = "UPDATE groups SET GroupID=" + groupIDToChangeTo + " WHERE GroupID = "
						+ groupIDToChangeFrom + " AND ActiveContactID = " + activeContact.getContactID();

				try (PreparedStatement statement1 = connection.prepareStatement(query1);) {

					statement1.executeUpdate(query1);
				}
			}
		}
	}

}
