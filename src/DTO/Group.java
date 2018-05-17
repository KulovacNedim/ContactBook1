package DTO;

public class Group {

	private int recordID;
	private int activeContactID; // Logged in contact
	private int contactID;
	private int groupID;

	public Group() {

	}

	public Group(int recordID, int activeContactID, int contactID, int groupID) {
		this.recordID = recordID;
		this.activeContactID = activeContactID;
		this.contactID = contactID;
		this.groupID = groupID;
	}

	public int getRecordID() {
		return recordID;
	}

	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}

	public int getActiveContactID() {
		return activeContactID;
	}

	public void setActiveContactID(int activeContactID) {
		this.activeContactID = activeContactID;
	}

	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	@Override
	public String toString() {
		return "Group [recordID=" + recordID + ", activeContactID=" + activeContactID + ", contactID=" + contactID
				+ ", groupID=" + groupID + "]";
	}

}
