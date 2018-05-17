package DTO;

public class Phone {

	private int recordID;
	private int contactID;
	private int phoneID;
	private String phoneName;
	private String phoneNumber;
	
	public Phone() {
		
	}
	
	public Phone (int recordID, int contactID, int phoneID, String phoneName, String phoneNumber) {
		this.recordID = recordID;
		this.contactID = contactID;
		this.phoneID = phoneID;
		this.phoneName = phoneName;
		this.phoneNumber = phoneNumber;
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

	public int getPhoneID() {
		return phoneID;
	}

	public void setPhoneID(int phoneID) {
		this.phoneID = phoneID;
	}

	public String getPhoneName() {
		return phoneName;
	}

	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "\n\t\t  RECORD ID:      " + recordID +"\n\t\t  CONTACT ID:     " + contactID + "\n\t\t  PHONE ID:       " + phoneID + " (" + (this.phoneID ==1 ? "Personal" : "Bussiness")  + ") " + "\n\t\t  PHONE NAME:     " + phoneName + "\n\t\t  PHONE NUMBER:   "
				+ phoneNumber + "\n";
	}
	
}
