import java.sql.SQLException;
import java.util.Scanner;

import com.ss.lms.presentation.Presentation;
import com.ss.lms.presentation.PresentationAdmin;
import com.ss.lms.presentation.PresentationBorrower;
import com.ss.lms.presentation.PresentationLibrarian;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
	    Presentation pres = null;
		
		while(true) {
			System.out.println("Welcome to the GCIT Library Management System. Which category of a user are you?");
			System.out.println("1. Librarian");
			System.out.println("2. Administrator");
			System.out.println("3. Borrower");
			System.out.println("4. Quit");
			System.out.println("Enter your selection:");
			boolean check = false;
			while(check == false) {
				String input = scanner.nextLine();
				
				switch(input) {
				case "1":
					pres = new PresentationLibrarian();
					check = true;
					break;
				case "2":
					pres = new PresentationAdmin();
					check = true;
					break;
				case "3":
					pres = new PresentationBorrower();
					check = true;
					break;
				case "4":
					scanner.close();
					return;
				default:
					System.out.println("Enter a valid choice.");
				}
			}
		}
	}

}

