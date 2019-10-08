package com.ss.lms.presentation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.ss.lms.dataaccess.AuthorDataAccess;
import com.ss.lms.dataaccess.BookDataAccess;
import com.ss.lms.dataaccess.BookLoanDataAccess;
import com.ss.lms.dataaccess.BorrowerDataAccess;
import com.ss.lms.dataaccess.LibraryBranchDataAccess;
import com.ss.lms.dataaccess.PublisherDataAccess;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;
import com.ss.lms.service.UserAdmin;

public class PresentationAdmin extends Presentation
{
	Scanner scanner;
	
	public PresentationAdmin() throws ClassNotFoundException, SQLException 
	{
		super(new UserAdmin(
				new AuthorDataAccess(), new PublisherDataAccess(), new BookDataAccess(),
				new LibraryBranchDataAccess(), new BorrowerDataAccess(), new BookLoanDataAccess()
				));
	}
	
	public void menu() 
	{
		/*
		 * BOILER PLATE CODE AHEAD:
		 * these objects are used purely as input to DAO find functions which will return the entire contents of the table
		 * the state of these objects is still very mutable, final in this case only stops the reference from changing
		 * */
		final Author findAllAuthors = new Author(-1, "%");
		final Publisher findAllPublishers = new Publisher(-1, "%", "%", "%");
		final Book findAllBooks = new Book(-1, "%", findAllAuthors, findAllPublishers);
		final LibraryBranch findAllLibraryBranches = new LibraryBranch(-1, "%", "%");
		final Borrower findAllBorrowers = new Borrower(-1, "%", "%", "%");
		final BookLoan findAllBookLoans= new BookLoan(findAllBooks, findAllLibraryBranches, findAllBorrowers, Date.valueOf("0001-01-01"), Date.valueOf("0001-01-01"));
		
		do 
		{
			String operation = "";
			String tableSelection = "";
			
			System.out.println("/*MAIN > ADMIN > OPERATIONS************************************************************************************/");
			System.out.println("Select the operation you would like to do.");
			System.out.println("1. Create\n2. Read\n3. Update\n4. Delete\n0. Quit to user selection");
			
			operation = getNextLine();
			
			switch(operation) 
			{
			case "1":// CREATE operation
				System.out.println("/*MAIN > ADMIN > CREATE************************************************************************************/");
				tableSelection = selectTable();
				
				switch(tableSelection) 
				{
				case "1": // Authors table
					System.out.println("/*MAIN > ADMIN > CREATE > AUTHOR************************************************************************************/");
					administrator.readAuthor(findAllAuthors).forEach(row -> System.out.println(row));
					
					Author userAuthor = createEntityAuthor("", false, true);
					
					// the user quit somewhere in the process
					if(userAuthor == null) 
					{
						break;
					}
					
					// TODO consider returning a boolean to let the user know the operation was successful or not
					administrator.createAuthor(userAuthor);
					break;
					
				case "2": // Publishers table
					System.out.println("/*MAIN > ADMIN > CREATE > PUBLISHER************************************************************************************/");
					administrator.readPublisher(findAllPublishers).forEach(row -> System.out.println(row));
					
					Publisher userPublisher = createEntityPublisher("", false, true);

					// the user quit somewhere in the process
					if(userPublisher == null) 
					{
						break;
					}

					// TODO consider returning a boolean to let the user know the operation was successful or not
					administrator.createPublisher(userPublisher);
					break;
					
				case "3": // Books table
					System.out.println("/*MAIN > ADMIN > CREATE > BOOK************************************************************************************/");
					
					System.out.println("\nAuthors Table:");
					administrator.readAuthor(findAllAuthors).forEach(row -> System.out.println(row));
					
					System.out.println("\nPublishers Table:");
					administrator.readPublisher(findAllPublishers).forEach(row -> System.out.println(row));
					
					Book userBook = createEntityBook("", false, true);
					
					// the user quit somewhere in the process
					if(userBook == null) 
					{
						break;
					}
					
					// TODO consider returning a boolean to let the user know the operation was successful or not
					administrator.createBook(userBook);
					break;
				case "4": // Library Branches table
					System.out.println("/*MAIN > ADMIN > CREATE > BRANCH************************************************************************************/");
					administrator.readLibraryBranch(findAllLibraryBranches).forEach(row -> System.out.println(row));
					
					LibraryBranch userLibraryBranch = createEntityLibraryBranch("", false, true);
					
					// the user quit somewhere in the process
					if(userLibraryBranch == null) 
					{
						break;
					}
					
					// TODO consider returning a boolean to let the user know the operation was successful or not
					administrator.createLibraryBranch(userLibraryBranch);
					break;
					
				case "5": // Borrower table
					System.out.println("/*MAIN > ADMIN > CREATE > BORROWER************************************************************************************/");
					administrator.readBorrower(findAllBorrowers).forEach(row -> System.out.println(row));
					
					Borrower userBorrower= createEntityBorrower("", false, true);
					
					// the user quit somewhere in the process
					if(userBorrower == null) 
					{
						break;
					}

					// TODO consider returning a boolean to let the user know the operation was successful or not
					administrator.createBorrower(userBorrower);
					break;
					
				case "6": // Book Loans table
					System.out.println("/*MAIN > ADMIN > CREATE > LOAN************************************************************************************/");
					System.out.println("Admin cannot create a Book Loan.\nQuitting to operation selection");
					break;
					
				case "0": // return to operation select
					continue;
				}
				break; // once operation is done, return to operation select
				
			case "2": // READ operation
				System.out.println("/*MAIN > ADMIN > READ************************************************************************************/");
				tableSelection = selectTable();
				
				switch(tableSelection) 
				{
				case "1": // Authors table
					System.out.println("/*MAIN > ADMIN > READ > AUTHOR************************************************************************************/");
					Author userAuthor = createEntityAuthor("Note: Enter N/A if you aren't concerned with the value of a field", true, true);
					
					// the user quit somewhere in the process
					if(userAuthor == null) 
					{
						break;
					}
					
					administrator.readAuthor(userAuthor).forEach(row -> System.out.println(row));
					break;
					
				case "2": // Publishers table
					System.out.println("Publishers table selected");
					Publisher userPublisher= createEntityPublisher("Note: Enter N/A if you aren't concerned with the value of a field", true, true);
					
					// the user quit somewhere in the process
					if(userPublisher == null) 
					{
						break;
					}
					
					administrator.readPublisher(userPublisher).forEach(row -> System.out.println(row));;
					break;
					
				case "3": // Books table
					System.out.println("/*MAIN > ADMIN > READ > BOOK************************************************************************************/");
					Book userBook = createEntityBook("Note: Enter N/A if you aren't concerned with the value of a field", true, true);
					
					// the user quit somewhere in the process
					if(userBook == null) 
					{
						break;
					}
					
					administrator.readBook(userBook).forEach(row -> System.out.println(row));;
					break;
					
				case "4": // Library Branches table
					System.out.println("/*MAIN > ADMIN > READ > BRANCH************************************************************************************/");
					LibraryBranch userLibraryBranch = createEntityLibraryBranch("Note: Enter N/A if you aren't concerned with the value of a field", true, true);
					
					// the user quit somewhere in the process
					if(userLibraryBranch == null) 
					{
						break;
					}
					
					administrator.readLibraryBranch(userLibraryBranch).forEach(row -> System.out.println(row));
					break;
					
				case "5": // Borrower table
					System.out.println("/*MAIN > ADMIN > READ > BORROWER************************************************************************************/");
					Borrower userBorrower = createEntityBorrower("Note: Enter N/A if you aren't concerned with the value of a field", true, true);
					
					// the user quit somewhere in the process
					if(userBorrower == null) 
					{
						break;
					}
					
					administrator.readBorrower(userBorrower).forEach(row -> System.out.println(row));;
					break;
					
				case "6": // Book Loans table
					System.out.println("/*MAIN > ADMIN > READ > LOAN************************************************************************************/");
					BookLoan userBookLoan= createEntityBookLoan("Note: Enter N/A if you aren't concerned with the value of a field");
					
					// the user quit somewhere in the process
					if(userBookLoan == null) 
					{
						break;
					}
					
					administrator.readBookLoan(userBookLoan).forEach(row -> System.out.println(row));;
					break;
					
				case "0": // return to operation select
					continue;
				}
				break;
			case "3": // UPDATE operation
				System.out.println("/*MAIN > ADMIN > UPDATE************************************************************************************/");
				tableSelection = selectTable();
				
				switch(tableSelection) 
				{
				case "1": // Authors table
					System.out.println("/*MAIN > ADMIN > UPDATE > AUTHOR************************************************************************************/");
					administrator.readAuthor(findAllAuthors).forEach(row -> System.out.println(row));
					
					Author userAuthor = createEntityAuthor("Note: The value of Author ID will determine the row to be updated, the following values represent the new data to overwrite with.\nEnter N/A to leave a non primary key field as-is", true, true);
					
					// the user quit somewhere in the process
					if(userAuthor == null) 
					{
						System.out.println("I hit the break!");
						break;
					}
					
					
					if(userAuthor.getAuthorId() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when updating.\nReturning to table select menu");
						break;
					}
					
					administrator.updateAuthor(userAuthor);
					break;
					
				case "2": // Publishers table
					System.out.println("/*MAIN > ADMIN > UPDATE > PUBLISHER************************************************************************************/");
					administrator.readPublisher(findAllPublishers).forEach(row -> System.out.println(row));
					
					Publisher userPublisher= createEntityPublisher("Note: The value of Publisher ID will determine the row to be updated, the following values represent the new data to overwrite with.\nEnter N/A to leave a non primary key field as-is", true, true);
					
					// the user quit somewhere in the process
					if(userPublisher== null) 
					{
						break;
					}
					
					
					if(userPublisher.getPublisherId() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when updating.\nReturning to table select menu");
						break;
					}
					
					
					administrator.updatePublisher(userPublisher);
					break;
					
				case "3": // Books table
					System.out.println("/*MAIN > ADMIN > UPDATE > BOOK************************************************************************************/");
					System.out.println("\nBooks Table:");
					administrator.readBook(findAllBooks).forEach(row -> System.out.println(row));

					System.out.println("\nAuthors Table:");
					administrator.readAuthor(findAllAuthors).forEach(row -> System.out.println(row));
					
					System.out.println("\nPublishers Table:");
					administrator.readPublisher(findAllPublishers).forEach(row -> System.out.println(row));
					
					Book userBook = createEntityBook("Note: The value of Book ID will determine the row to be updated, the following values represent the new data to overwrite with.\nEnter N/A to leave a non primary key field as-is", true, true);
					
					// the user quit somewhere in the process
					if(userBook == null) 
					{
						break;
					}
					
					
					if(userBook.getBookId() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when updating.\nReturning to table select menu");
						break;
					}
					administrator.updateBook(userBook);
					break;
					
				case "4": // Library Branches table
					System.out.println("/*MAIN > ADMIN > UPDATE > BRANCH************************************************************************************/");

					System.out.println("\nBranches Table:");
					administrator.readLibraryBranch(findAllLibraryBranches).forEach(row -> System.out.println(row));
					
					LibraryBranch userLibraryBranch= createEntityLibraryBranch("Note: The value of Branch ID will determine the row to be updated, the following values represent the new data to overwrite with.\nEnter N/A to leave a non primary key field as-is", true, true);
					
					// the user quit somewhere in the process
					if(userLibraryBranch == null) 
					{
						break;
					}
					
					
					if(userLibraryBranch.getBranchId() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when updating.\nReturning to table select menu");
						break;
					}
					
					administrator.updateLibraryBranch(userLibraryBranch);
					break;
					
				case "5": // Borrower table
					System.out.println("/*MAIN > ADMIN > UPDATE > BORROWER************************************************************************************/");

					System.out.println("\nBorrowers Table:");
					administrator.readBorrower(findAllBorrowers).forEach(row -> System.out.println(row));
					
					Borrower userBorrower= createEntityBorrower("Note: The value of Card Number will determine the row to be updated, the following values represent the new data to overwrite with.\nEnter N/A to leave a non primary key field as-is", true, true);
					
					// the user quit somewhere in the process
					if(userBorrower == null) 
					{
						break;
					}
					
					
					if(userBorrower.getCardNo() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when updating.\nReturning to table select menu");
						break;
					}
					
					administrator.updateBorrower(userBorrower);
					break;
					
				case "6":
					System.out.println("/*MAIN > ADMIN > UPDATE > LOAN************************************************************************************/");
					System.out.println("\nBook Loans Table:");
					administrator.readBookLoan(findAllBookLoans).forEach(row -> System.out.println(row));
					
					System.out.println("\nBooks Table:");
					administrator.readBook(findAllBooks).forEach(row -> System.out.println(row));
					
					System.out.println("\nBranch Table:");
					administrator.readLibraryBranch(findAllLibraryBranches).forEach(row -> System.out.println(row));
					
					System.out.println("\nBorrowers Loans Table:");
					administrator.readBorrower(findAllBorrowers).forEach(row -> System.out.println(row));
					
					BookLoan userBookLoan = createEntityBookLoan("Book, Branch, and Card values must exist to update a due date");
					
					// the user quit somewhere in the process
					if(userBookLoan == null) 
					{
						break;
					}
					
					
					if(userBookLoan.getBook().getBookId() == -1 ||
						userBookLoan.getBranch().getBranchId()== -1 ||
						userBookLoan.getBorrower().getCardNo() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when updating.\nReturning to table select menu");
						break;
					}
					
					administrator.updateBookLoan(userBookLoan);
					break;
					
				case "0": // return to operation select
					continue;
				}
				break;
			case "4": // DELETE operation
				System.out.println("/*MAIN > ADMIN > DELETE************************************************************************************/");
				tableSelection = selectTable();
				
				switch(tableSelection) 
				{
				case "1": // Authors table
					System.out.println("/*MAIN > ADMIN > DELETE > AUTHOR************************************************************************************/");
					administrator.readAuthor(findAllAuthors).forEach(row -> System.out.println(row));
					
					Author userAuthor = createEntityAuthor("Note: The Author ID you enter will determine which Author will be deleted", true, false);
					
					// the user quit somewhere in the process
					if(userAuthor == null) 
					{
						break;
					}
					
					if(userAuthor.getAuthorId() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when deleting.\nReturning to operations menu");
						break;
					}
					
					administrator.deleteAuthor(userAuthor);
					break;
					
				case "2": // Publishers table
					System.out.println("/*MAIN > ADMIN > DELETE > PUBLISHER************************************************************************************/");
					administrator.readPublisher(findAllPublishers).forEach(row -> System.out.println(row));
					
					Publisher userPublisher= createEntityPublisher("Note: The Publisher ID you enter will determine which Publisher will be deleted", true, false);
					
					// the user quit somewhere in the process
					if(userPublisher == null) 
					{
						break;
					}
					
					
					if(userPublisher.getPublisherId() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when deleting.\nReturning to operations menu");
						break;
					}
					
					administrator.deletePublisher(userPublisher);
					break;
					
				case "3": // Books table
					System.out.println("/*MAIN > ADMIN > DELETE > BOOK************************************************************************************/");
					administrator.readBook(findAllBooks).forEach(row -> System.out.println(row));
					
					Book userBook = createEntityBook("Note: The Book ID you enter will determine which Book will be deleted", true, false);
					
					// the user quit somewhere in the process
					if(userBook == null) 
					{
						break;
					}
					
					
					if(userBook.getBookId() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when deleting.\nReturning to operations menu");
						break;
					}
					
					administrator.deleteBook(userBook);
					break;
					
				case "4": // Library Branches table
					System.out.println("/*MAIN > ADMIN > DELETE > BRANCH************************************************************************************/");
					administrator.readLibraryBranch(findAllLibraryBranches).forEach(row -> System.out.println(row));
					
					LibraryBranch userLibraryBranch = createEntityLibraryBranch("Note: The Branch ID you enter will determine which Branch will be deleted", true, false);
					
					// the user quit somewhere in the process
					if(userLibraryBranch == null) 
					{
						break;
					}
					
					
					if(userLibraryBranch.getBranchId() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when deleting.\nReturning to operations menu");
						break;
					}
					
					administrator.deleteLibraryBranch(userLibraryBranch);
					break;
					
				case "5": // Borrower table
					System.out.println("/*MAIN > ADMIN > DELETE > BORROWER************************************************************************************/");
					administrator.readBorrower(findAllBorrowers).forEach(row -> System.out.println(row));
					
					Borrower userBorrower = createEntityBorrower("Note: The Card Number you enter will determine which borrower will be deleted", true, false);
					
					// the user quit somewhere in the process
					if(userBorrower == null) 
					{
						break;
					}
					
					
					if(userBorrower.getCardNo() == -1) 
					{
						System.out.println("You cannot enter N/A for a primary key value when deleting.\nReturning to operations menu");
						break;
					}
					
					administrator.deleteBorrower(userBorrower);
					break;
					
				case "6": // Book Loans table
					System.out.println("/*MAIN > ADMIN > DELETE > LOAN************************************************************************************/");
					System.out.println("Admin cannot delete a Book Loan.\nQuitting to operation selection");
					break;
					
				case "0": // return to operation select
					continue;
				}
				break;
			case "0": // Return to user select
				return;
			}
		}
		while(true);
	}
	
