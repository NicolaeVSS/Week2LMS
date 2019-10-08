package com.ss.lms.dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ss.lms.entity.*;

public class LibraryBranchDataAccess extends DataAccess<LibraryBranch> {
	public LibraryBranchDataAccess() throws SQLException, ClassNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(LibraryBranch entity) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement query;
		String sql;
		//query = con.createStatement();
		sql = "insert into tbl_library_branch (branchId, branchName, branchAddress)"
				+ "values (?,?,?);";

		query = con.prepareStatement(sql);
		query.setInt(1, entity.getBranchId());
		query.setString(2, entity.getBranchName());
		query.setString(3, entity.getBranchName());
		
		query.executeUpdate();
		
	}

	@Override
	public ArrayList<LibraryBranch> find(LibraryBranch entity) throws SQLException {
		// TODO Auto-generated method stub
		String sql;
		int findBranchId = entity.getBranchId();
		String findBranchName = entity.getBranchName();
		String findBranchAddress = entity.getBranchAddress();
		String strBranchId = "branchId = ? ";
		String strBranchName = "branchName LIKE ? ";
		String strBranchAddress = "branchAddress LIKE ? "; 

		ResultSet result;
		PreparedStatement query;
		if(findBranchId == -1) {
			strBranchId = ("branchId > ? ");
		}
		if(findBranchName == "%") {
			strBranchName = ("branchName LIKE ? ");
		}
		if(findBranchAddress == "%") {
			strBranchAddress = ("branchAddress LIKE ?");
		}
		//System.out.println("Hello2");
//		System.out.println("select * from tbl_book_copy "
//				+ "where " + findBranchId 
//				+ "and " + findBranchName 
//				+ "and " + findBranchAddress);
		
		sql = "select * from tbl_library_branch "
				+ "where " + strBranchId 
				+ "and " + strBranchName  
				+ "and " + strBranchAddress;
		query = con.prepareStatement(sql);
		query.setInt(1, findBranchId);
		query.setString(2, findBranchName);
		query.setString(3, findBranchAddress);
		//System.out.println(query.toString());
		
		result = query.executeQuery();
			
		return packageResultSet(result);
	}

	@Override
	public void update(LibraryBranch entity) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement query;
		String sql;
		
		String newBranchName = entity.getBranchName();
		String newBranchAddress = entity.getBranchAddress();
//		
//		sql = "select * from tbl_library_branch "
//				+ "where branchId = ?";
//		query = con.prepareStatement(sql);
//		query.setInt(1, entity.getBranchId());
//		
//		ResultSet result = query.executeQuery();
		
//		if(newBranchName == "%") {
//			newTitle = result.getString(2);
//		}
//		if(authorId == -1) {
//			authorId = result.getInt(3);
//		}
//		if(publisherId == -1) {
//			publisherId = result.getInt(4);
//		}
		
		sql = "update tbl_library_branch "
				+ "set branchName = ?, "
				+ "branchAddress = ? "
				+ "where branchId = ? ";
		query = con.prepareStatement(sql);
		query.setString(1, newBranchName);
		query.setString(2, newBranchAddress);
		query.setInt(3, entity.getBranchId());
		
		//System.out.println(query.toString());
		query.executeUpdate();
	}

	@Override
	public void delete(LibraryBranch entity) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement query;
		String sql;
		sql = "delete from tbl_library_branch where branchId = ?";
		query = con.prepareStatement(sql);

		query.setInt(1, entity.getBranchId());
		
		query.executeUpdate();
	}
	
    public ArrayList<LibraryBranch> packageResultSet(ResultSet result) throws SQLException{
    	ArrayList<LibraryBranch> branchList = new ArrayList<LibraryBranch>();
		while(result.next()) { //BranchID,BranchName, BranchAddress
			LibraryBranch branch = new LibraryBranch(result.getInt(1), result.getString(2), result.getString(3));
			branchList.add(branch); 
		}	
    	return branchList;
    }
    
	@Override
	public Integer generatePrimaryKey() throws SQLException 
	{
		String sql = "SELECT MAX(branchId) AS max FROM library.tbl_library_branch;";
		Statement query = con.createStatement();
		
		ResultSet result = query.executeQuery(sql);
		result.next();
		
		return (result.getInt("max") + 1);
	}
}
