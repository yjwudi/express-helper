package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class UserDAO {
	/**
	 * Statement���� 
	 */
	private Statement state = null;

	/**
	 * PreparedStatement����
	 */
	private PreparedStatement preState = null;

	/**
	 * ������
	 */
	private ResultSet resultSet = null;

	/** 
	 * ��ݿ����������
	 */
	private Connection connection = null;


	private DBManager dbManager = null;

	public UserDAO(){}
	
	public boolean CheckUser(String name) throws SQLException, IllegalAccessException, ClassNotFoundException{
		
		if(name==null||name.length()==0)
			return false;
		connection = DBManager.getConnection();
		
		String sqlState = "SELECT * FROM user WHERE name = '" + name + "'";

			state = connection.createStatement();
			resultSet = state.executeQuery(sqlState);
		   if(resultSet.next())
		   {
			    
			   DBManager.closeAll(connection, state, resultSet);
			   return true;
		   }
		   else 
		   {
			   DBManager.closeAll(connection, state, resultSet);
			   return false;
		   }
	} 
	
	public boolean RegisterUser(String name,String password, String tel) throws SQLException, IllegalAccessException, ClassNotFoundException
	{
		if(name==null||name.length()==0||tel==null||tel.length()!=11)
			return false;
		if(CheckUser(name))
			return false;
		if(password==null||password.length()==0)
			return false;
		
		connection= DBManager.getConnection(); //     
		StringBuffer sqlState = new StringBuffer();
		sqlState.append("INSERT INTO user(name,password,telephone)");
		sqlState.append(" VALUES(?,?,?)");
		//System.out.println(sqlState);
		try {

			preState = connection.prepareStatement(sqlState.toString()); 
			preState.setString(1, name); //
			preState.setString(2, password);
			preState.setString(3, tel);

		preState.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeAll(connection, preState, resultSet);
			return true;
		}
		
	}
	public boolean Login(String name,String password) throws IllegalAccessException, ClassNotFoundException, SQLException
	{
		if(name==null||name.length()==0)
			return false;
		if(password==null||password.length()==0)
			return false;
		
		connection = DBManager.getConnection();
		
		String sqlState = "SELECT password FROM user WHERE name = '" + name + "'";

			state = connection.createStatement();
			resultSet = state.executeQuery(sqlState);
		   if(resultSet.next())
		   {
			    
			   //DBManager.closeAll(connection, state, resultSet);
			   String thePassword = resultSet.getString("password");
			   if(password.equals(thePassword))
				   return true;
			   else
				   return false;
		   }
		   else 
		   {
			   DBManager.closeAll(connection, state, resultSet);
			   return false;
		   }
	}

	
	public boolean ManagerLogin(String name,String password) throws IllegalAccessException, ClassNotFoundException, SQLException
	{
		if(name==null||name.length()==0)
			return false;
		if(password==null||password.length()==0)
			return false;
		if(name.equals("JoinWei")&&password=="11111")
			return true;
		else return false;
	}
	
}