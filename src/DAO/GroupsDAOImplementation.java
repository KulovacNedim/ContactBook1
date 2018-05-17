package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Contact;
import DTO.Group;
import validation.InputValidation;
import validation.ScannerValidation;

public class GroupsDAOImplementation implements GroupsDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();
	ContactDAOImplementation contactDAO = new ContactDAOImplementation();

	@Override
	public void addGroup(Contact contact) throws SQLException {

		GroupsTypesDAOImplementation groupTypes = new GroupsTypesDAOImplementation();
		InputValidation valid = new InputValidation();

		System.out.println("\nThere are those contacts in database:\n");
		contactDAO.printAllContacts();

		int contactID = -1;
		do {
			System.out.print("\nEnter contact\'s ID to set contact\'s groups: ");
			contactID = ScannerValidation.getInt();

			if (contactID == -1) {
				ScannerValidation.getString();
			} else if (!valid.isValidContactID(contactID)) {
				System.out.println("There is no such record ID like " + contactID + ". Try again.");
			}

		} while (!valid.isValidContactID(contactID));

		System.out.println("\nThere are those group types in database:\n");
		groupTypes.printAllGroupTypes();

		int groupID = -1;
		do {
			System.out.print("\nEnter group ID to add contact to it: ");
			groupID = ScannerValidation.getInt();

			if (groupID == -1) {
				ScannerValidation.getString();
			} else if (!valid.isValidGroupType(groupID)) {
				System.out.println("There is no such record ID like " + groupID + ". Try again.");
			}

		} while (!valid.isValidGroupType(groupID));

		String query = "INSERT INTO groups(ActiveContactID, ContactID, GroupID) VALUES (?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setInt(1, contact.getContactID());
			statement.setInt(2, contactID);
			statement.setInt(3, groupID);

			statement.executeUpdate();

		}
	}

	@Override
	public void setGroup(Contact contact) throws SQLException {

		GroupsTypesDAOImplementation groupTypes = new GroupsTypesDAOImplementation();
		InputValidation valid = new InputValidation();

		System.out.println("\nThere are those contacts in database:\n");
		contactDAO.printAllContacts();

		System.out.print("\nEnter contact\'s contact ID to set contact\'s groups: ");
		int contactID = ScannerValidation.getInt();

		System.out.println("\nThere are those group types in database:\n");
		groupTypes.printAllGroupTypes();

		int groupID = -1;
		do {
			System.out.print("\nEnter group ID to add contact to it: ");
			groupID = ScannerValidation.getInt();

			if (groupID == -1) {
				ScannerValidation.getString();
			} else if (!valid.isValidGroupType(groupID)) {
				System.out.println("There is no such record ID like " + groupID + ". Try again.");
			}

		} while (!valid.isValidGroupType(groupID));

		String query = "UPDATE groups SET GroupID = ? WHERE ContactID = " + contactID + " AND ActiveContactID = "
				+ contact.getContactID();

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setInt(1, groupID);

			statement.executeUpdate();

		}
	}

	@Override
	public ArrayList<Group> getContactsGroups(int contactID) throws SQLException {

		ArrayList<Group> groups = new ArrayList<>();

		String query = "SELECT * FROM groups";

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				groups.add(new Group(rs.getInt("RecordID"), rs.getInt("ActiveContactID"), rs.getInt("ContactID"),
						rs.getInt("GroupID")));
			}
		}
		return groups;
	}
}
