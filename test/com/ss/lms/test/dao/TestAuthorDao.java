package com.ss.lms.test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ss.lms.dataaccess.*;
import com.ss.lms.entity.Author;

class TestAuthorDao {

	static AuthorDataAccess authorsDao;

	// used to find all authors
	public static Author findAllAuthors = new Author(-1, "%");
	
	// used as input to the dao for testing
	public static Author author = new Author(1000,"Testing Author");
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		authorsDao = new AuthorDataAccess();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		authorsDao.delete(author);
		authorsDao.close();
	}

	@Test
	final void testAllFunctions() throws SQLException 
	{
		// save current data
		ArrayList<Author> oldData = authorsDao.find(findAllAuthors);
		
		// create new entry
		authorsDao.insert(author);
		
		// append that entry manually to the end of old data
		oldData.add(author);
		
		// retrieve what we inserted into the DB
		ArrayList<Author> foundData = authorsDao.find(author);
		if(foundData.size() != 1) 
		{
			foundData.forEach(ele -> System.out.println(ele.toString()));
			fail("More than one author entry was found");
		}
		
		// check we found our entered author
		// assertEquals(author,foundData.get(0));
		if(! (author.getAuthorId().equals(foundData.get(0).getAuthorId()) &&
				author.getAuthorName().equals(foundData.get(0).getAuthorName())))
		{
			fail("Mismatch:\n" + author + "\n" + foundData.get(0));
		}
		
		// check the table as a whole is changed
		// assertEquals(oldData,authorsDao.find(findAllAuthors));
		ArrayList<Author> newData = authorsDao.find(findAllAuthors);
		for(int i = 0; i<oldData.size(); ++i) 
		{
			if(! (newData.get(i).getAuthorId().equals(oldData.get(i).getAuthorId()) &&
					newData.get(i).getAuthorName().equals(oldData.get(i).getAuthorName())))
			{
				fail("Mismatch:\n" + newData.get(i) + "\n" + foundData.get(i));
			}
			
		}
		
		
		// remove our author entry
		authorsDao.delete(author);
		oldData.remove(author);
		newData = authorsDao.find(findAllAuthors);
		
		// check he no longer exists
		// assertEquals(oldData, authorsDao.find(findAllAuthors));
		for(int i = 0; i < newData.size(); ++i) 
		{
			if(! (newData.get(i).getAuthorId().equals(oldData.get(i).getAuthorId()) &&
					newData.get(i).getAuthorName().equals(oldData.get(i).getAuthorName())))
			{
				fail("Mismatch:\n" + newData.get(i) + "\n" + foundData.get(i));
			}
		}
	}
}
