package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Contact;
import DTO.Phone;;

public interface PhoneDAOInterface {

	public ArrayList<Phone> getContactsPhones(int contactID) throws SQLException;

	public void updatePhoneTableInfo(int contactID) throws SQLException;

	public void deleteContactsPhone(Contact contact) throws SQLException;

	public void addPhoneTableInfo(Contact contact) throws SQLException;
	
	public void printContactsPhones(int contactID) throws SQLException;

}
