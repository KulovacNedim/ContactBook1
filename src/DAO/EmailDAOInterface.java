package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Contact;
import DTO.Mail;

public interface EmailDAOInterface {

	public ArrayList<Mail> getContactsEmails(int contactID) throws SQLException;

	public void updateContactsEmail(int contactID) throws SQLException;

	public void deleteContactsEmail(Contact contact) throws SQLException;

	public void addEmailToContact(Contact contact) throws SQLException;
	
	public void printContactsEmails(int contactID) throws SQLException;

}
