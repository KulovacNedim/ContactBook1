package DAO;

import java.sql.SQLException;

import DTO.Contact;

public interface CompanyDAOInterface {

	public void updateContactsCompany(Contact contact) throws SQLException;

}
