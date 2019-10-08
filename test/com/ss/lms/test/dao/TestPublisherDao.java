package com.ss.lms.test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ss.lms.dataaccess.*;
import com.ss.lms.entity.*;

class TestPublisherDao {
	static PublisherDataAccess publisherDao;
	public static Publisher findAllPublishers = new Publisher(-1, "%", "%", "%");
	public static Publisher publisher = new Publisher(1000, "Testing Publisher", "Testing Publisher Address", "Testing Publisher Phone");

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		publisherDao = new PublisherDataAccess();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		publisherDao.delete(publisher);
		publisherDao.close();
	}


	@Test
	final void testAllFunctions() throws SQLException 
	{
		// save current data
		ArrayList<Publisher> oldData = publisherDao.find(findAllPublishers);
				
		// create new entry
		publisherDao.insert(publisher);
		
		// append that entry manually to the end of old data
		oldData.add(publisher);
		
		// retrieve what we inserted into the DB
		ArrayList<Publisher> foundData = publisherDao.find(publisher);
		if(foundData.size() != 1) 
		{
			System.out.println("Found:");
			foundData.forEach(ele -> System.out.println(ele.toString()));
			fail("No or more than one publisher entry was found");
		}
		
		
		// check we found our entered author
		// assertEquals(publisher,foundData.get(0));
		if(! (publisher.getPublisherId().equals(foundData.get(0).getPublisherId()) &&
				publisher.getPublisherName().equals(foundData.get(0).getPublisherName()) &&
				publisher.getPublisherAddress().equals(foundData.get(0).getPublisherAddress()) &&
				publisher.getPublisherPhone().equals(foundData.get(0).getPublisherPhone())))
		{
			fail("Mismatch:\n" + publisher + "\n" + foundData.get(0));
		}
		
		// check the table as a whole is changed
		// assertEquals(oldData,pubsliherDao.find(findAllPublishers));
		ArrayList<Publisher> newData = publisherDao.find(findAllPublishers);
		for(int i = 0; i<oldData.size(); ++i) 
		{
			if(! (newData.get(i).getPublisherId().equals(oldData.get(i).getPublisherId()) &&
					newData.get(i).getPublisherName().equals(oldData.get(i).getPublisherName()) &&
					newData.get(i).getPublisherAddress().equals(oldData.get(i).getPublisherAddress()) &&
					newData.get(i).getPublisherPhone().equals(oldData.get(i).getPublisherPhone())))
			{
				fail("Mismatch:\n" + newData.get(i) + "\n" + oldData.get(i));
			}
			
		}
		
		
		// remove our publisher entry
		publisherDao.delete(publisher);
		oldData.remove(publisher);
		newData = publisherDao.find(findAllPublishers);
		
		// check he no longer exists
		// assertEquals(oldData, authorsDao.find(findAllAuthors));
		for(int i = 0; i < newData.size(); ++i) 
		{
			if(! (newData.get(i).getPublisherId().equals(oldData.get(i).getPublisherId()) &&
					newData.get(i).getPublisherName().equals(oldData.get(i).getPublisherName()) &&
					newData.get(i).getPublisherAddress().equals(oldData.get(i).getPublisherAddress()) &&
					newData.get(i).getPublisherPhone().equals(oldData.get(i).getPublisherPhone())))
			{
				fail("Mismatch:\n" + newData.get(i) + "\n" + foundData.get(i));
			}
		}
	}
}
