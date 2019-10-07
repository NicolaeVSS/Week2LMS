
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.lms.dataaccess.BookCopyDataAccess;
import com.ss.lms.dataaccess.BookLoanDataAccess;
import com.ss.lms.dataaccess.BorrowerDataAccess;
import com.ss.lms.dataaccess.LibraryBranchDataAccess;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;
import com.ss.lms.service.ServiceBorrower;
import com.ss.lms.service.UserBorrower;

public class PresentationBorrower extends Presentation {
	public PresentationBorrower() throws ClassNotFoundException, SQLException {
		super(new UserBorrower(new LibraryBranchDataAccess(), new BorrowerDataAccess(), new BookCopyDataAccess(),
				new BookLoanDataAccess()));
	}

	@Override
	public void menu() {
		while (true) {
			System.out.println("Enter your card number: ");
			Borrower borr = new Borrower(-1, "%", "%", "%");
			ArrayList<Borrower> borrowers = super.userBorrower.readBorrower(borr);
			int input = super.getIntegerFieldFromUser("CardId");
			for (Borrower bor : borrowers) {
				if (bor.getCardNo() == input) {
					borrowOptions(bor);
				}
			}
			if (input == Integer.MIN_VALUE) {
				return;
			}
		}
	}

	public void borrowOptions(Borrower borrower) {
		while (true) {
			System.out.println("1. Check out a book.");
			System.out.println("2. Return a book.");
			System.out.println("3. Quit to previous");
			int input = super.getIntegerFieldFromUser("Selection");
			switch (input) {
			case 1:
				borrowBranchCheckOut(borrower);
				break;
			case 2:
				borrowReturnBranch(borrower);
				break;
			case 3:
				return;
			case Integer.MIN_VALUE:
				return;
			default:
				System.out.println("Invalid Input.");
			}
		}
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

			// super.scanner.nextLine();

			boolean check = true;

			while (check == true) {

				int branchId = getIntegerFieldFromUser("Branch Selection");

				// super.scanner.nextLine();

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
//		System.out.println(libraryBranch.toString());
//		System.out.println(borrower.toString());
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

			// Creating a book with the information given to pass the supporting functions

			// Checking if the entered value is the quit option

			if (bookId == i || bookId == Integer.MIN_VALUE) {

				return;

			}

			// If the entered value is within the available id's then it will go on to add
			// copies

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

			BookCopy bookCopy = new BookCopy(book, libraryBranch, -1);
			
			ArrayList<BookCopy> allBooks = userBorrower.readBookCopy(new BookCopy(book, libraryBranch,-1));

			// Choosing which book you want to add copies of

			System.out.println("Choose your Book:");

			int i = 1;

			ArrayList<BookCopy> bookCopies = userBorrower.readBookCopy(bookCopy);

			for (BookCopy aBook : allBooks) {

				System.out.println(i + ") " + aBook.getBook().getTitle() + " by " + aBook.getBook().getAuthor().getAuthorName());

				i++;

			}

			System.out.println(i + ") Quit to previous");

			System.out.println("Enter your book:");

			// Getting a valid integer book ID

			int bookId = getIntegerFieldFromUser("Book");

			// Creating a book with the information given to pass the supporting functions

			// Checking if the entered value is the quit option

			if (bookId == i || bookId == Integer.MIN_VALUE) {

				return;

			}

			// If the entered value is within the available id's then it will go on to add
			// copies

			if (bookId <= allBooks.size()) {

				BookLoan bookLoan = new BookLoan(allBooks.get(bookId-1).getBook(), libraryBranch, borrower,
						Date.valueOf("0001-01-01"), Date.valueOf("0001-01-01"));

				userBorrower.createBookLoan(bookLoan);

			}

		}

	}

}