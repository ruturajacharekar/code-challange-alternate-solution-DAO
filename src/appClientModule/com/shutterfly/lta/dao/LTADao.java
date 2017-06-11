package com.shutterfly.lta.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.shutterfly.lta.model.CustomerBean;

/*
 * 
 * Common DAO class for all database operations
 * 
 * Intentional singleton class to allow sharing of connection
 */

public class LTADao {
	
	private Connection connection;
	private PreparedStatement pstmt;
	private final String dbURL = "jdbc:derby://localhost:1527/LTA;user=root;password=root";
	private static LTADao instance;
	
	private String INSERT_CUSTOMER_QUERY = "INSERT INTO CUSTOMER (type,verb,key,event_time,last_name,adr_city,adr_state) VALUES "+
											" (?,?,?,?,?,?,?)";
	
	public LTADao() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        this.connection =  DriverManager.getConnection(dbURL);
	}
	
	public static LTADao getInstance() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return new LTADao();
	}
	
	public boolean insertCustomerRecord(CustomerBean customer){
		
		try {
			PreparedStatement pstmt = this.connection.prepareStatement(INSERT_CUSTOMER_QUERY);
			pstmt.setString(1, customer.getType());
			pstmt.setString(2, customer.getVerb());
			pstmt.setString(3, customer.getKey());
			pstmt.setString(4, customer.getLastName());
			pstmt.setString(5, customer.getAdrCity());
			pstmt.setString(6, customer.getAdrState());
			
			return pstmt.execute();
		} catch (SQLException e) {
			System.out.println(pstmt.toString());
			e.printStackTrace();
			System.out.println("Database error");
			return false;
		} finally{
			closeResources(pstmt);
		}
	}
	
	private void closeResources(PreparedStatement ps){
		try{
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in closing resources");
		}
	}
}
