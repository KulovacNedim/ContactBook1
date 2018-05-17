package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import DTO.Address;
import DTO.Group;
import DTO.Mail;
import DTO.Note;
import DTO.Phone;

public class SetContactObject {

	Connection connection = ConnectionManager.getInstance().getConnection();

	public String getContactsFirstName(int contactID) throws SQLException {

		String firstName;

		String query = "SELECT * FROM contacts WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			rs.next();
			firstName = rs.getString("FirstName");
		}

		return firstName;
	}

	public String getContactsLastName(int contactID) throws SQLException {

		String lastName;

		String query = "SELECT LastName FROM contacts WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			rs.next();

			lastName = rs.getString("LastName");
		}

		return lastName;
	}

	public String getContactsNickName(int contactID) throws SQLException {

		String nickName;

		String query = "SELECT NickName FROM contacts WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			rs.next();
			nickName = rs.getString("NickName");
		}

		return nickName;
	}

	public String getContactsPassword(int contactID) throws SQLException {

		String password;

		String query = "SELECT Password FROM contacts WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			rs.next();
			password = rs.getString("Password");
		}

		return password;
	}

	public ArrayList<Phone> getPhoneNumbersForContact(int contactID) throws SQLException {

		final ArrayList<Phone> phoneNumbers = new ArrayList<>();

		String query = "SELECT * FROM phone WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				phoneNumbers.add(new Phone(rs.getInt("RecordID"), rs.getInt("ContactID"), rs.getInt("PhoneID"), rs.getString("PhoneName"),
						rs.getString("PhoneNumber")));
			}
		}
		return phoneNumbers;
	}

	public ArrayList<Mail> getEmailsForContact(int contactID) throws SQLException {

		final ArrayList<Mail> mailAdresses = new ArrayList<>();

		String query = "SELECT * FROM email WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				mailAdresses.add(new Mail(rs.getInt("RecordID"), rs.getInt("ContactID"), rs.getInt("EmailID"), rs.getString("EmailName"),
						rs.getString("EmailAddress")));
			}
		}

		return mailAdresses;
	}

	public ArrayList<Address> getAddresesForContact(int contactID) throws SQLException {

		final ArrayList<Address> homeAddresses = new ArrayList<>();

		String query = "SELECT * FROM address WHERE ContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				homeAddresses.add(new Address(rs.getInt("RecordID"), rs.getInt("ContactID"), rs.getInt("AddressID"), rs.getString("State"),
						rs.getString("City"), rs.getString("Street"), rs.getString("Zip")));
			}
		}
		return homeAddresses;
	}

	public ArrayList<Group> getClientsGruopsForContact(int contactID) throws SQLException {

		final ArrayList<Group> groups = new ArrayList<>();

		String query = "SELECT * FROM groups WHERE ActiveContactID = " + contactID;

		ResultSet rs = null;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) {
				groups.add(new Group(rs.getInt("RecordID"), rs.getInt("ActiveContactID"), rs.getInt("ContactID"), rs.getInt("GroupID")));
			}
		}
		return groups;
	}

	public Date getBirthDateForContact(int contactID) throws SQLException {

		String query = "SELECT * FROM birthdate WHERE ContactID = " + contactID;

		ResultSet rs = null;

		Date date = new Date();

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			rs.next();

			date = rs.getDate("BirthDate");
		}
		return date;
	}

	public ArrayList<Note> getNotesForClient(int contactID) throws SQLException {

		final ArrayList<Note> notes = new ArrayList<>();

		String query = "SELECT * FROM notes WHERE ContactID = " + contactID;

		ResultSet rs = null;
		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);

			try {
				while (rs.next()) {
					notes.add(new Note(rs.getInt("RecordID"), rs.getInt("ContactID"), rs.getString("Note"), rs.getBoolean("Archived")));
				}
			} catch (Exception e) {
				System.err.println("eto");
			}
		}
		return notes;
	}

	public String getCompanyForClient(int contactID) throws SQLException {

		String query = "SELECT * FROM company WHERE ContactID = " + contactID;

		ResultSet rs = null;

		String company;

		try (Statement statement = connection.createStatement();) {

			rs = statement.executeQuery(query);
			rs.next();
			company = rs.getString("Company");
		}
		return company;
	}

}
