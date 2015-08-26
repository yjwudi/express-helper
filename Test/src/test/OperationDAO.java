package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class OperationDAO {

	/**
	 * Statement����
	 */
	private Statement state = null;
	private Statement state2 = null;

	/**
	 * PreparedStatement����
	 */
	private PreparedStatement preState = null;

	/**
	 * ������
	 */
	private ResultSet resultSet = null;
	private ResultSet resultSet2 = null;

	/**
	 * ��ݿ����������
	 */
	private Connection connection = null;

	private DBManager dbManager = null;

	public OperationDAO() {
	}

	public boolean UserOperation(String userName, String telephone,
			String express, String to, String from, String pay, String time)
			throws IllegalAccessException, ClassNotFoundException, SQLException {

		if (userName == null || userName.length() == 0)
			return false;
		if (to == null || to.length() == 0)
			return false;
		if (from == null || from.length() == 0)
			return false;
		if (pay == null || pay.length() == 0)
			return false;
		connection = DBManager.getConnection();

		StringBuffer sqlState = new StringBuffer();
		sqlState.append("INSERT INTO information(userName,telephone,express,destinationEnd, destinationBegin,pay,time,isFinish)");
		sqlState.append(" VALUES(?,?,?,?,?,?,?,?)");
		String unFinish = "NO";
		try {

			preState = connection.prepareStatement(sqlState.toString());
			preState.setString(1, userName);
			preState.setString(2, telephone);
			preState.setString(3, express);
			preState.setString(4, to);
			preState.setString(5, from);
			preState.setString(6, pay);
			preState.setString(7, time);
			preState.setString(8, unFinish);

			preState.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeAll(connection, preState, resultSet);
			return true;
		}

	}

	public String ManagerOperation() throws SQLException,
			IllegalAccessException, ClassNotFoundException {
		connection = DBManager.getConnection();
		String array = new String();
		String sqlState = "SELECT * FROM information WHERE isFinish = '" + "NO"
				+ "'";

		state = connection.createStatement();
		resultSet = state.executeQuery(sqlState);
		while (resultSet.next()) {
			String name = resultSet.getString("userName");
			String to = resultSet.getString("destinationEnd");
			String from = resultSet.getString("destinationBegin");
			String pay = resultSet.getString("pay");
			String time = resultSet.getString("time");
			String tel = resultSet.getString("telephone");
			String re = name + "|" + to + "|" + from + "|" + pay + "|" + tel
					+ "|" + time;
			array = array + "&" + re;
		}
		// DBManager.closeAll(connection, preState, resultSet);
		return array;
	}

	public String[] getRecord(String userName) throws IllegalAccessException,
			ClassNotFoundException, SQLException {
System.out.println(userName);
		DBManager.closeAll(connection, preState, resultSet);
		connection = DBManager.getConnection();
		String sqlState = "SELECT * FROM information WHERE userName = '"
				+ userName + "'";
		String[] record = new String[10];
		state = connection.createStatement();
		resultSet = state.executeQuery(sqlState);
		while (resultSet.next()) {
		record[0] = resultSet.getString("userName");
System.out.println(record[0]);
		record[1] = resultSet.getString("destinationEnd");
		record[2] = resultSet.getString("destinationBegin");
		record[3] = resultSet.getString("pay");
		record[4]= resultSet.getString("time");
		record[5] = resultSet.getString("telephone");
		record[6] = resultSet.getString("express");
		break;
		}
		return record;
	}

	public boolean ManagerRelease(String userName) throws IllegalAccessException,
			ClassNotFoundException, SQLException {
		String[] service = getRecord(userName);
		System.out.println("kdkdkk " + service[5]);
		DBManager.closeAll(connection, preState, resultSet);
		connection = DBManager.getConnection();
		StringBuffer sqlState = new StringBuffer();
		sqlState.append("INSERT INTO so.release(userName,telephone,express,destinationEnd, destinationBegin,pay,time)");
		sqlState.append(" VALUES(?,?,?,?,?,?,?)");
		try {

			preState = connection.prepareStatement(sqlState.toString());
			preState.setString(1, service[0]);
			preState.setString(2, service[5]);
			preState.setString(3, service[6]);
			preState.setString(4, service[1]);
			preState.setString(5, service[2]);
			preState.setString(6, service[3]);
			preState.setString(7, service[4]);

			preState.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeAll(connection, preState, resultSet);
			return true;
		}
	}
	/*
	 * public boolean ManagerMakeSure(int id) throws IllegalAccessException,
	 * ClassNotFoundException, SQLException { connection =
	 * DBManager.getConnection(); String sql = "update students set isFinish='"
	 * +"Yes"+ "' where id='" + id + "'";
	 * 
	 * try { preState = (PreparedStatement) connection.prepareStatement(sql);
	 * preState.executeUpdate();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } return true; }
	 */

}
