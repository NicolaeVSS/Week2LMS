package com.ss.lms.service;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ss.lms.dataaccess.AuthorDataAccess;
import com.ss.lms.dataaccess.BookCopyDataAccess;
import com.ss.lms.dataaccess.BookDataAccess;
import com.ss.lms.dataaccess.BookLoanDataAccess;
import com.ss.lms.dataaccess.BorrowerDataAccess;
import com.ss.lms.dataaccess.DataAccess;
import com.ss.lms.dataaccess.LibraryBranchDataAccess;
import com.ss.lms.dataaccess.PublisherDataAccess;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.BookLoan;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.LibraryBranch;
public class UserBorrower implements ServiceBorrower 
{
    public DataAccess<LibraryBranch> libraryBranchDao = null;
    public DataAccess<Borrower> borrowerDao = null;
    public DataAccess<BookCopy> bookCopyDao = null;
    public DataAccess<BookLoan> bookLoanDao = null;
    
    public UserBorrower(LibraryBranchDataAccess libraryBranchDao, BorrowerDataAccess borrowerDao,
            BookCopyDataAccess bookCopyDao, BookLoanDataAccess bookLoanDao) 
    {
        this.libraryBranchDao = libraryBranchDao;
        this.borrowerDao = borrowerDao;
        this.bookCopyDao = bookCopyDao;
        this.bookLoanDao = bookLoanDao;
    }
    
    public void closeConnection() 
    {
        try 
        {
            libraryBranchDao.close();
            borrowerDao.close();
            bookCopyDao.close();
            bookLoanDao.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void returnBookLoan(BookLoan bookLoan) 
    {
        try 
        {
            // get current book book copies for the specific book loan
            ArrayList<BookCopy> currentBookCopies = bookCopyDao.find(new BookCopy(
                    bookLoan.getBook(),
                    bookLoan.getBranch(),
                    -1));
            
            if(currentBookCopies.size() != 1) 
            {
                System.out.println("Couldn't find unique entry in Book Copies");
                return;
            }
            
            // delete the book loan
            bookLoanDao.delete(bookLoan);
            
            
            // increment the noOfCopy in BookCopy
            bookCopyDao.update(new BookCopy(
                    bookLoan.getBook(),
                    bookLoan.getBranch(),
                    currentBookCopies.get(0).getNoOfCopies()+1));
            
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void createBookLoan(BookLoan bookLoan) 
    {
        try 
        {
            // get current book book copies for the specific book loan
            ArrayList<BookCopy> currentBookCopies = bookCopyDao.find(new BookCopy(
                    bookLoan.getBook(),
                    bookLoan.getBranch(),
                    -1));
            
            // make sure there is only one row
            if(currentBookCopies.size() != 1 && currentBookCopies.get(0).getNoOfCopies() < 1) 
            {
                System.out.println("Couldn't find unique entry in Book Copies\nOR the library has no copies");
                return;
            }
            System.out.println("current book copies: " + currentBookCopies.get(0).toString());
            
            // create the book loan
            bookLoanDao.insert(bookLoan);
            
            System.out.println("running insert query on: " + bookLoan.toString());
            
            // increment the noOfCopy in BookCopy
            bookCopyDao.update(new BookCopy(
                    bookLoan.getBook(),
                    bookLoan.getBranch(),
                    currentBookCopies.get(0).getNoOfCopies()-1));
            
            System.out.println("Setting num copies to:" + (currentBookCopies.get(0).getNoOfCopies()-1));
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
    }
    @Override
    public ArrayList<LibraryBranch> readLibraryBranch(LibraryBranch libraryBranch) 
    {
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
    public ArrayList<Borrower> readBorrower(Borrower borrower) 
    {
        // TODO Auto-generated method stub
                ArrayList<Borrower> borrow = null;
                try {
                    borrow = new ArrayList<Borrower>(borrowerDao.find(borrower));
                    return borrow;
                } catch (SQLException e) {
                    System.out.println("Invalid Query");
                }
                return borrow;
    }
    @Override
    public ArrayList<BookLoan> readBookLoan(BookLoan bookLoan) 
    {
        ArrayList<BookLoan> branches = null;
        try {
            branches = new ArrayList<BookLoan>(bookLoanDao.find(bookLoan));
            return branches;
        }
        catch(SQLException e) {
            System.out.println("Invalid Query");
        }
        return branches;
    }
    @Override
    public void updateBookCopy(BookLoan bookLoan) {
        ArrayList<BookCopy> copyList = new ArrayList<BookCopy>();
        try {
            BookCopyDataAccess DaoBookCopy = new BookCopyDataAccess();
            BookCopy copy = new BookCopy(bookLoan.getBook(), bookLoan.getBranch(), -1);
            copyList = DaoBookCopy.find(copy);
            copy.setNoOfCopies(copyList.get(0).getNoOfCopies() + 1);
            DaoBookCopy.insert(copy);
            BookLoanDataAccess DaoBookloan = new BookLoanDataAccess();
            DaoBookloan.delete(bookLoan);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public ArrayList<BookCopy> readBookCopy(BookCopy bookCopy) {
        ArrayList<BookCopy> copies = null;
        try {
            copies = new ArrayList<BookCopy>(bookCopyDao.find(bookCopy));
            return copies;
        } catch (SQLException e) {
            System.out.println("Invalid Query");
        }
        return copies;
    }
}
