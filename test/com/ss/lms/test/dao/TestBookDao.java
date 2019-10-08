package com.ss.lms.test.dao;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ss.lms.dataaccess.AuthorDataAccess;
import com.ss.lms.dataaccess.BookDataAccess;
import com.ss.lms.dataaccess.PublisherDataAccess;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Publisher;

class TestBookDao {
	static BookDataAccess bookDao;
	
	// used to find all books
	public static Author findAllAuthors = new Author(-1, "%");
	public static Publisher findAllPublishers = new Publisher(-1, "%", "%", "%");
	public static Book findAllBooks = new Book(-1, "%", findAllAuthors, findAllPublishers);
	
	// used to ender into the db
	public static Author author = new Author(1000,"Testing Author");
	public static Publisher publisher = new Publisher(1000, "Testing Publisher", "Testing Publisher Address", "Testing Publisher Phone");
	public static Book book = new Book(1000, "Testing Book Title", author, publisher);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		bookDao = new BookDataAccess();
		AuthorDataAccess authorDao = new AuthorDataAccess();
		PublisherDataAccess publisherDao = new PublisherDataAccess();
		
		authorDao.insert(author);
		publisherDao.insert(publisher);
		
		authorDao.close();
		publisherDao.close();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		AuthorDataAccess authorDao = new AuthorDataAccess();
		PublisherDataAccess publisherDao = new PublisherDataAccess();
		
		authorDao.delete(author);
		publisherDao.delete(publisher);
		bookDao.delete(book);
		
		authorDao.close();
		publisherDao.close();
		bookDao.close();
	}


	@Test
	final void testInsert() throws SQLException 
	{
		// save current data
		ArrayList<Book> oldData = bookDao.find(findAllBooks);
		
		// create new entry
		bookDao.insert(book);
		
		// append that entry manually to the end of old data
		oldData.add(book);
		
		// retrieve what we inserted into the DB
		ArrayList<Book> foundData = bookDao.find(book);
		if(foundData.size() != 1) 
		{
			foundData.forEach(ele -> System.out.println(ele.toString()));
			fail("More than one book entry was found");
		}
		
		// check we found our entered author
		// assertEquals(author,foundData.get(0));
		if(! (book.getAuthor().getAuthorId().equals(foundData.get(0).getAuthor().getAuthorId()) &&
				book.getPublisher().getPublisherId().equals(foundData.get(0).getPublisher().getPublisherId()) &&
				book.getTitle().equals(foundData.get(0).getTitle()) &&
				book.getBookId().equals(foundData.get(0).getBookId())))
		{
			fail("Mismatch:\n" + book + "\n" + foundData.get(0));
		}
		
		// check the table as a whole is changed
		// assertEquals(oldData,authorsDao.find(findAllAuthors));
		ArrayList<Book> newData = bookDao.find(findAllBooks);
		for(int i = 0; i<oldData.size(); ++i) 
		{
			if(! (newData.get(i).getAuthor().getAuthorId().equals(oldData.get(i).getAuthor().getAuthorId()) &&
					newData.get(i).getPublisher().getPublisherId().equals(oldData.get(i).getPublisher().getPublisherId()) &&
					newData.get(i).getBookId().equals(oldData.get(i).getBookId()) &&
					newData.get(i).getTitle().equals(oldData.get(i).getTitle())))
			{
				fail("Mismatch:\n" + newData.get(i) + "\n" + foundData.get(i));
			}
		}
		
		
		// remove our author entry
		bookDao.delete(book);
		oldData.remove(book);
		newData = bookDao.find(findAllBooks);
		
		// check he no longer exists
		// assertEquals(oldData, authorsDao.find(findAllAuthors));
		for(int i = 0; i < newData.size(); ++i) 
		{
			if(! (newData.get(i).getAuthor().getAuthorId().equals(oldData.get(i).getAuthor().getAuthorId()) &&
					newData.get(i).getPublisher().getPublisherId().equals(oldData.get(i).getPublisher().getPublisherId()) &&
					newData.get(i).getBookId().equals(oldData.get(i).getBookId()) &&
					newData.get(i).getTitle().equals(oldData.get(i).getTitle())))
			{
				fail("Mismatch:\n" + newData.get(i) + "\n" + foundData.get(i));
			}
		}
	
	}
}
