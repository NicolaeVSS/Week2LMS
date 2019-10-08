package com.ss.lms.dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;

public class BookCopyDataAccess extends DataAccess<BookCopy> {
	public BookCopyDataAccess() throws SQLException, ClassNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}
	//BookCopy has
	//bookId int
	//branchId int
	//noOfCopies int

	@Override
	public void insert(BookCopy entity) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement query;
		String sql;
		//query = con.createStatement();
		sql = "insert into tbl_book_copies (bookId, branchId, noOfCopies)"
				+ "values (?,?,?);";

		query = con.prepareStatement(sql);
		query.setInt(1, entity.getBook().getBookId());
		query.setInt(2, entity.getBranch().getBranchId());
		query.setInt(3, entity.getNoOfCopies());
		
		//System.out.println(query);
		
		query.executeUpdate();
	}

	@Override
	public ArrayList<BookCopy> find(BookCopy entity) throws SQLException {
		// TODO Auto-generated method stub
		//ArrayList<BookCopy> bookCopyList = new ArrayList<BookCopy>();
		String sql;
		int findBookId = entity.getBook().getBookId();
		int findBranchId = entity.getBranch().getBranchId();
		int findNoOfCopies = entity.getNoOfCopies();
		String strBookId = "bookId = ? ";
		String strBranchId = "branchId = ? ";
		String strNoOfCopies = "noOfCopies = ? "; 
		ResultSet result;
		PreparedStatement query;
		if(findBookId == -1) {
			strBookId = ("bookId > ? ");
		}
		if(findBranchId == -1) {
			strBranchId = ("branchId > ? ");
		}
		if(findNoOfCopies == -1) {
			strNoOfCopies = ("noOfCopies > ? ");
		}
		
		sql = "select * from tbl_book_copies "
				+ "where " + strBookId 
				+ "and " + strBranchId  
				+ "and " + strNoOfCopies;
		query = con.prepareStatement(sql);
		query.setInt(1, findBookId);
		query.setInt(2, findBranchId);
		query.setInt(3, findNoOfCopies);
		

		//System.out.println(query);
		
		result = query.executeQuery();
			
		return packageResultSet(result);
	}

	@Override
	public void update(BookCopy entity) throws SQLException {
		// TODO Auto-generated method stub
		//ResultSet result;
		PreparedStatement query;
		String sql;
		//query = con.createStatement();
		sql = "update tbl_book_copies set noOfCopies = ?"
						+ " where bookId = ? "
						+ " and branchId = ? ";

		query = con.prepareStatement(sql);
		query.setInt(2, entity.getBook().getBookId());
		query.setInt(3, entity.getBranch().getBranchId());
		query.setInt(1, entity.getNoOfCopies());
		//System.out.println("Update query: " + query);
		query.executeUpdate();
	}

	@Override
	public void delete(BookCopy entity) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement query;
		String sql;
		sql = "delete from tbl_book_copies where branchId = ?"
				+ " and bookId = ?;";
		query = con.prepareStatement(sql);

		query.setInt(2, entity.getBook().getBookId());
		query.setInt(1, entity.getBranch().getBranchId());
		
		//System.out.println(query);
		
		query.executeUpdate();
	}
	

    public ArrayList<BookCopy> packageResultSet(ResultSet result) throws SQLException{
    	PreparedStatement query;
    	String sql;
    	ArrayList<BookCopy> bookCopyList = new ArrayList<BookCopy>();
		while(result.next()) { 
			sql = "select authorId, authorName from tbl_author, tbl_book where bookId = ? and authorId = authId;";
			query = con.prepareStatement(sql);
			query.setInt(1, result.getInt(1));
			
			query.executeQuery();
			ResultSet resultAuthor = query.getResultSet();
			resultAuthor.next();


			Author author = new Author(resultAuthor.getInt(1), resultAuthor.getString(2));
			sql = "select * from tbl_publisher, tbl_book where bookId = ? and publisherId = pubId;";
			query = con.prepareStatement(sql);
			query.setInt(1, result.getInt(1));

			//System.out.println(query);
			ResultSet resultPublisher = query.executeQuery();
			resultPublisher.next();
			Publisher publisher = new Publisher(resultPublisher.getInt(1), resultPublisher.getString(2),
						resultPublisher.getString(3), resultPublisher.getString(4));
			sql = "select * from tbl_book where bookId = ?;";
			query = con.prepareStatement(sql);
			query.setInt(1, result.getInt(1));

			//System.out.println(query);
			ResultSet resultBook = query.executeQuery();
			resultBook.next();
			sql = "select * from tbl_library_branch where branchId = ?;";
			query = con.prepareStatement(sql);
			query.setInt(1, result.getInt(2));

			//System.out.println(query);
			ResultSet resultBranch = query.executeQuery();
			resultBranch.next();
			Book book = new Book(resultBook.getInt(1),resultBook.getString(2), author, publisher);
			LibraryBranch branch = new LibraryBranch(resultBranch.getInt(1),resultBranch.getString(2),resultBranch.getString(3));
			BookCopy bookCopy = new BookCopy(book, branch, result.getInt(3));
			bookCopyList.add(bookCopy);  
		}	
    	return bookCopyList;
    }
    
	@Override // Should not be called, book copy uses a composite key.
	public Integer generatePrimaryKey() throws SQLException 
	{
		System.err.println("Erroneous function call. generatePrimaryKey() in BookCopyDataAccess");
		return null;
	}
}