	/*
	 * This function returns an object created via user input. 
	 * note - will print the note before asking for input, mostly for advising input
	 * getPrimaryKey - whether the user is asked to enter their own primary key, false for create operations, true for read
	 * getNonPrimaryKeyValues - whether to ask the user to enter any non-PK fields, useful for delete operations
	 * */
	private Author createEntityAuthor(String note, boolean getPrimaryKey, boolean getNonPrimaryKeyValues) 
	{
		Author userAuthor = new Author();
		StringBuffer allStringInput = new StringBuffer();
		Integer allIntegerInput;
		
		System.out.println("\n" + note);
		
		if(getPrimaryKey) 
		{
			// Getting ID
			allIntegerInput = getIntegerFieldFromUser("Author ID");
			if(allIntegerInput == Integer.MIN_VALUE) 
			{
				return null; 
			}
			userAuthor.setAuthorId(allIntegerInput);
		}
		else 
		{
			userAuthor.setAuthorId(-1);
		}
		
		if(getNonPrimaryKeyValues) 
		{
			// Getting name
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Author Name"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userAuthor.setAuthorName(allStringInput.toString());
		}
		else 
		{
			userAuthor.setAuthorName("%");
		}
		
		return userAuthor;
	}
	
	/*
	 * This function returns an object created via user input. 
	 * note - will print the note before asking for input, mostly for advising input
	 * getPrimaryKey - whether the user is asked to enter their own primary key, false for create operations, true for read
	 * getNonPrimaryKeyValues - whether to ask the user to enter any non-PK fields, useful for delete operations
	 * */
	private Publisher createEntityPublisher(String note, boolean getPrimaryKey, boolean getNonPrimaryKeyValues) 
	{
		Publisher userPublisher = new Publisher();
		StringBuffer allStringInput = new StringBuffer();
		Integer allIntegerInput;

		System.out.println("\n" + note);
		
		if(getPrimaryKey) 
		{
			// Getting ID
			allIntegerInput = getIntegerFieldFromUser("Publisher ID");
			if(allIntegerInput == Integer.MIN_VALUE) 
			{
				return null;
			}
			userPublisher.setPublisherId(allIntegerInput);
		}
		else 
		{
			userPublisher.setPublisherId(-1);
		}
		if(getNonPrimaryKeyValues) 
		{
			// Getting name
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Publisher Name"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userPublisher.setPublisherName(allStringInput.toString());
			
			// Getting Address
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Publisher Address"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userPublisher.setPublisherAddress(allStringInput.toString());
			
			// Getting Phone
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Publisher Phone"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userPublisher.setPublisherPhone(allStringInput.toString());
		}
		else 
		{
			userPublisher.setPublisherName("%");
			userPublisher.setPublisherAddress("%");
			userPublisher.setPublisherPhone("%");
		}
		

		return userPublisher;
	}
	
