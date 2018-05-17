package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import DAO.AddressDAOImplementation;
import DAO.AddressTypesDAOImplementation;
import DAO.BirthdateDAOImplementation;
import DAO.CompanyDAOImplementation;
import DAO.ConnectionManager;
import DAO.ContactDAOImplementation;
import DAO.EmailDAOImplementation;
import DAO.EmailTypesDAOImplementation;
import DAO.GroupsDAOImplementation;
import DAO.GroupsTypesDAOImplementation;
import DAO.NotesDAOImplementation;
import DAO.PhoneDAOImplementation;
import DAO.PhoneTypesDAOImplementation;
import DTO.Contact;
import validation.LoginValidation;
import validation.ScannerValidation;

public class MenusAndHeaders {

	LoginValidation loginValidation = new LoginValidation();
	ContactDAOImplementation contactDAO = new ContactDAOImplementation();
	Connection connection = ConnectionManager.getInstance().getConnection();

	public void PrintWelcomeScrean() throws SQLException {

		System.out.println("*****************************************************************");
		System.out.println("            WELCOME TO CONTACT BOOK APLICATION                   ");
		System.out.println("*****************************************************************");

		printLoginRegisterOptions();
		System.out.println();
	}

	public void printLoginRegisterOptions() throws SQLException {

		Login login = new Login();

		System.out.println("\nSelect option: \n");
		System.out.println("\t 1) LOGIN");
		System.out.println("\t 2) REGISTER");

		System.out.print("\nEnter your choice: ");

		boolean exitLoop = false;

		while (!exitLoop) {

			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				try {
					login.login();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				exitLoop = true;
				break;
			case 2:
				registerNewContact();

				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");
					System.out.println("*****************************************************************\n");

					printLoginRegisterOptions();
				} else {
					ScannerValidation.getString();
					System.out.println("*****************************************************************\n");
					printLoginRegisterOptions();
				}

			}
		}
	}

	private void registerNewContact() throws SQLException {

		System.out.println("\n*****************************************************************");
		System.out.println("                    REGISTER NEW CONTACT                         ");
		System.out.println("*****************************************************************");

		contactDAO.addContact();

		System.out.println("\n*****************************************************************");

		printLoginRegisterOptions();

	}

	public void printMainMenuOptions(Contact activeContact) throws SQLException, ParseException {

		System.out.println("\n                     HELLO " + activeContact.getFirstName().toUpperCase() + " "
				+ activeContact.getLastName().toUpperCase() + "!");
		System.out.println("\n             WELCOME TO CONTACT BOOK APLICATION!");
		System.out.println("*****************************************************************");

		System.out.println("\nSelect option: \n");

		System.out.println("    1) Edit your contact");
		System.out.println("    2) Search contact");
		System.out.println("    3) List all contacts");
		System.out.println("    4) Add new contact");
		System.out.println("    5) Edit contacts");
		System.out.println("    6) Delete contact");
		System.out.println("    7) Edit settings");
		System.out.println("    8) Log out");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter your choice: ");

			int option = ScannerValidation.getInt();

			System.out.println("*****************************************************************");

			switch (option) {
			case 1:
				System.out.println("\nEDITING YOUR PROFILE SECTION:");
				System.out.println("*****************************************************************");

				System.out.println("\nYour current profile infos: \n");

				System.out.println(activeContact.toString());

				contactDAO.updateContact(activeContact);

				System.out.println("\nRequested updates are done. Going back to main menu...");

				activeContact = contactDAO.getContactObject(activeContact.getContactID());

				printMainMenuOptions(activeContact);

				exitLoop = true;
				break;
			case 2:
				System.out.println("\nSEARCH  FOR CONTACT SECTION:");
				System.out.println("*****************************************************************");

				contactDAO.searchForContact();

				System.out.println("\nRequested operation is done. Going back to main menu...");

				printMainMenuOptions(activeContact);

				exitLoop = true;
				break;
			case 3:
				System.out.println("\nPRINT ALL CONTACTS SECTION:");
				System.out.println("*****************************************************************");
				System.out.println();

				contactDAO.printAllContacts();

				System.out.println("\nRequested operation is done. Going back to main menu...");

				printMainMenuOptions(activeContact);

				exitLoop = true;
				break;
			case 4:
				System.out.println("\nADD NEW CONTACT SECTION:");
				System.out.println("*****************************************************************");

				contactDAO.addContact();

				System.out.println("\nRequested operation is done. Going back to main menu...");

				printMainMenuOptions(activeContact);

				exitLoop = true;
				break;
			case 5:
				System.out.println("\nEDIT CONTACT SECTION:");
				System.out.println("*****************************************************************");

				contactDAO.printAllContacts();

				System.out.print("\nEnter ID of contact you want to edit: ");
				int contactID = ScannerValidation.getInt();

				Contact contact = contactDAO.getContactObject(contactID);

				contactDAO.updateContact(contact);

				System.out.println("\nRequested operation is done. Going back to main menu...");

				printMainMenuOptions(activeContact);

				exitLoop = true;
				break;
			case 6:
				System.out.println("\nDELETE CONTACT SECTION:");
				System.out.println("*****************************************************************");

				contactDAO.printAllContacts();

				System.out.print("\nEnter ID of contact you want to delete: ");
				int contactToDeleteID = ScannerValidation.getInt();

				contactDAO.deleteContact(contactToDeleteID, activeContact);

				System.out.println("\nRequested operation is done. Going back to main menu...");

				printMainMenuOptions(activeContact);

				exitLoop = true;
				break;
			case 7:
				System.out.println("\nEDIT SETTINGS SECTION:");
				System.out.println("*****************************************************************");

				editOptions(activeContact);

				exitLoop = true;
				break;
			case 8:
				activeContact = null;

				System.out.println("Logging out...\n");

				printLoginRegisterOptions();

				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	public void updatePhoneMenu(Contact contact) throws SQLException {

		PhoneDAOImplementation phoneTableDAO = new PhoneDAOImplementation();

		System.out.println("\nDo you want to edit contact\'s phone numbers?");
		System.out.println("    1) Add new phone number");
		System.out.println("    2) Edit one of existing phone number");
		System.out.println("    3) Delete phone number");
		System.out.println("    4) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				phoneTableDAO.addPhoneTableInfo(contact);

				exitLoop = true;
				break;
			case 2:
				phoneTableDAO.updatePhoneTableInfo(contact.getContactID());

				exitLoop = true;
				break;
			case 3:
				phoneTableDAO.deleteContactsPhone(contact);

				exitLoop = true;
				break;
			case 4:
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	public void updateEmailMenu(Contact contact) throws SQLException {

		EmailDAOImplementation emailTableDAO = new EmailDAOImplementation();

		System.out.println("\nDo you want to edit contact\'s e-mail accounts?");
		System.out.println("    1) Add new e-mail account to contact");
		System.out.println("    2) Edit one of existing e-mail accounts");
		System.out.println("    3) Delete one of existing e-mail accounts");
		System.out.println("    4) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				emailTableDAO.addEmailToContact(contact);

				exitLoop = true;
				break;
			case 2:
				emailTableDAO.updateContactsEmail(contact.getContactID());

				exitLoop = true;
				break;
			case 3:
				emailTableDAO.deleteContactsEmail(contact);

				exitLoop = true;
				break;
			case 4:
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	public void updateHomeAddressTable(Contact contact) throws SQLException {

		AddressDAOImplementation addressTableDAO = new AddressDAOImplementation();

		System.out.println("\nDo you want to edit contact\'s home addresses?");
		System.out.println("    1) Add new home address to contact");
		System.out.println("    2) Edit one of existing home addresses");
		System.out.println("    3) Delete one of existing addresses");
		System.out.println("    4) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				addressTableDAO.addAddressToContact(contact);

				exitLoop = true;
				break;
			case 2:
				addressTableDAO.updateContactsAddress(contact.getContactID());

				exitLoop = true;
				break;
			case 3:
				addressTableDAO.deleteContactsAddress(contact);

				exitLoop = true;
				break;
			case 4:
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	public void updateGroupsTable(Contact contact) throws SQLException {

		GroupsDAOImplementation groupsTableDAO = new GroupsDAOImplementation();
		ContactDAOImplementation contactDAO = new ContactDAOImplementation();

		System.out.println("\nDo you want to edit contact\'s groups?");
		System.out.println("    1) Add contact\'s group");
		System.out.println("    2) Edit contact\'s group");
		System.out.println("    3) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				groupsTableDAO.addGroup(contact);

				contact = contactDAO.getContactObject(contact.getContactID());

				exitLoop = true;
				break;
			case 2:
				groupsTableDAO.setGroup(contact);

				contact = contactDAO.getContactObject(contact.getContactID());
				exitLoop = true;
				break;
			case 3:
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	public void updateBirthdateTable(Contact contact) throws SQLException {

		BirthdateDAOImplementation birthdateTableDAO = new BirthdateDAOImplementation();

		System.out.println("\nDo you want to update contact's birthdate? ");
		System.out.println("    1) Yes");
		System.out.println("    2) No");

		boolean exitLoop1 = false;

		while (!exitLoop1) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				birthdateTableDAO.updateContactsBirthadate(contact);

				exitLoop1 = true;
				break;
			case 2:
				exitLoop1 = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");
					System.out.println("*****************************************************************\n");
				} else {
					ScannerValidation.getString();
					System.out.println("*****************************************************************\n");
				}
			}
		}
	}

	public void updateNotesTable(Contact contact) throws SQLException {

		NotesDAOImplementation notesTableDAO = new NotesDAOImplementation();

		System.out.println("\nDo you want to edit contact\'s notes?");
		System.out.println("    1) Add new note to contact");
		System.out.println("    2) Delete one of existing note");
		System.out.println("    3) Mark some notes as archived");
		System.out.println("    4) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				notesTableDAO.addNote(contact);

				exitLoop = true;
				break;
			case 2:
				notesTableDAO.deleteNote(contact);

				exitLoop = true;
				break;
			case 3:
				notesTableDAO.markNoteAsRead(contact);

				exitLoop = true;
				break;
			case 4:
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

					System.out.println("*****************************************************************");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	public void setContactsCompany(Contact contact) throws SQLException {

		CompanyDAOImplementation companyTableDAO = new CompanyDAOImplementation();

		System.out.println("\nDo you want to update contact's company? ");
		System.out.println("    1) Yes");
		System.out.println("    2) No");

		boolean exitLoop1 = false;

		while (!exitLoop1) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				companyTableDAO.updateContactsCompany(contact);
				exitLoop1 = true;
				break;
			case 2:
				exitLoop1 = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");
					System.out.println("*****************************************************************\n");
				} else {
					ScannerValidation.getString();
					System.out.println("*****************************************************************\n");
				}
			}
		}
	}

	private void editOptions(Contact activeContact) throws SQLException {

		System.out.println("\nSelect what do you want to edit: ");
		System.out.println("    1) Phone types");
		System.out.println("    2) e-mail types");
		System.out.println("    3) Address types");
		System.out.println("    4) Groups");
		System.out.println("    5) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {
			System.out.print("\nEnter your choice: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				editPhoneTypes(activeContact);
				editOptions(activeContact);
				exitLoop = true;
				break;
			case 2:
				editEmailTypes(activeContact);
				editOptions(activeContact);
				exitLoop = true;
				break;
			case 3:
				editAddressTypes(activeContact);
				editOptions(activeContact);
				exitLoop = true;
				break;
			case 4:
				editGroups(activeContact);
				editOptions(activeContact);
				exitLoop = true;
				break;
			case 5:
				try {
					System.out.println("\nGoing back to main menu...\n");
					printMainMenuOptions(activeContact);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	private void editPhoneTypes(Contact activeContact) throws SQLException {

		PhoneTypesDAOImplementation phoneTypesDAO = new PhoneTypesDAOImplementation();

		phoneTypesDAO.printAllPhoneTypes();

		System.out.println("\nDo you want to edit phone types?");
		System.out.println("    1) Add new phone type");
		System.out.println("    2) Edit one of existing phone type");
		System.out.println("    3) Delete one of existing phone type");
		System.out.println("    4) Change all group members for active contact");
		System.out.println("    5) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				phoneTypesDAO.addPhoneType();

				exitLoop = true;
				break;
			case 2:
				phoneTypesDAO.editPhoneType();

				exitLoop = true;
				break;
			case 3:
				phoneTypesDAO.deletePhoneType(activeContact);

				exitLoop = true;
				break;
			case 4:
				phoneTypesDAO.changeContactsPhoneTypes();

				exitLoop = true;
				break;
			case 5:
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	private void editEmailTypes(Contact activeContact) throws SQLException {

		EmailTypesDAOImplementation emailTypesDAO = new EmailTypesDAOImplementation();

		emailTypesDAO.printAllEmailTypes();

		System.out.println("\nDo you want to edit e-mail types?");
		System.out.println("    1) Add new e-mail type");
		System.out.println("    2) Edit one of existing e-mail type");
		System.out.println("    3) Delete one of existing e-mail type");
		System.out.println("    4) Change all group members for active contact");
		System.out.println("    5) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				emailTypesDAO.addEmailType();

				exitLoop = true;
				break;
			case 2:
				emailTypesDAO.editEmailType();

				exitLoop = true;
				break;
			case 3:
				emailTypesDAO.deleteEmailType(activeContact);

				exitLoop = true;
				break;
			case 4:
				emailTypesDAO.changeContactsEmailTypes();

				exitLoop = true;
				break;
			case 5:
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	private void editAddressTypes(Contact activeContact) throws SQLException {

		AddressTypesDAOImplementation addressTypesDAO = new AddressTypesDAOImplementation();

		addressTypesDAO.printAllAddressTypes();

		System.out.println("\nDo you want to edit address types?");
		System.out.println("    1) Add new address type");
		System.out.println("    2) Edit one of existing address type");
		System.out.println("    3) Delete one of existing address type");
		System.out.println("    4) Change all group members for active contact");
		System.out.println("    5) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				addressTypesDAO.addAddressType();

				exitLoop = true;
				break;
			case 2:
				addressTypesDAO.editAddressType();

				exitLoop = true;
				break;
			case 3:
				addressTypesDAO.deleteAddressType(activeContact);

				exitLoop = true;
				break;
			case 4:
				addressTypesDAO.changeContactsAddressTypes();

				exitLoop = true;
				break;
			case 5:
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}

	private void editGroups(Contact activeContact) throws SQLException {

		GroupsTypesDAOImplementation groupsTypesDAO = new GroupsTypesDAOImplementation();

		groupsTypesDAO.printAllGroupTypes();

		System.out.println("\nDo you want to edit contact groups?");
		System.out.println("    1) Add new group");
		System.out.println("    2) Edit one of existing group");
		System.out.println("    3) Change your contact\'s group for all members in group");
		System.out.println("    4) Cancel");

		boolean exitLoop = false;

		while (!exitLoop) {

			System.out.print("\nEnter option: ");
			int option = ScannerValidation.getInt();

			switch (option) {
			case 1:
				groupsTypesDAO.addGroup();

				exitLoop = true;
				break;
			case 2:
				groupsTypesDAO.editGroup();

				exitLoop = true;
				break;
			case 3:
				groupsTypesDAO.changeGroupForContacts(activeContact);

				exitLoop = true;
				break;
			case 4:
				exitLoop = true;
				break;
			default:
				if (option != -1) {
					System.out.println("\nThere is no such option like " + option + ". Try again.\n");

				} else {
					ScannerValidation.getString();
				}
			}
		}
	}
}
