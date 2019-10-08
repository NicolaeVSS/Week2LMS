package com.ss.lms.dataaccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DataAccess<T>
{
    Connection con;

    public DataAccess() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");  
        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library?useSSL=false","root","Bijon128");
    }

    public void close() throws SQLException 
    {
        con.close();
    }

    public abstract void insert(T entity) throws SQLException;

    public abstract ArrayList<T> find(T entity) throws SQLException;
    
    public abstract void update(T entity) throws SQLException;
    
    public abstract void delete(T entity) throws SQLException;
    
    public abstract ArrayList<T> packageResultSet(ResultSet result) throws SQLException;
    
    public abstract Integer generatePrimaryKey() throws SQLException;
}