	/*
	 * This function returns an object created via user input. 
	 * note - will print the note before asking for input, mostly for advising input
	 * getPrimaryKey - whether the user is asked to enter their own primary key, false for create operations, true for read
	 * getNonPrimaryKeyValues - whether to ask the user to enter any non-PK fields, useful for delete operations
	 * */
	private Book createEntityBook(String note, boolean getPrimaryKey, boolean getNonPrimaryKeyValues) 
	{
		Book userBook = new Book();
		userBook.setAuthor(new Author());
		userBook.setPublisher(new Publisher());
		
		StringBuffer allStringInput = new StringBuffer();
		Integer allIntegerInput;
		
		System.out.println("\n" + note);
		
		if(getPrimaryKey) 
		{
			// Getting ID
			allIntegerInput = getIntegerFieldFromUser("Book ID");
			if(allIntegerInput == Integer.MIN_VALUE) 
			{
				return null;
			}
			userBook.setBookId(allIntegerInput);
		}
		else 
		{
			userBook.setBookId(-1);
		}
		
		
		if(getNonPrimaryKeyValues) 
		{
			// Getting name
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Book Name"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userBook.setTitle(allStringInput.toString());
			
			// Getting author ID
			allIntegerInput = getIntegerFieldFromUser("Author ID");
			if(allIntegerInput == Integer.MIN_VALUE) 
			{
				return null;
			}
			userBook.getAuthor().setAuthorId(allIntegerInput);
			
			// Getting publisher ID
			allIntegerInput = getIntegerFieldFromUser("Publisher ID");
			if(allIntegerInput == Integer.MIN_VALUE) 
			{
				return null;
			}
			userBook.getPublisher().setPublisherId(allIntegerInput);
		}
		else 
		{
			userBook.setTitle("%");
			userBook.getAuthor().setAuthorId(-1);
			userBook.getPublisher().setPublisherId(-1);
		}
		
		return userBook;
	}
	
