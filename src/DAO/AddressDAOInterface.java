package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Contact;
import DTO.Address;

public interface AddressDAOInterface {

	public ArrayList<Address> getContactsAddresses(int contactID) throws SQLException;

	public void updateContactsAddress(int contactID) throws SQLException;

	public void deleteContactsAddress(Contact contact) throws SQLException;

	public void addAddressToContact(Contact contact) throws SQLException;
	
	public void printContactsAddresses(int contactID) throws SQLException;

}
