import java.sql.*;
 
public class MyDB   {  
   
    private static String
    schema = "yeet",
    user   = "root",
    pass   = "bjlw1227",
    path   = "jdbc:mysql://localhost:3306/"+schema;
   
    public Connection getCon()
    throws InstantiationException, IllegalAccessException,ClassNotFoundException, SQLException {   
   
        Connection c;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        c = DriverManager.getConnection(path,user,pass);  
        return c;
    }
}