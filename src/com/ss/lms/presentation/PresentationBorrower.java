package com.ss.lms.presentation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.lms.dataaccess.*;
import com.ss.lms.entity.*;
import com.ss.lms.service.UserBorrower;

public class PresentationBorrower extends Presentation {

	public PresentationBorrower(ServiceBorrower borrower) {
		super(borrower);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void menu() {
		// TODO Auto-generated method stub

	}

	public void borrowBranchCheckOut(Borrower borrower) {
		while (true) {
			ArrayList<LibraryBranch> branches;
			System.out.println("Pick the Branch you want to check out from: ");
			int i = 1;
			branches = userBorrower.readLibraryBranch(new LibraryBranch(-1, "%", "%"));
			for (LibraryBranch branch : branches) {
				System.out.println(i + ") " + branch.getBranchName() + ", " + branch.getBranchAddress());
				i++;
			}
			System.out.println(i + ") Quit to previous");
			System.out.println("Enter your branch:");
			// super.scanner.nextLine();
			boolean check = true;
			while (check == true) {
				int branchId = getIntegerFieldFromUser("Branch Selection");
				// super.scanner.nextLine();
				if (branchId == i || branchId == Integer.MIN_VALUE) {
					return;
				}
				if (branchId < i && branchId > 0) {
					borrowBookCheckOut(borrower, branches.get(0));
					check = false;
				}
			}
		}
	}

	public void borrowReturnBranch(Borrower borrower) {

		while (true) {
			ArrayList<LibraryBranch> branches;
			System.out.println("Pick the Branch you want to return a book to: ");

			int i = 1;
			branches = userBorrower.readLibraryBranch(new LibraryBranch(-1, "%", "%"));

			for (LibraryBranch branch : branches) {
				System.out.println(i + ") " + branch.getBranchName() + ", " + branch.getBranchAddress());
				i++;
			}
			System.out.println(i + ") Quit to previous");
			System.out.println("Enter your branch:");
			boolean check = true;

			while (check == true) {
				int branchId = getIntegerFieldFromUser("Branch Selection");
				if (branchId == i || branchId == Integer.MIN_VALUE) {
					return;
				}
				if (branchId < i && branchId > 0) {
					borrowReturnBook(borrower, branches.get(0));
					check = false;
				}
			}
		}
	}

	public void borrowReturnBook(Borrower borrower, LibraryBranch libraryBranch) {
		System.out.println("Pick the book you want to return: ");
		while (true) {
			Author author = new Author(-1, "%");
			Publisher publisher = new Publisher(-1, "%", "%", "%");
			Book book = new Book(-1, "%", author, publisher);
			BookLoan bookLoan = new BookLoan(book, libraryBranch, borrower, Date.valueOf("0001-01-01"),
					Date.valueOf("0001-01-01"));

			ArrayList<BookLoan> bookLoans = userBorrower.readBookLoan(bookLoan);
			int i = 1;
			for (BookLoan bookL : bookLoans) {
				System.out.println(
						i + ") " + bookL.getBook().getTitle() + " by " + bookL.getBook().getAuthor().getAuthorName());
				i++;
			}
			System.out.println(i + ") Quit to previous");
			System.out.println("Enter your book:");
			
			// Getting a valid integer book ID
			int bookId = getIntegerFieldFromUser("Book");
			/* Creating a book with the information given to pass the supporting functions
			 * Checking if the entered value is the quit option
			 * */
			if (bookId == i || bookId == Integer.MIN_VALUE) {
				return;
			}

			/* If the entered value is within the available id's then it will go on to add
			 * copies
			  **/
			if (bookId <= bookLoans.size()) {
				userBorrower.returnBookLoan(bookLoans.get(bookId-1));
			}
		}
	}

	public void borrowBookCheckOut(Borrower borrower, LibraryBranch libraryBranch) {
		System.out.println("Pick the Book you want to check out:");
		while (true) {
			Author author = new Author(-1, "%");
			Publisher publisher = new Publisher(-1, "%", "%", "%");
			Book book = new Book(-1, "%", author, publisher);
			
			ArrayList<BookCopy> allBooks = userBorrower.readBookCopy(new BookCopy(book, libraryBranch,-1));
			// Choosing which book you want to add copies of
			System.out.println("Choose your Book:");
			int i = 1;
			
			for (BookCopy aBook : allBooks) {
				System.out.println(i + ") " + aBook.getBook().getTitle() + " by " + aBook.getBook().getAuthor().getAuthorName());
				i++;
			}
			System.out.println(i + ") Quit to previous");
			System.out.println("Enter your book:");

			// Getting a valid integer book ID

			int bookId = getIntegerFieldFromUser("Book");
			/* Creating a book with the information given to pass the supporting functions
			* Checking if the entered value is the quit option
			*/ 
			if (bookId == i || bookId == Integer.MIN_VALUE) {
				return;
			}
			/* If the entered value is within the available id's then it will go on to add
			 * copies
			 */
			if (bookId <= allBooks.size()) {
				BookLoan bookLoan = new BookLoan(allBooks.get(bookId-1).getBook(), libraryBranch, borrower,
						Date.valueOf("0001-01-01"), Date.valueOf("0001-01-01"));
				userBorrower.createBookLoan(bookLoan);
			}		
		}
	}

}
