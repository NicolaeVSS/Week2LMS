package com.ss.lms.dataaccess;
import java.util.ArrayList;
import java.sql.*;
import com.ss.lms.entity.*;

public class BookLoanDataAccess extends DataAccess<BookLoan> 
{
    public BookLoanDataAccess() throws SQLException, ClassNotFoundException 
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /*
     * Adding book to borrower
     * */
    @Override
    public void insert(BookLoan entity) throws SQLException 
    {
        // TODO Auto-generated method stub
        PreparedStatement query;
        String sql;
        sql = "insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values (?,?,?,now(),now() + INTERVAL 7 DAY)";
        
        query = con.prepareStatement(sql);
        query.setInt(1,entity.getBook().getBookId());
        query.setInt(2,entity.getBranch().getBranchId());
        query.setInt(3,entity.getBorrower().getCardNo());

        query.executeUpdate();

    }
    
    @Override
    public ArrayList<BookLoan> find(BookLoan entity) throws SQLException 
    {
        PreparedStatement query;
        String sql;
        ResultSet result;
        int cardNo =  entity.getBorrower().getCardNo();
        int branchId = entity.getBranch().getBranchId();
        int bookId = entity.getBook().getBookId();
        Date dateDue = entity.getDueDate();
        Date dateOut = entity.getDateOut();
        
        String strBookId = "bookId = ? ";
        String strBranchId = "branchId = ? ";
        String strCard = "cardNo = ? ";
        String strDueDate = "dueDate = ? ";
        String strDateOut = "dateOut = ? ";
        if(cardNo == -1) {
            strCard = "cardNo > ? ";
        }
        if(bookId == -1) {
            strBookId = "bookId > ? ";
        }
        if(branchId == -1) {
            strBranchId = "branchID > ? ";
        }
        if(dateDue.equals(Date.valueOf("0001-01-01"))) {
            strDueDate = "dueDate > ? ";
        }
        if(dateOut.equals(Date.valueOf("0001-01-01"))) {
            strDateOut = "dateOut > ? ";
        }   
        
        sql = "select * from tbl_book_loans "
                + "where " + strBranchId
                + "and " + strCard
                + "and " + strBookId
                + "and " + strDateOut
                + "and " + strDueDate;
                 
        
        query = con.prepareStatement(sql);
        query.setInt(1,entity.getBranch().getBranchId());
        query.setInt(2,entity.getBorrower().getCardNo());
        query.setInt(3,entity.getBook().getBookId());
        query.setDate(4, entity.getDateOut());
        query.setDate(5, entity.getDueDate());

        result = query.executeQuery();
        
        return packageResultSet(result) ;
    }
    
    /*
     * Return book
     * */
    @Override
    public void update(BookLoan entity) throws SQLException {
        PreparedStatement query;
        String sql;
        
        sql = "update tbl_book_loans set "
        		+ "dateOut = ?,"
        		+ "dueDate = ? "
        		+ "where bookId = ? and branchId = ? and cardno = ?";
        
        
        query = con.prepareStatement(sql);
        query.setDate(1, entity.getDateOut());
        query.setDate(2, entity.getDueDate());
        query.setInt(3,entity.getBook().getBookId());
        query.setInt(4,entity.getBranch().getBranchId());   
        query.setInt(5,entity.getBorrower().getCardNo());   
        query.executeUpdate();
    }
    
    @Override
    public void delete(BookLoan entity) throws SQLException 
    {
        String sqlBookId = "bookId = ?";
        String sqlBranchId = " branchId = ?";
        String sqlCardNo = "cardNo = ?";
        
        if(entity.getBook().getBookId() == -1) 
        {
        	sqlBookId = "bookId > ?";
        }
        if(entity.getBranch().getBranchId() == -1) 
        {
        	sqlBranchId = "branchId > ?";
        }
        if(entity.getBorrower().getCardNo() == -1) 
        {
        	sqlCardNo = "cardNo > ?";
        }

        PreparedStatement query;
        String sql = "delete from tbl_book_loans where " + sqlBookId 
        		+ " and " + sqlBranchId
        		+ " and " + sqlCardNo;
        
        
        query = con.prepareStatement(sql);
        query.setInt(1,entity.getBook().getBookId());
        query.setInt(2,entity.getBranch().getBranchId());
        query.setInt(3,entity.getBorrower().getCardNo());
        
        System.out.println("bookId to del:" + entity.getBook().getBookId());
        System.out.println("branchId to del:" + entity.getBranch().getBranchId());
        System.out.println("card to del:" + entity.getBorrower().getCardNo());
        System.out.println(query.toString());
        
        query.executeUpdate();
    }
    
    @Override
    public ArrayList<BookLoan> packageResultSet(ResultSet result) throws SQLException 
    {
        String sql;
        PreparedStatement query;
        ArrayList<BookLoan> bookLoans = new ArrayList<>();
        while (result.next()) {
            //Author
            sql = "select * from tbl_author, tbl_book where bookId = ? and authorId = authId";
            query = con.prepareStatement(sql);
            query.setInt(1, result.getInt(1));
            ResultSet resultAuthor = query.executeQuery();
            resultAuthor.next();
            Author author =  new Author();
            author.setAuthorId(resultAuthor.getInt(1));
            author.setAuthorName(resultAuthor.getString(2));
            
            //Publisher
            sql = "select * from tbl_publisher, tbl_book where bookId = ? and publisherId = publisherId";
            query = con.prepareStatement(sql);
            query.setInt(1, result.getInt(1));
            ResultSet resultPublisher = query.executeQuery();
            resultPublisher.next();
            Publisher publisher = new Publisher(resultPublisher.getInt(1), resultPublisher.getString(2),
                        resultPublisher.getString(3), resultPublisher.getString(4));
            
            //Book
            sql = "select * from tbl_book where bookId = ?";
            query = con.prepareStatement(sql);
            query.setInt(1, result.getInt(1));
            ResultSet resultBook = query.executeQuery();
            resultBook.next();
            Book book = new Book(resultBook.getInt(1),resultBook.getString(2), author, publisher);
        
            
            //Borrower
            sql = "select * from tbl_borrower where cardNo = ?";
            query = con.prepareStatement(sql);
            query.setInt(1, result.getInt(3));
            ResultSet resultBorrow = query.executeQuery();
            resultBorrow.next();
            Borrower borrower = new Borrower(resultBorrow.getInt(1), resultBorrow.getString(2),
                    resultBorrow.getString(3),resultBorrow.getString(4));
            
            //Library
            sql = "select * from tbl_library_branch where branchId = ? "; 
            query = con.prepareStatement(sql);
            query.setInt(1, result.getInt(2));
            ResultSet resultBranch = query.executeQuery();
            resultBranch.next();
            LibraryBranch branch = new LibraryBranch(resultBranch.getInt(1), resultBranch.getString(2),
                    resultBranch.getString(3)); 
            
            BookLoan bookLoan = new BookLoan();
            bookLoan.setBook(book);
            bookLoan.setBranch(branch);     
            bookLoan.setBorrower(borrower);     
            bookLoan.setDateOut(result.getDate("dateOut"));       
            bookLoan.setDueDate(result.getDate("dueDate"));       
            bookLoans.add(bookLoan);
        }
        return bookLoans;
    }
    
	@Override
	public Integer generatePrimaryKey() throws SQLException 
	{
		System.err.println("Erroneous function call. generatePrimaryKey() in BookLoanDataAccess");
		return null;
	}
}