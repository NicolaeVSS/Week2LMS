/**
 * 
 */
package com.ss.lms.test.service;
import static org.junit.Assert.*;
import java.sql.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ss.lms.dataaccess.*;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;
import com.ss.lms.presentation.PresentationLibrarian;
import com.ss.lms.service.ServiceLibrarian;
import com.ss.lms.service.UserLibrarian;

/**
 * @author sj
 *
 */
class TestLibrarianService {


	private ServiceLibrarian librarian;
	private Author author;
	private Publisher publisher;
	private Book book;
	private LibraryBranch libraryBranch;
	private BookCopy bookCopy;
	DataAccess<Book> bookDao;
	DataAccess<BookCopy> bookCopyDao; 
	DataAccess<LibraryBranch> libraryBranchDao;
	Connection con;
	
	@BeforeEach
	public void beforeTest() {
		try {
			librarian = new UserLibrarian(new BookDataAccess(), new LibraryBranchDataAccess(), new BookCopyDataAccess());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false","root","");
			////database-1.crhcwiispks5.us-east-2.rds.amazonaws.com
		} catch (ClassNotFoundException|SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		author = new Author(1, "%");
		publisher = new Publisher(1, "%", "%", "%");
		book = new Book(6, "%", author, publisher);
		libraryBranch = new LibraryBranch(6, "TestName", "TestAddress");
		bookCopy = new BookCopy(book, libraryBranch, 50);
		librarian.deleteBookCopy(bookCopy);
		librarian.updateLibraryBranch(libraryBranch);
	}

	@After
	public void tearDown() {
		librarian.deleteBookCopy(bookCopy);
	}
	
	@Test
	public void testCloseConnection() {
		int expected = 1;
		int actual = 0;
		librarian.closeConnection();
		
		ArrayList<Book> books = librarian.readBook(book);
		librarian.readBook(book);
		try {
			books.get(0).getBookId();
		}
		catch(NullPointerException e) {
			actual = 1;
		}
		assertEquals(expected, actual);

	}
	
	@Test
	public void testCreateBookCopy() {
		int expected = 1;
		int actual = 0;
		librarian.createBookCopy(bookCopy);
		ArrayList<BookCopy> bookCopies = librarian.readBookCopy(bookCopy);

		if(bookCopies.get(0).getBranch().getBranchId() == 6
				&& bookCopies.get(0).getBook().getBookId() == 6 
				&& bookCopies.get(0).getNoOfCopies() == 50) {
			actual = 1;
		}
		
		assertEquals(expected, actual);
	}

	@Test
	public void testReadBook() {
		int expected = 1;
		int actual = 0;

		ArrayList<Book> books = librarian.readBook(book);
		
		if(books.get(0).getAuthor().getAuthorId() == 1 
				&& books.get(0).getPublisher().getPublisherId() == 1 
				&& books.get(0).getTitle().equals("Test") 
				&& book.getBookId() == 6) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testReadLibraryBranch() {
		int expected = 1;
		int actual = 0;
		
		ArrayList<LibraryBranch> libraryBranches = librarian.readLibraryBranch(libraryBranch);
		if(libraryBranches.get(0).getBranchId() == 6
				&& "TestName".equals(libraryBranches.get(0).getBranchName())
				&& "TestAddress".equals(libraryBranches.get(0).getBranchAddress())) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testReadBookCopy() {
		int expected = 1;
		int actual = 0;
		librarian.createBookCopy(bookCopy);
		ArrayList<BookCopy> bookCopies = librarian.readBookCopy(bookCopy);
		if(bookCopies.get(0).getBook().getBookId() == 6
				&& bookCopies.get(0).getBranch().getBranchId() == 6
				&& bookCopies.get(0).getNoOfCopies() == 50) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdateLibraryBranch() {
		int expected = 1;
		int actual = 0;
		LibraryBranch libraryBranch2 = new LibraryBranch(6, "TestNewName", "TestNewAddress");
		librarian.updateLibraryBranch(libraryBranch2);
		libraryBranch2 = librarian.readLibraryBranch(libraryBranch2).get(0);
		if(libraryBranch2.getBranchId() == 6
				&& "TestNewName".equals(libraryBranch2.getBranchName())
				&& "TestNewAddress".equals(libraryBranch2.getBranchAddress())) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdateBookCopy() {

		librarian.createBookCopy(bookCopy);
		int expected = 1;
		int actual = 0;
		BookCopy bookCopies2 = new BookCopy(book, libraryBranch, 55);
		librarian.updateBookCopy(bookCopies2);
		BookCopy bookCp = new BookCopy(book, libraryBranch, -1);
		BookCopy testBookCopy = librarian.readBookCopy(bookCp).get(0);
		//ArrayList<BookCopy> newBookCopy = new ArrayList<BookCopy>();
		//newBookCopy = librarian.readBookCopy(bookCopies2);
		if(testBookCopy.getBranch().getBranchId() == 6
				&& testBookCopy.getBook().getBookId() == 6 
				&& testBookCopy.getNoOfCopies() == 55) {
			actual = 1;
		}
		
		assertEquals(expected, actual);
	}

	@Test
	public void testDeleteBookCopy() {
		int expected = 1;
		int actual = 0;
		librarian.deleteBookCopy(bookCopy);
		try {
			librarian.readBookCopy(bookCopy).get(0);
		}catch(IndexOutOfBoundsException e) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	
}
