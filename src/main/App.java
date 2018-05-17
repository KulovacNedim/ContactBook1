package main;

import java.sql.SQLException;

import utils.MenusAndHeaders;

public class App {

	public static void main(String[] args) throws SQLException {

		MenusAndHeaders menusAndHeaders = new MenusAndHeaders();
		
		menusAndHeaders.PrintWelcomeScrean();
	}

}
