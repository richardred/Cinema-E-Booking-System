import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ConnectDB {
	
	static Connection connection = null;
	static String databaseName = "";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	static String username = "root";
	static String password = "bjlw1227";
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(url, username, password);
		PreparedStatement ps = connection.prepareStatement("INSERT INTO `logindatabase`.`user` (`username`) VALUES ('6');");
		
		int status = ps.executeUpdate();
		
		if (status != 0) {
			System.out.println("Database was connected!");
			System.out.println("Record was inserted.");
		}
		else {
			
		}
	}
}