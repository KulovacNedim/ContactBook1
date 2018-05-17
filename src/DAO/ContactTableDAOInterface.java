package DAO;

import java.sql.SQLException;

import DTO.Contact;

public interface ContactTableDAOInterface {

	public void updateContactTableInfo(Contact contact) throws SQLException;

	public void addContactTableInfo() throws SQLException;

}
