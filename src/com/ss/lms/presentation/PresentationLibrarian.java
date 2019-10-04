package com.ss.lms.presentation;

import java.sql.SQLException;
import java.util.ArrayList;
//import java.sql.SQLException;
//import java.util.Arrays;
import java.util.Scanner;
//import java.util.stream.Stream;

import com.ss.lms.dataaccess.BookCopyDataAccess;
import com.ss.lms.dataaccess.BookDataAccess;
import com.ss.lms.dataaccess.DataAccess;
import com.ss.lms.dataaccess.LibraryBranchDataAccess;
import com.ss.lms.entity.*;
import com.ss.lms.service.*;

public class PresentationLibrarian extends Presentation {

	private static Scanner scanner;

	public PresentationLibrarian() throws SQLException, ClassNotFoundException {
		super(new UserLibrarian(new BookDataAccess(), new LibraryBranchDataAccess(), new BookCopyDataAccess()));
		
		//scanner = new Scanner( System.in );
		
		//menu();
		
	}
	//
	//Displays librarians choices and gets the user input
	public void menu() {
		while(true) {
			System.out.println("\n\nLibrarian Menu.");
			System.out.println("1. Enter a branch you manage");
			System.out.println("2. Quit to previous");
			int input = getIntegerFieldFromUser("Selection");
			boolean check = false;
			while (check == false) {
				switch(input) {
				case 1:
					//TODO service layer get branches
					branches();
					check = true;
					break;
				case 2:
					return;
				default:
					System.out.println("Enter a valid choice.");
				}
			}
		}
	}
	
	//branches() will allow the user to select which branch they want to interact with
	public void branches(){
		while(true) {
			ArrayList<LibraryBranch> branches;
			System.out.println("Choose your branch:");
			
			int i = 1;
			branches = librarian.readLibraryBranch(new LibraryBranch(-1,"%","%"));
			for(LibraryBranch branch : branches) {
				System.out.println(i + ") " + branch.getBranchName() + ", " + branch.getBranchAddress());
				i++;
			}
			System.out.println(i + ") Quit to previous");
//			Stream<String> branchStream = Arrays.stream(branches);
//			branchStream.forEach((str) -> System.out.println(str);
			

			System.out.println("Enter your branch:");
			int branchId = getIntegerFieldFromUser("Branch ID");
			
			super.scanner.nextLine();
			if(branchId == i) {
				return;
			}
			for(LibraryBranch branch : branches)
			{
				if(branch.getBranchId() == branchId) {
					branchOptions(branch);
				}
			}
			return;
		}
	}
	
	//branchOptions will allow the user to select if they wish to update the details of the library
	//	or add copies of Book to the Branch
	public void branchOptions(LibraryBranch branch) {
		while(true) {
			System.out.println("1) Update the details of the Library");
			System.out.println("2) Add copies of Book to the Branch");
			System.out.println("3) Quit to previous");
			boolean check = false;
			while (check == false) {
				int input = getIntegerFieldFromUser("Selection");
				switch(input) {
				case 1:
					branchUpdate(branch);
					check = true;
					break;
				case 2:
					copies(branch);
					check = true;
					break;
				case 3:
					return;
				default:
					System.out.println("Invalid input.");
				}
			}
		}
	}
	
	//branchUpdate() takes in the selected branch entity
	//The user can then input changes they want to make and it will call on the service layer to update those changes
	public void branchUpdate(LibraryBranch branch) {
		System.out.println("You have chosen to update the Branch with Branch Id: " + branch.getBranchId() + " and Branch Name: " + branch.getBranchName());
		System.out.println("Enter 'quit' at any prompt to cancel operation.");
		System.out.println("Please enter new branch name or enter N/A for no change:");
		String input = getStringFieldFromUser("branch name");
		if("quit".equals(input)) {
			return;
		}
		System.out.println("Please enter new branch address or enter N/A for no change:");
		String input2 = getStringFieldFromUser("branch name");
		if("quit".equals(input)) {
			return;
		}
		
		branch.setBranchName(input);
		branch.setBranchAddress(input2);
//		if(!"N/A".equals(name) && !"N/A".equals(address)) {
		System.out.println("Updating Name and Address.");
		librarian.updateLibraryBranch(branch);
		
		System.out.println("Update Successful");
	}
	
	public void copies(LibraryBranch branch) {
		System.out.println("Pick the Book you want to add copies of, to your branch:");
		while(true) {
			Author author = new Author(-1, "%");
			Publisher publisher = new Publisher(-1, "%", "%", "%");
			Book book = new Book(-1,"%",author, publisher);
			BookCopy bookCopy = new BookCopy(book ,branch, -1);
			ArrayList<BookCopy> copies = librarian.readBookCopy(bookCopy);
			
			//Choosing which book you want to add copies of
			System.out.println("Choose your Book:");
			int i = 1;
			for(BookCopy copy : copies) {
				System.out.println(i + ") " + copy.getBook().getTitle() + " by " + copy.getBook().getAuthor().getAuthorName());
				i++;
			}
			System.out.println(i + ") Quit to previous");
			System.out.println("Enter your book:");
			
			//Getting a valid integer book ID
			int bookId = 0;
			while(!super.scanner.hasNextInt()) {
				System.out.println("Please enter a valid Integer.");
				System.out.print("Enter your book: ");
			    super.scanner.next();
			}
			bookId = super.scanner.nextInt();
			super.scanner.nextLine();
			
			//Creating a book with the information given to pass the supporting functions

			//Checking if the entered value is the quit option
			if(bookId == i) {
				return;
			}
			//If the entered value is within the available id's then it will go on to add copies
//			if(bookId <= copies.size()) {
//				addCopies(copies.get(bookId-1), branchId);
//			}
		}
	}
	
	//addCopies gets the new number of copies desired and calls the service to update the database
	public void addCopies(Book book, int branchId) {
//		System.out.println("Existing number of books: " + librarian.getNumberOfCopies(book, branchId));
//		System.out.println("Enter new number of copies: ");
//		int numCopies= 0;
//		//Gets a valid integer for the new number of copies
//		while(!scanner.hasNextInt()) {
//			System.out.println("Please enter a valid Integer.");
//			System.out.print("Enter your book: ");
//		    scanner.next();
//		}
//
//		numCopies = scanner.nextInt();
//		scanner.nextLine();
//		librarian.changeCopies(book, branchId, numCopies);
//		
	}
	
}
