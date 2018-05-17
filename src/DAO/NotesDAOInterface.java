package DAO;

import java.sql.SQLException;
import DTO.Contact;

public interface NotesDAOInterface {

	public void addNote(Contact contact) throws SQLException;

	public void deleteNote(Contact contact) throws SQLException;

	public void markNoteAsRead(Contact contact) throws SQLException;
	
	public void printNotes(Contact contact) throws SQLException;

}
