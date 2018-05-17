package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import DTO.Contact;
import validation.ScannerValidation;

public class NotesDAOImplementation implements NotesDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public void addNote(Contact contact) throws SQLException {

		System.out.print("\nEnter note for contact: ");
		String note = ScannerValidation.getString();

		String query = "INSERT INTO notes(ContactID, Note) VALUES (?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setInt(1, contact.getContactID());
			statement.setString(2, note);

			statement.executeUpdate();
		}
	}

	@Override
	public void deleteNote(Contact contact) throws SQLException {

		System.out.println("\nChoose which note you want to delete: ");

		printNotes(contact);

		System.out.print("Enter your choice: ");
		int recordID = ScannerValidation.getInt();

		String query = "DELETE FROM notes WHERE RecordID = " + recordID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();
		}
	}

	@Override
	public void markNoteAsRead(Contact contact) throws SQLException {

		System.out.println("\nChoose which note you want to mark as read: ");

		printNotes(contact);

		System.out.print("Enter your choice: ");
		int recordID = ScannerValidation.getInt();

		String query = "UPDATE notes SET Archived = 0 WHERE RecordID = " + recordID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.executeUpdate();

		}
	}

	@Override
	public void printNotes(Contact contact) throws SQLException {

		String query = "SELECT * FROM notes WHERE ContactID = " + contact.getContactID();

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				System.out.println(rs.getInt("RecordID") + " => " + rs.getString("Note") + " archived: "
						+ rs.getBoolean("Archived"));
			}
		}
	}
}
