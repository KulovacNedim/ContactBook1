package DAO;

import java.sql.SQLException;
import java.util.SortedMap;

import DTO.Contact;

public interface EmailTypesDAOInterface {

	public SortedMap<Integer, String> getAllEmailTypes() throws SQLException;

	public void printAllEmailTypes() throws SQLException;
	
	public void addEmailType() throws SQLException;
	
	public void editEmailType() throws SQLException;
	
	public void deleteEmailType(Contact activeContact) throws SQLException;

}
