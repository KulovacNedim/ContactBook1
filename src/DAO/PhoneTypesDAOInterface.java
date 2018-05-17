package DAO;

import java.sql.SQLException;
import java.util.SortedMap;

import DTO.Contact;

public interface PhoneTypesDAOInterface {

	public SortedMap<Integer, String> getAllPhoneTypes() throws SQLException;

	public void printAllPhoneTypes() throws SQLException;
	
	public void addPhoneType() throws SQLException;
	
	public void editPhoneType() throws SQLException;
	
	public void deletePhoneType(Contact activeContact) throws SQLException;
	
	public void changeContactsPhoneTypes() throws SQLException;

}
