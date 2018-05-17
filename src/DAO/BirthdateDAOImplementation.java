package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import DTO.Contact;
import validation.ScannerValidation;

public class BirthdateDAOImplementation implements BirthdateDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public void updateContactsBirthadate(Contact contact) throws SQLException {

		boolean exitLoop2 = false;

		ScannerValidation.getAnyString();
		while (!exitLoop2) {

			System.out.print("\nEnter contact\'s birthdate in format \'yyyy/MM/dd\': ");

			String dateFormat = "dd/MM/yyyy";

			String birthdate = ScannerValidation.getAnyString();

			try {
				contact.setBirthdate(new SimpleDateFormat(dateFormat).parse(birthdate));

				String query = "UPDATE birthdate SET BirthDate = ? WHERE ContactID = " + contact.getContactID();

				try (PreparedStatement statement = connection.prepareStatement(query);) {

					statement.setString(1, birthdate);

					statement.executeUpdate();

				}
				exitLoop2 = true;

			} catch (ParseException e) {
				System.out.println("Inappropriate date format. Use \'yyyy/MM/dd\' format. Try again.");
			} catch (Exception ex) {
				System.out.println("Inappropriate date format. Use \'yyyy/MM/dd\' format. Try again.");
			}
		}

	}

}
