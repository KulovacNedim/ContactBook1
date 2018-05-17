package DAO;

import java.sql.SQLException;

import DTO.Contact;

public interface BirthdateDAOInterface {

	public void updateContactsBirthadate(Contact contact) throws SQLException;

}
