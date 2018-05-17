package DTO;

public class Note {

	private int recordID;
	private int contactID;
	private String note;
	private boolean archived;

	public Note() {

	}

	public Note(int recordID, int contactID, String note, boolean archived) {
		this.recordID = recordID;
		this.contactID = contactID;
		this.note = note;
		this.archived = archived;
	}

	public int getRecordID() {
		return recordID;
	}

	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}

	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	@Override
	public String toString() {
		return "\n\t\t  RECORD ID:      " + contactID + "\n\t\t  CONTACT ID:     " + contactID + "\n\t\t  NOTE:           " + note + "\n\t\t  ARCHIVED:       "
				+ archived + "\n";
	}

}
