package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAbstract {
	public Connection getConnection() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore","bookstore","2620");
			return con;
		} catch (SQLException e) {
			System.out.println(e);
		}return null;
	}
}