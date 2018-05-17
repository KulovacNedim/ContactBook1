package utils;

import java.sql.SQLException;
import java.text.ParseException;

import DAO.ContactDAOImplementation;
import DTO.Contact;
import validation.LoginValidation;
import validation.ScannerValidation;

public class Login {

	LoginValidation loginValidation = new LoginValidation();
	MenusAndHeaders menus =  new MenusAndHeaders();
	ContactDAOImplementation contactDAO = new ContactDAOImplementation();
	
	public void login() throws SQLException, ParseException {

		System.out.print("\nEnter your nickname: ");
		String nickName = ScannerValidation.getString();

		System.out.print("Enter your password: ");
		String password = ScannerValidation.getString();

		
		if (loginValidation.validationLogin(nickName, password)) {

			System.out.println("\nLOGIN SUCCESFULL!");
			
			Contact activeContact = new Contact();
			activeContact = contactDAO.getActiveContactObject(nickName, password);

			menus.printMainMenuOptions(activeContact);;

		} else {

			System.out.println("\nWRONG NICK/PASSWORD COMBINATION! TRY AGAIN.");
			
			menus.printLoginRegisterOptions();
		}

	}

}
