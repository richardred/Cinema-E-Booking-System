import java.sql.*;

public class MyDB
{    public Connection getCon() 
    throws InstantiationException, IllegalAccessException, 
    ClassNotFoundException, SQLException 
    {    Connection c;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        c = DriverManager.getConnection
            ("jdbc:mysql://localhost:3306/yeet","root","bjlw1227");  
        return c;
    }
}