	/*
	 * This function returns an object created via user input. 
	 * note - will print the note before asking for input, mostly for advising input
	 * getPrimaryKey - whether the user is asked to enter their own primary key, false for create operations, true for read
	 * getNonPrimaryKeyValues - whether to ask the user to enter any non-PK fields, useful for delete operations
	 * */
	private LibraryBranch createEntityLibraryBranch(String note, boolean getPrimaryKey, boolean getNonPrimaryKeyValues) 
	{
		LibraryBranch userLibraryBranch = new LibraryBranch();
		StringBuffer allStringInput = new StringBuffer();
		Integer allIntegerInput;
		
		System.out.println("\n" + note);
		
		if(getPrimaryKey) 
		{
			// Getting ID
			allIntegerInput = getIntegerFieldFromUser("Library Branch ID");
			if(allIntegerInput == Integer.MIN_VALUE) 
			{
				return null;
			}
			userLibraryBranch.setBranchId(allIntegerInput);
		}
		else 
		{
			userLibraryBranch.setBranchId(-1);
		}
		
		if(getNonPrimaryKeyValues) 
		{
			// Getting name
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Library Branch Name"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userLibraryBranch.setBranchName(allStringInput.toString());
			
			// Getting address
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Library Branch Address"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userLibraryBranch.setBranchAddress(allStringInput.toString());
			
		}
		else 
		{
			userLibraryBranch.setBranchName("%");
			userLibraryBranch.setBranchAddress("%");
		}
		
		return userLibraryBranch;
	}

