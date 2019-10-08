package com.ss.lms.test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ss.lms.dataaccess.BookCopyDataAccess;
import com.ss.lms.dataaccess.DataAccess;
import com.ss.lms.entity.*;

class TestBookCopyDao {

	static DataAccess<BookCopy> bookCopyDao;
	static Author author;
	static Publisher publisher;
	static Book book;
	static LibraryBranch libraryBranch;
	static BookCopy bookCopy;
	static Connection con;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		bookCopyDao = new BookCopyDataAccess();
		author = new Author(-1, "%");
		publisher = new Publisher(-1, "%", "%", "%");
		book = new Book(7, "%", author, publisher);
		libraryBranch = new LibraryBranch(4, "TestName", "TestAddress");
		bookCopy = new BookCopy(book, libraryBranch, 50);
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false","root","");
		Statement stmt=con.createStatement();
		stmt.executeUpdate("Delete from tbl_book_copies where "
						+ "bookId = 7 and branchId = 4;");
	}

	@AfterEach
	void tearDownAfterTest() throws Exception {
		Statement stmt=con.createStatement();
		stmt.executeUpdate("Delete from tbl_book_copies where "
						+ "bookId = 7 and branchId = 4;");
	}
	@AfterAll
	static void tearDownAfterClass() throws SQLException {
		con.close();
	}

	@Test
	public void testInsert() throws SQLException {
		int expected = 1;
		int actual = 0;
		bookCopyDao.insert(bookCopy);
		String sql = "select * from tbl_book_copies "
				+ "where bookId = 7 "
				+ "and branchId = 4 "
				+ "and noOfCopies = 50;";
		Statement stmt=con.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		//System.out.println(sql);
		result.next();
		//System.out.println(result.getFetchSize());
		if(result.getInt(1) == 7 && result.getInt(2) == 4 && result.getInt(3) == 50) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testFind() throws SQLException {
		int expected = 1;
		int actual = 0;
		String sql = "insert into tbl_book_copies (bookId, branchId, noOfCopies)"
				+ " values (7, 4, 50)";
		Statement stmt=con.createStatement();
		stmt.executeUpdate(sql);
		ArrayList<BookCopy> bookCopies = bookCopyDao.find(bookCopy);
//		System.out.println(bookCopies.size());
//		System.out.println(bookCopies.get(0).getBook().getBookId() + "- bookId\n"
//				+ bookCopies.get(0).getBranch().getBranchId()
//				+ "- BranchId\n" + bookCopies.get(0).getNoOfCopies());
		if(bookCopies.get(0).getBook().getBookId() == 7 
				&& bookCopies.get(0).getBranch().getBranchId() == 4
				&& bookCopies.get(0).getNoOfCopies() == 50) {
			actual = 1;
		}
		assertEquals(expected, actual);
		
	}

	@Test
	public void testUpdate() throws SQLException {
		int expected = 1;
		int actual = 0;

		Statement stmt=con.createStatement();
		String sql = "insert into tbl_book_copies (bookId, branchId, noOfCopies)"
				+ " values (7, 4, 50)";
		stmt.executeUpdate(sql);
		bookCopy.setNoOfCopies(51);
		bookCopyDao.update(bookCopy);

		stmt=con.createStatement();
		ResultSet result = stmt.executeQuery("select * from tbl_book_copies "
				+ "where bookId = 7 "
				+ "and branchId = 4 ");

		//System.out.println(sql);
		result.next();
		//System.out.println(result.getFetchSize());
		if(result.getInt(1) == 7 && result.getInt(2) == 4 && result.getInt(3) == 51) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testDelete() throws SQLException  {
		int expected = 1;
		int actual1 = 0;
		int actual2 = 0;
		int actual = 0;
		
		Statement stmt=con.createStatement();
		String sql = "insert into tbl_book_copies (bookId, branchId, noOfCopies)"
				+ " values (7, 4, 50)";
		stmt.executeUpdate(sql);
		
		ResultSet result = stmt.executeQuery("select count(1) from tbl_book_copies");
		result.next();
		int startCount = result.getInt(1);
		//System.out.println(result.getInt(1));
		
		bookCopyDao.delete(bookCopy);
		
		result = stmt.executeQuery("select count(1) from tbl_book_copies");
		result.next();
		int finalCount = result.getInt(1);
		
		if(startCount == finalCount + 1) {
			actual1 = 1;
		}
		
		result = stmt.executeQuery("select count(1) from tbl_book_copies where bookId = 7 and branchId = 4");
		result.next();
		//System.out.println(result.getInt(1));
		if(result.getInt(1) == 0) {
			actual2 = 1;
		}
		if(actual1 == 1 && actual2 == 1) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}
	

}
