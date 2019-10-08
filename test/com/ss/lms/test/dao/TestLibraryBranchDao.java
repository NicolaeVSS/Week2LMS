package com.ss.lms.test.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ss.lms.dataaccess.DataAccess;
import com.ss.lms.dataaccess.LibraryBranchDataAccess;
import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.LibraryBranch;

class TestLibraryBranchDao {

	static DataAccess<LibraryBranch> libraryBranchDao;
	static LibraryBranch libraryBranch;
	static Connection con;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		libraryBranchDao = new LibraryBranchDataAccess();
		libraryBranch = new LibraryBranch(15, "TestName", "TestAddress");
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false", "root", "");
		Statement stmt = con.createStatement();
		stmt.executeUpdate("Delete from tbl_library_branch where " + "branchId = 15");
	}

	@AfterEach
	void tearDownAfterEach() throws Exception {
		Statement stmt = con.createStatement();
		stmt.executeUpdate("Delete from tbl_library_branch where " + "branchId = 15");
	}



	@AfterAll
	static void tearDownAfterClass() throws SQLException {
		con.close();
	}

	@Test
	public void testInsert() throws SQLException {
		int expected = 1;
		int actual = 0;
		libraryBranchDao.insert(libraryBranch);
		String sql = "select * from tbl_library_branch " + "where branchId = 15";
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		// System.out.println(sql);
		result.next();
		// System.out.println(result.getFetchSize());
//		System.out.println(result.getInt(1));
//		System.out.println(result.getString(2));
//		System.out.println(result.getString(3));
		if (result.getInt(1) == 15 && "TestName".equals(result.getString(2))
				&& "TestAddress".equals(result.getString(3))) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testFind() throws SQLException {
		int expected = 1;
		int actual = 0;
		String sql = "insert into tbl_library_branch (branchId, branchName, branchAddress)"
				+ " values (15, \"TestName\", \"TestAddress\")";
		Statement stmt=con.createStatement();
		stmt.executeUpdate(sql);
		ArrayList<LibraryBranch> branches = libraryBranchDao.find(libraryBranch);
//		System.out.println(branches.get(0).getBranchId() + " "+
//				branches.get(0).getBranchName() + " "+
//				branches.get(0).getBranchAddress() + " ");
		if(branches.get(0).getBranchId() == 15 
				&& "TestName".equals(branches.get(0).getBranchName())
				&& "TestAddress".equals(branches.get(0).getBranchAddress())) {
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdate() throws SQLException {
		int expected = 1;
		int actual = 0;

		Statement stmt=con.createStatement();
		String sql = "insert into tbl_library_branch (branchId, branchName, branchAddress)"
				+ " values (15, \"TestName\", \"TestAddress\")";
		stmt.executeUpdate(sql);
		libraryBranch.setBranchName("NewTestName");
		libraryBranch.setBranchAddress("NewTestAddress");
		libraryBranchDao.update(libraryBranch);

		stmt=con.createStatement();
		ResultSet result = stmt.executeQuery("select * from tbl_library_branch "
				+ "where branchId = 15 ");

		//System.out.println(sql);
		result.next();
		//System.out.println(result.getFetchSize());
		if (result.getInt(1) == 15 && "NewTestName".equals(result.getString(2))
				&& "NewTestAddress".equals(result.getString(3))){
			actual = 1;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testDelete() throws SQLException {
		int expected = 1;
		int actual1 = 0;
		int actual2 = 0;
		int actual = 0;
		
		Statement stmt=con.createStatement();
		String sql = "insert into tbl_library_branch (branchId, branchName, branchAddress)"
				+ " values (15, \"TestName\", \"TestAddress\")";
		stmt.executeUpdate(sql);
		
		ResultSet result = stmt.executeQuery("select count(1) from tbl_library_branch");
		result.next();
		int startCount = result.getInt(1);
		//System.out.println(result.getInt(1));
		
		libraryBranchDao.delete(libraryBranch);
		
		result = stmt.executeQuery("select count(1) from tbl_library_branch");
		result.next();
		int finalCount = result.getInt(1);
		
		if(startCount == finalCount + 1) {
			actual1 = 1;
		}
		
		result = stmt.executeQuery("select count(1) from tbl_library_branch where branchId = 15");
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
