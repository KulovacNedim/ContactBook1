package DTO;

public class Mail {

	private int recordID;
	private int contactID;
	private int mailID;
	private String mailName;
	private String mailAddress;

	public Mail() {

	}

	public Mail(int recordID, int contactID, int mailID, String mailName, String mailAddress) {
		this.recordID = recordID;
		this.contactID = contactID;
		this.mailID = mailID;
		this.mailName = mailName;
		this.mailAddress = mailAddress;
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

	public int getMailID() {
		return mailID;
	}

	public void setMailID(int mailID) {
		this.mailID = mailID;
	}

	public String getMailName() {
		return mailName;
	}

	public void setMailName(String mailName) {
		this.mailName = mailName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	@Override
	public String toString() {
		return "\n\t\t  RECORD ID:      " + recordID + "\n\t\t  CONTACT ID:     " + contactID + "\n\t\t  e-mail ID:      " + mailID + " ("
				+ (this.mailID == 1 ? "Personal" : "Bussiness") + ") " + "\n\t\t  e-mail Name:    " + mailName
				+ "\n\t\t  e-mail ADDRESS: " + mailAddress + "\n";
	}

}
