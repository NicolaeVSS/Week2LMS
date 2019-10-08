package com.ss.lms.service;

import java.util.*;

import com.ss.lms.entity.*;

public interface ServiceBorrower
{
	
	public void closeConnection();
	
	/*************************************************
	 * 
	 * ALL CREATE OPERATIONS
	 * 
	 *************************************************/

	public void createBookLoan(BookLoan bookLoan);
	
	/*************************************************
	 * 
	 * ALL READ OPERATIONS
	 * 
	 *************************************************/

	public ArrayList<LibraryBranch> readLibraryBranch(LibraryBranch libraryBranch);
	
	public ArrayList<Borrower> readBorrower(Borrower borrower);

	public ArrayList<BookLoan> readBookLoan(BookLoan bookLoan);

	public ArrayList<BookCopy> readBookCopy(BookCopy bookCopy);
	
	/*************************************************
	 * 
	 * ALL UPDATE OPERATIONS
	 * 
	 *************************************************/

	public void updateBookCopy(BookLoan bookCopy);
	
	/*************************************************
	 * 
	 * ALL DLETE OPERATIONS
	 * 
	 *************************************************/

	public void returnBookLoan(BookLoan bookLoan);

}
