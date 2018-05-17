package DAO;

import java.sql.SQLException;
import java.util.SortedMap;

import DTO.Contact;

public interface GroupTypesDAOInterface {

	public SortedMap<Integer, String> getAllGroupTypes() throws SQLException;

	public void printAllGroupTypes() throws SQLException;
	
	public void addGroup() throws SQLException;
	
	public void editGroup() throws SQLException;
	
	public void changeGroupForContacts(Contact activeContact) throws SQLException;

}
