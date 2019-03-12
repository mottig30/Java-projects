
//STEP 1. Import required packages
import java.sql.*;

public class DataBase {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	Connection conn = null;
	Statement stmt = null;

	public DataBase() {
		dbConnection();
		createTable();
	}// Data Base

	public void dbConnection() {
		// STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// STEP 3: Open a connection
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// end main

	public void insertToTable(CrimeEvent c) {
		try {
			stmt = conn.createStatement();
			String sql;
			sql = " INSERT INTO policeevents VALUES";
			sql += "(";
			sql += c.getSerialNum();
			sql += " ,";
			sql += c.getEventLocation();
			sql += " ,";
			sql += c.getDistanceCalculated();
			sql += " ,";
			sql += c.getEventLevel();
			sql += " ,";
			sql += "'" + c.getAddress() + "'";
			sql += ");";
			stmt.executeUpdate(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}// insertToTable

	public void createTable() {
		try {
			stmt = conn.createStatement();

			String sql;
			sql = "DROP table IF EXISTS policeevents; ";
			stmt.executeUpdate(sql);
			sql = " CREATE TABLE policeevents  ( ID  INT NOT NULL,   Location  INT NULL,   Distance  INT NULL,   Severity  INT NULL,   Address  VARCHAR(50) NULL,  PRIMARY KEY ( ID ));";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// create Table
}// end FirstExample