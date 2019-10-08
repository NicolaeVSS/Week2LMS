package com.ss.lms.presentation;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ss.lms.dataaccess.BookCopyDataAccess;
import com.ss.lms.dataaccess.BookDataAccess;
import com.ss.lms.dataaccess.LibraryBranchDataAccess;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;
import com.ss.lms.service.UserLibrarian;

public class PresentationLibrarian extends Presentation {

	public PresentationLibrarian() throws SQLException, ClassNotFoundException {
		super(new UserLibrarian(new BookDataAccess(), new LibraryBranchDataAccess(), new BookCopyDataAccess()));
		
		//scanner = new Scanner( System.in );
		
		//menu();
	}
	//Displays librarians choices and gets the user input
	public void menu() {
		while(true) {
			System.out.println("\n\n/*MAIN > LIBRARIAN *************************************************************************************");
			System.out.println("Librarian Menu.");
			System.out.println("1. Enter a branch you manage");
			System.out.println("2. Quit to previous");
			int input = getIntegerFieldFromUser("Selection");
			boolean check = false;
			while (check == false) {
				switch(input) {
				case 1:
					branches();
					check = true;
					break;
				case 2:
					return;
				case Integer.MIN_VALUE:
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
			System.out.println("/*MAIN > LIBRARIAN > BRANCH **************************************************************************");
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
			//super.scanner.nextLine();
			boolean check = true;
			while(check == true) {
				int branchId = getIntegerFieldFromUser("Branch");
				
				//super.scanner.nextLine();
				if(branchId == i || branchId == Integer.MIN_VALUE) {
					return;
				}
				if(branchId < i && branchId > 0) {
					branchOptions(branches.get(branchId-1));
					check = false;
				}
				
			}
		}
	}
	
	//branchOptions will allow the user to select if they wish to update the details of the library
	//	or add copies of Book to the Branch
	public void branchOptions(LibraryBranch branch) {
		while(true) {
			System.out.println("/*MAIN > LIBRARIAN > BRANCH > OPTIONS ***************************************************************");
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
				case Integer.MIN_VALUE:
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
		System.out.println("/*MAIN > LIBRARIAN > BRANCH > OPTIONS > UPDATE ******************************************************");
		System.out.println("You have chosen to update the Branch with Branch Id: " + branch.getBranchId() + " and Branch Name: " + branch.getBranchName());
		System.out.println("Please enter new branch name or enter N/A for no change:");
		String input = getStringFieldFromUser("branch name");
		if("quit".equals(input)) {
			return;
		}
		System.out.println("Please enter new branch address or enter N/A for no change:");
		String input2 = getStringFieldFromUser("branch address");
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
		System.out.println("/*MAIN > LIBRARIAN > BRANCH > OPTIONS > COPIES *********************************************************");

		System.out.println("Pick the Book you want to add copies of, to your branch:");
		while(true) {
			Author author = new Author(-1, "%");
			Publisher publisher = new Publisher(-1, "%", "%", "%");
			Book book = new Book(-1,"%",author, publisher);
			ArrayList<Book> allBooks = librarian.readBook(book);

			
			//Choosing which book you want to add copies of
			System.out.println("Choose your Book:");
			int i = 1;
			for(Book aBook : allBooks) {
				System.out.println(i + ") " + aBook.getTitle() + " by " + aBook.getAuthor().getAuthorName());
				i++;
			}
			System.out.println(i + ") Quit to previous");
			System.out.println("Enter your book:");
			
			//Getting a valid integer book ID
			int bookId = getIntegerFieldFromUser("Book");
			//Creating a book with the information given to pass the supporting functions

			//Checking if the entered value is the quit option
			if(bookId == i || bookId == Integer.MIN_VALUE) {
				return;
			}
			//If the entered value is within the available id's then it will go on to add copies
			if(bookId <= allBooks.size()) {
//				System.out.println("Book id: " + allBooks.get(bookId -1).getBookId());
//				System.out.println("Branch id: " + branch.getBranchId());
				addCopies(allBooks.get(bookId-1), branch);
			}
		}
	}
	
	//addCopies gets the new number of copies desired and calls the service to update the database
	public void addCopies(Book book, LibraryBranch branch) {
		System.out.println("/*MAIN > LIBRARIAN > BRANCH > OPTIONS > COPIES > UPDATE **************************************************");
		BookCopy bookCopy = new BookCopy(book, branch, -1);
		ArrayList<BookCopy> bookCopies = librarian.readBookCopy(bookCopy);
		int noOfCopies;
		if(bookCopies.size() == 0) {
			noOfCopies = 0;
			System.out.println("Existing number of books: 0");
		}
		else {
			noOfCopies = librarian.readBookCopy(bookCopy).get(0).getNoOfCopies();
			System.out.println("Existing number of books: " + noOfCopies);
		}
		int numCopies = getIntegerFieldFromUser("Number of Books");
		if(numCopies == Integer.MIN_VALUE || numCopies < 0) {
			return;
		}
		if(numCopies == 0) {
			System.out.println("Deleting bookCopy.");
			librarian.deleteBookCopy(bookCopy);
		}
		else if (bookCopies.size() > 0) {
			System.out.println("Updating bookCopy.");
			bookCopy.setNoOfCopies(numCopies);
			librarian.updateBookCopy(bookCopy);
		}
		else {
			System.out.println("Creating bookCopy.");
			bookCopy.setNoOfCopies(numCopies);
			librarian.createBookCopy(bookCopy);
		}
		
	}
	
}
