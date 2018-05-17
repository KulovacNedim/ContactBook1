package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Contact;
import DTO.Group;

public interface GroupsDAOInterface {
	
	public void addGroup(Contact contact) throws SQLException;

	public void setGroup(Contact contact) throws SQLException;

	public ArrayList<Group> getContactsGroups(int contactID) throws SQLException;
}
