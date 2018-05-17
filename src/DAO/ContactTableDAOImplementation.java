package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import DTO.Contact;
import validation.NewContactValidation;
import validation.ScannerValidation;

public class ContactTableDAOImplementation implements ContactTableDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public void updateContactTableInfo(Contact contact) throws SQLException {
		if (contact != null) {

			String query = "UPDATE contacts SET FirstName = ?, LastName = ?, NickName = ?, Password = ? WHERE contactID = "
					+ contact.getContactID();

			System.out.print("\nSet a new first name for contact (current: " + contact.getFirstName() + " ): ");
			String firstName = ScannerValidation.getString();

			System.out.print("Set a new last name for scontact (current: " + contact.getLastName() + " ): ");
			String lastName = ScannerValidation.getString();

			System.out.print("Set a new nick name for contact (current: " + contact.getNickName() + " ): ");
			String nickName = ScannerValidation.getString();

			System.out.print("Set a new password for student (current: " + contact.getPassword() + " ): ");
			String password = ScannerValidation.getString();

			try (
					PreparedStatement statement = connection.prepareStatement(query);) {

				statement.setString(1, firstName);
				statement.setString(2, lastName);
				statement.setString(3, nickName);
				statement.setString(4, password);

				statement.executeUpdate();
			}
		}

	}

	@Override
	public void addContactTableInfo() throws SQLException {

		NewContactValidation newContactValidation = new NewContactValidation();

		System.out.print("Enter contacts nick name (alias: user name): ");
		String nickName = ScannerValidation.getString();

		if (newContactValidation.isNickNameAvailable(nickName)) {

			String query = "INSERT INTO contacts(FirstName, LastName, NickName, Password) VALUES (?, ?, ?, ?)";

			System.out.print("\nEnter contacts first name: ");
			String firstName = ScannerValidation.getString();

			System.out.print("Enter contacts last name: ");
			String lastName = ScannerValidation.getString();

			System.out.print("Enter contacts password: ");
			String password = ScannerValidation.getString();

			try (PreparedStatement statement = connection.prepareStatement(query);) {

				statement.setString(1, firstName);
				statement.setString(2, lastName);
				statement.setString(3, nickName);
				statement.setString(4, password);

				statement.executeUpdate();
			}

			System.out.println("\nYour contact is created. Login and edit your personal informations.");

		} else {
			System.out.println("\nNick name you entered is not available. Try again.\n");

			addContactTableInfo();
		}

	}

}