	/*
	 * This function returns an object created via user input. 
	 * note - will print the note before asking for input, mostly for advising input
	 * getPrimaryKey - whether the user is asked to enter their own primary key, false for create operations, true for read
	 * getNonPrimaryKeyValues - whether to ask the user to enter any non-PK fields, useful for delete operations
	 * */
	private Borrower createEntityBorrower(String note, boolean getPrimaryKey, boolean getNonPrimaryKeyValues) 
	{
		Borrower userBorrower= new Borrower();
		StringBuffer allStringInput = new StringBuffer();
		Integer allIntegerInput;
		
		System.out.println("\n" + note);

		if(getPrimaryKey) 
		{
			// Getting ID
			allIntegerInput = getIntegerFieldFromUser("Borrower Card Number");
			if(allIntegerInput == Integer.MIN_VALUE) 
			{
				return null;
			}
			userBorrower.setCardNo(allIntegerInput);
		}
		else 
		{
			userBorrower.setCardNo(-1);
		}

		if(getNonPrimaryKeyValues) 
		{
			// Getting name
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Borrower Name"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userBorrower.setName(allStringInput.toString());
			
			// Getting address
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Borrower Address"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userBorrower.setAddress(allStringInput.toString());
			
			// Getting phone number
			allStringInput.setLength(0); // empty the buffer before input
			allStringInput.append(getStringFieldFromUser("Borrower Phone Number"));
			if("quit".equals(allStringInput.toString())) 
			{
				return null;
			}
			userBorrower.setPhone(allStringInput.toString());
		}
		else 
		{
			userBorrower.setName("%");
			userBorrower.setAddress("%");
			userBorrower.setPhone("%");
		}
		
		return userBorrower;
	}
	
