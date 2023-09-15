package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {


	private static String bd="clinica";
	private static String parametros="?useSSl=false&serverTimezone=UTC";
	private static String user="root";
	private static String password="root";
	private static String url="jdbc:mysql://localhost:3306/"+bd+parametros;
	Connection conn = null;
	
	public DbConnection() throws SQLException {
		// TODO Auto-generated constructor stub
		conn=DriverManager.getConnection(url, user, password);
		System.err.println("Se he realizado la conexion");
	}

	public Connection getConnection() {
		return conn;
	}
	
	public Connection disconect() throws SQLException {
		
		if(conn!=null) {
			System.err.println("Clossing database ["+conn+"]...");
			conn.close();
			System.err.println("Disconected database ...");
		}
		return conn;

	}

	
	
	

}
