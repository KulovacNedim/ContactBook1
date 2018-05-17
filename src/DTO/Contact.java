package DTO;

import java.util.ArrayList;
import java.util.Date;

public class Contact {

	private int contactID;
	private String firstName;
	private String lastName;
	private String nickName;
	private String password;
	private ArrayList<Phone> phoneNumbers;
	private ArrayList<Mail> mailAdresses;
	private ArrayList<Address> homeAddresses;
	private ArrayList<Group> groups;
	private Date birthdate;
	private ArrayList<Note> notes;
	private String company;

	public Contact() {

	}

	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Phone> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(ArrayList<Phone> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public ArrayList<Mail> getMailAdresses() {
		return mailAdresses;
	}

	public void setMailAdresses(ArrayList<Mail> mailAdresses) {
		this.mailAdresses = mailAdresses;
	}

	public ArrayList<Address> getHomeAddresses() {
		return homeAddresses;
	}

	public void setHomeAddresses(ArrayList<Address> homeAddresses) {
		this.homeAddresses = homeAddresses;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "CONTACT ID:       " + contactID + "\nFIRST NAME:       " + firstName + "\nLAST NAME:        " + lastName
				+ "\nNICK NAME:        " + nickName + "\nPASSWORD:         " + password + "\nPHONE NUMBERS:    "
				+ phoneNumbers.toString().substring(1, phoneNumbers.toString().length() - 1) + "\nE-MAIL ADDRESSES: "
				+ mailAdresses.toString().substring(1, mailAdresses.toString().length() - 1) + "\nHOME ADDRESSES:   "
				+ homeAddresses.toString().substring(1, homeAddresses.toString().length() - 1) 
//				+ "\nGROUP:            " + groups 
				+ "\nBIRTHDATE:        " + birthdate + "\nNOTES:            "
				+ notes.toString().substring(1, notes.toString().length() - 1) + "\nCOMPANY:          " + company;
	}

}
