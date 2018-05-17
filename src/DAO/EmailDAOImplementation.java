package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Contact;
import DTO.Mail;
import validation.InputValidation;
import validation.ScannerValidation;

public class EmailDAOImplementation implements EmailDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public ArrayList<Mail> getContactsEmails(int contactID) throws SQLException {

		ArrayList<Mail> mails = new ArrayList<>();

		String query = "SELECT * FROM email WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				Mail mail = new Mail(rs.getInt("RecordID"), rs.getInt("ContactID"), rs.getInt("EmailID"),
						rs.getString("EmailName"), rs.getString("EmailAddress"));
				mails.add(mail);
			}
		}

		return mails;
	}

	@Override
	public void updateContactsEmail(int contactID) throws SQLException {

		InputValidation valid = new InputValidation();

		System.out.println("\nChoose which e-mail you want to edit: ");

		printContactsEmails(contactID);

		int recordID = -1;
		do {
			System.out.print("Enter e-mail ID you want to edit: ");
			recordID = ScannerValidation.getInt();

			if (recordID == -1) {
				ScannerValidation.getString();
			} else if (!valid.isValidMailRecord(contactID, recordID)) {
				System.out.println("There is no such record ID like " + recordID + ". Try again.");
			}

		} while (!valid.isValidMailRecord(contactID, recordID));

		System.out.print("Enter new e-mail name:");
		String newEmailName = ScannerValidation.getString();

		System.out.print("Enter e-mail address:");
		String newEmailAddress = ScannerValidation.getString();

		String query = "UPDATE email SET EmailName = ?, EmailAddress = ? WHERE ContactID = " + contactID
				+ " AND RecordID = " + recordID;

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setString(1, newEmailName);
			statement.setString(2, newEmailAddress);

			statement.executeUpdate();
		}
	}

	@Override
	public void deleteContactsEmail(Contact contact) throws SQLException {

		InputValidation valid = new InputValidation();

		String query = "DELETE FROM email WHERE ContactID = ? AND recordID = ?";

		System.out.println("\nChoose which e-mail you want to delete: ");

		printContactsEmails(contact.getContactID());

		int recordID = -1;
		do {
			System.out.print("Enter e-mail ID you want to delete: ");
			recordID = ScannerValidation.getInt();

			if (recordID == -1) {
				ScannerValidation.getString();
			} else if (!valid.isValidMailRecord(contact.getContactID(), recordID)) {
				System.out.println("There is no such record ID like " + recordID + ". Try again.");
			}

		} while (!valid.isValidMailRecord(contact.getContactID(), recordID));

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setInt(1, contact.getContactID());
			statement.setInt(2, recordID);

			statement.executeUpdate();
		}
	}

	@Override
	public void addEmailToContact(Contact contact) throws SQLException {

		EmailTypesDAOImplementation emailTypeDAO = new EmailTypesDAOImplementation();
		InputValidation typesValidation = new InputValidation();

		System.out.println("\nSelect what e-mail type you want to add to contact: \n");

		emailTypeDAO.printAllEmailTypes();

		int emailType = 0;
		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter your choice: ");
			emailType = ScannerValidation.getInt();

			if (typesValidation.validateEmailType(emailType)) {
				exitLoop = true;
			} else if (emailType == -1) {
				ScannerValidation.getString();
			} else if (!exitLoop) {
				System.out.println("There is no such phone type like " + emailType + ". Try again.");
			}
		}

		System.out.print("Enter contact\'s e-mail name - hint: ");
		String emailName = ScannerValidation.getString();

		System.out.print("Enter contact\'s email address: ");
		String emailAddress = ScannerValidation.getString();

		String query = "INSERT INTO email(ContactID, EmailID, EmailName, EmailAddress) VALUES (?, ?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(query);) {

			statement.setInt(1, contact.getContactID());
			statement.setInt(2, emailType);
			statement.setString(3, emailName);
			statement.setString(4, emailAddress);

			statement.executeUpdate();
		}
	}

	@Override
	public void printContactsEmails(int contactID) throws SQLException {

		ArrayList<Mail> mails = getContactsEmails(contactID);

		for (int i = 0; i < mails.size(); i++) {
			System.out.println("\t Phone " + mails.get(i).getRecordID() + " " + mails.get(i).toString());
		}
	}
}
