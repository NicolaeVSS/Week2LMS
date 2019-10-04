package com.ss.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ss.lms.dataaccess.*;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.Publisher;

public class UserLibrarian implements ServiceLibrarian{

	private DataAccess<Book> bookDao = null;
	private DataAccess<LibraryBranch> libraryBranchDao = null;
	private DataAccess<BookCopy> bookCopyDao = null;
	
	public UserLibrarian(BookDataAccess bookDao, LibraryBranchDataAccess libraryBranchDao, BookCopyDataAccess bookCopyDao) {
		this.bookDao = bookDao;
		this.libraryBranchDao = libraryBranchDao;
		this.bookCopyDao = bookCopyDao;
	}
	
	@Override
	public void closeConnection() 
	{
		try 
		{
			bookDao.close();
			libraryBranchDao.close();
			bookCopyDao.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void createBookCopy(BookCopy bookCopy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Book> readBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LibraryBranch> readLibraryBranch(LibraryBranch libraryBranch) {
		// TODO Auto-generated method stub
		ArrayList<LibraryBranch> branches = null;
		try {
			branches = new ArrayList<LibraryBranch>(libraryBranchDao.find(libraryBranch));
			return branches;
		}
		catch(SQLException e) {
			System.out.println("Invalid Query");
		}
		return branches;
	}

	@Override
	public ArrayList<BookCopy> readBookCopy(BookCopy bookCopy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateLibraryBranch(LibraryBranch libraryBranch) {
		// TODO Auto-generated method stub
		try {
			LibraryBranch oldBranch = libraryBranchDao.find(new LibraryBranch(libraryBranch.getBranchId(), "%", "%")).get(0);

			if(libraryBranch.getBranchAddress().equals("%")) {
				libraryBranch.setBranchAddress(oldBranch.getBranchAddress());
			}
			if(libraryBranch.getBranchName().contentEquals("%")) {
				libraryBranch.setBranchName(oldBranch.getBranchName());
			}
			libraryBranchDao.update(libraryBranch);		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateBookCopy(BookLoan bookCopy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBookCopy(BookLoan bookCopy) {
		// TODO Auto-generated method stub
		
	}

}
