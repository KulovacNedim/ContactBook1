package DAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import DTO.Contact;

public interface ContactDAOInterface {
	
	public ArrayList<Contact> getContacts() throws SQLException;

	public void addContact() throws SQLException;
	
	public void updateContact(Contact contact) throws SQLException, ParseException;
	
	public Contact getActiveContactObject(String nickName, String password) throws SQLException;
	
	void printAllContacts() throws SQLException;
	
	public Contact getContactObject(int ContactID) throws SQLException;
	
	public void searchForContact() throws SQLException;
	
	public void deleteContact(int contactID, Contact activeContact) throws SQLException;
	
	public void deleteContact(Contact contact, Contact activeContact) throws SQLException;

}