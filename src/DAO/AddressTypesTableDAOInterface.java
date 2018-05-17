package DAO;

import java.sql.SQLException;
import java.util.SortedMap;

import DTO.Contact;

public interface AddressTypesTableDAOInterface {

	public SortedMap<Integer, String> getAllAddressTypes() throws SQLException;

	public void printAllAddressTypes() throws SQLException;
	
	public void addAddressType() throws SQLException;
	
	public void editAddressType() throws SQLException;
	
	public void deleteAddressType(Contact activeContact) throws SQLException;

}
