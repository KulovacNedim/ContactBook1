package DTO;

public class Address {

	private int recordID;
	private int contactID;
	private int addressID;
	private String state;
	private String city;
	private String street;
	private String zip;

	public Address() {

	}

	public Address(int recordID, int contactID, int addressID, String state, String city, String street, String zip) {
		this.recordID = recordID;
		this.contactID = contactID;
		this.addressID = addressID;
		this.state = state;
		this.city = city;
		this.street = street;
		this.zip = zip;
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

	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Override
	public String toString() {
		return "\n\t\t  RECORD ID:      " + recordID + "\n\t\t  CONTACT ID:     " + contactID + "\n\t\t  ADDRESS ID:     " + addressID + " ("
				+ (this.addressID == 1 ? "Personal" : "Bussiness") + ") " + "\n\t\t  STATE:          " + state
				+ "\n\t\t  CITY:           " + city + "\n\t\t  STREET:         " + street + "\n\t\t  ZIP:            "
				+ zip + "\n";
	}
}