	/*
	 * This function returns an object created via user input. 
	 * note - will print the note before asking for input, mostly for advising input
	 * */
	private BookLoan createEntityBookLoan(String note) 
	{
		BookLoan userBookLoan = new BookLoan();
			userBookLoan.setBook(new Book());
				userBookLoan.getBook().setAuthor(new Author());
				userBookLoan.getBook().setPublisher(new Publisher());
			userBookLoan.setBranch(new LibraryBranch());
			userBookLoan.setBorrower(new Borrower());
			
		StringBuffer allStringInput = new StringBuffer();
		Integer allIntegerInput;
		
		System.out.println("\n" + note);
		
		// Getting book ID
		allIntegerInput = getIntegerFieldFromUser("Book ID");
		if(allIntegerInput == Integer.MIN_VALUE) 
		{
			return null;
		}
		userBookLoan.getBook().setBookId(allIntegerInput);
		System.out.println("setting bookId: " + userBookLoan.getBook().getBookId());
		
		// Getting Branch ID
		allIntegerInput = getIntegerFieldFromUser("Branch ID");
		if(allIntegerInput == Integer.MIN_VALUE) 
		{
			return null;
		}
		userBookLoan.getBranch().setBranchId(allIntegerInput);
		System.out.println("setting branchId: " + userBookLoan.getBranch().getBranchId());
		
		// Getting card number
		allIntegerInput = getIntegerFieldFromUser("Card Number");
		if(allIntegerInput == Integer.MIN_VALUE) 
		{
			return null;
		}
		userBookLoan.getBorrower().setCardNo(allIntegerInput);
		System.out.println("setting cardNo: " + userBookLoan.getBorrower().getCardNo());
		
		// cannot change dateOut
		userBookLoan.setDateOut(Date.valueOf("0001-01-01"));

		// Getting due date
		// get year:
		Integer year;
		do 
		{
			year = getIntegerFieldFromUser("Due Year (YYYY)");
			if(year == Integer.MIN_VALUE) 
			{
				return null;
			}
			
			if(year == -1) 
			{
				userBookLoan.setDueDate(Date.valueOf("0001-01-01"));
				return userBookLoan;
			}
			
			if(year < 0 || year > 10000) 
			{
				System.out.println("Input not recognized." + year);
			}
		}
		while(year < 1 || year > 10000);
		
		// get month
		Integer month;
		do 
		{
			month = getIntegerFieldFromUser("Due Month (MM)");
			if(month == Integer.MIN_VALUE) 
			{
				return null;
			}
			
			if(year == -1) 
			{
				userBookLoan.setDueDate(Date.valueOf("0001-01-01"));
				return userBookLoan;
			}
			
			if(month < 0 || month > 13) 
			{
				System.out.println("Input not recognized." + month);
			}
		}
		while(month < 1 || month > 13);
		
		// get day
		Integer day; 
		do 
		{
			day = getIntegerFieldFromUser("Due Day (DD)");
			if(day == Integer.MIN_VALUE) 
			{
				return null;
			}
			if(year == -1) 
			{
				userBookLoan.setDueDate(Date.valueOf("0001-01-01"));
				return userBookLoan;
			}
			if(day < 1 || day > 31) 
			{
				System.out.println("Input not recognized." + day);
			}
		}
		while(day < 0 || day > 31);

		allStringInput.setLength(0); // empty the buffer before input
		allStringInput.append(StringUtils.leftPad(year.toString(), 4, "0") + "-" + StringUtils.leftPad(month.toString(), 2, "0")  + "-" + StringUtils.leftPad(day.toString(), 2, "0"));
		userBookLoan.setDueDate(Date.valueOf(allStringInput.toString()));
		
		return userBookLoan;
	}
	
	/*
	 * This function prompts the admin with all the tables in the db and returns their selection
	 * */
	private String selectTable() 
	{
		do 
		{
			System.out.println("Select the table you would like to do your operation on.");
			System.out.println("1. Authors\n2. Publishers\n3. Books\n4. Library Branch\n5. Borrower\n6. Book Loans\n0. Quit to previous");
			
			String targetTable = getNextLine();
			
			switch(targetTable) 
			{
			case "1": // Authors table
				return "1";
			case "2": // Publishers table
				return "2";
			case "3": // Books table
				return "3";
			case "4": // Library Branches table
				return "4";
			case "5": // Borrower table
				return "5";
			case "6": // Book Loans table
				return "6";
			case "0": // return to operation select
				return "0";
			}
			
			System.out.println("Invalid Input.");
			
		}while(true);
	}
}
