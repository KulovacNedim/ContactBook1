package validation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerValidation {

	static Scanner sc = new Scanner(System.in);

	public static int getInt() {

		int integer = -1;

		try {
			integer = sc.nextInt();
		} catch (InputMismatchException ex) {
			System.out.println("\nInteger expected! Try again.\n");
		}
		return integer;
	}

	public static String getString() {

		boolean exitLoop = false;
		String nString = "";
		while (!exitLoop) {

			String string = sc.nextLine();

			for (int i = 0; i < string.length(); i++) {
				if (Character.isLetterOrDigit(string.charAt(i)) || string.charAt(i) == ' ') {
					nString = string;
					exitLoop = true;
				} else {
					System.out.print("Input cannot contain anything but letters or digits. Try again: ");
				}
			}
		}
		return nString;
	}

	public static String getAnyString() {

		String string = sc.nextLine();

		return string;
	}
}
