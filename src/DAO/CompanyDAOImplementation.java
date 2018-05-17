package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DTO.Contact;
import validation.ScannerValidation;

public class CompanyDAOImplementation implements CompanyDAOInterface {

	Connection connection = ConnectionManager.getInstance().getConnection();

	@Override
	public void updateContactsCompany(Contact contact) throws SQLException {

		System.out.print("Enter contact\'s company name: ");

		String company = ScannerValidation.getString();

		if (contact.getCompany() != null) {

			String query = "UPDATE company SET Company = ? WHERE ContactID = " + contact.getContactID();

			try (PreparedStatement statement = connection.prepareStatement(query);) {

				statement.setString(1, company);
				;

				statement.executeUpdate();
			}

		} else {
			String query = "INSERT INTO company(ContactID, Company) VALUES (?, ?)";

			try (PreparedStatement statement = connection.prepareStatement(query);) {

				statement.setInt(1, contact.getContactID());
				statement.setString(2, company);

				statement.executeUpdate();
			}
		}
	}

}
