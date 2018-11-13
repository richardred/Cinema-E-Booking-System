import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.mail.MessagingException;
import javax.servlet.*;
import java.sql.*;
 
@SuppressWarnings("serial")
public class signup extends HttpServlet {  
	private static MyDB db = new MyDB();
    private static Connection c = null;
    private static String 
    database = "yeet",
    table    = "account",
    NameF, NameL, Email, EmailC, Pass, PassC;
    private static int UserID;
    private static int IsSubbed;
   
    /*  registerUser() description:
     *
     *  establishes a connection with the database "ecinema",
     *  creates a user table, if one does not already exist ,
     *  checks if first name, last name, email, and password are valid,
     *  then attempts to add the user information to the user table.
     */
   
    private static void registerUser() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, MessagingException {    
        c=db.getCon();
    
        createUserTable();
        
        if(!validFields()) return;
            
        PreparedStatement ps = c.prepareStatement
        ("INSERT INTO " + table
        +"(USERID,EMAIL,PASSWORD,ISLOGGEDIN,ISVERIFIED,FNAME,LNAME,ISSUBBED,ADDRESS,CITY,STATE,ZIP) VALUES "
        +"('"+UserID+"','"+Email+"','"+Pass+"',FALSE,FALSE,'"+NameF+"','"+NameL+"','"+IsSubbed+"',NULL,NULL,NULL,NULL);");
        
        ps.executeUpdate();
        System.out.println("SUCCESS: user registered.");
        Mail m = new Mail();
        m.send_email("richardred.q@gmail.com", "testsubject", "yeet");
    }
   
    /*     createUserTable() description:
     * 
     *  if a "user" table does not exist in the "ecinema" database,
     *  a new "user" table is created, which contains columns:
     *    
     *    USERID     : unique user ID
     *    EMAIL      : user email addresses
     *    PASSWORD   : user passwords
     *    ISLOGGEDIN : indicates if user is currently logged in
     *    ISVERIFIED : indicates if user has verified  email
     *    FNAME      : user first names
     *    LNAME      : user last names
     *    ISSUBBED   : indicates if user is subscribed to promotions
     *    ADDRESS    : user residence street address
     *    CITY       : user residence city
     *    STATE      : user residence state
     *    ZIP           : user residence zip code
     */
    
    private static void createUserTable() throws SQLException {    
    	DatabaseMetaData dbm = c.getMetaData();
        ResultSet r = dbm.getTables(null, null, table, null);
        if (!r.next()) {    
            PreparedStatement ps0  = c.prepareStatement(
            	"CREATE TABLE `yeet`.`account` ("
				  +"`USERID` INT primary key NOT NULL AUTO_INCREMENT,"
				  +"`EMAIL` VARCHAR(45) NOT NULL,"
				  +"`PASSWORD` VARCHAR(45) NULL,"
				  +"`ISLOGGEDIN` TINYINT NULL,"
				  +"`ISVERIFIED` TINYINT NULL,"
				  +"`FNAME` VARCHAR(45) NULL,"
				  +"`LNAME` VARCHAR(45) NULL,"
				  +"`ISSUBBED` TINYINT NULL,"
				  +"`ADDRESS` VARCHAR(45) NULL,"
				  +"`CITY` VARCHAR(45) NULL,"
				  +"`STATE` VARCHAR(45) NULL,"
				  +"`ZIP` INT NULL);");
            ps0.executeUpdate();
            System.out.println("SUCCESS: New user table created.");
        }    
        
      
    }

    /*  validFields() description:
     *  checks if all String fields satisfy conditions:
     *  1. all fields must be non-empty
     *  2. email and confirm email must match
     *  3. password and confirm password must match
     *  4. email must not already exit in the database
     */
   
    private static boolean validFields() throws SQLException {  
        //check if there is an empty field
        if (NameF.isEmpty()||Email .isEmpty()||Pass .isEmpty()||
            NameL.isEmpty()||EmailC.isEmpty()||PassC.isEmpty()) {
        	System.out.println("ERROR: All fields are not complete");
            return false;  
        }
       
        // check if the given emails match
        if (!Email.equals(EmailC)) {
        	System.out.println("ERROR: Emails do not match.");
            return false;      
        }
        
        // check if the given passwords match
        if (!Pass.equals(PassC)) {
        	System.out.println("ERROR: Passwords do not match.");
            return false;  
        }
        
        // check if email is in the user database
        PreparedStatement ps= c.prepareStatement("SELECT EMAIL FROM account");
        ResultSet r = ps.executeQuery();
        while (r.next())
            if (r.getString("EMAIL").equals(Email)) {
            	System.out.println("ERROR: Email is already in use.");
                return false;
            }
        return true;
    }
   
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
    throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {  
           
            /*
            NameF =request.getParameter("FirstName");
            NameL =request.getParameter("LastName" );
            Email =request.getParameter("Email"    );
            EmailC=request.getParameter("EmailC"   );
            Pass  =request.getParameter("Password" );
            PassC =request.getParameter("PasswordC");
            out.println("output test");
           registerUser();
           
           */
        }
    }
       
    /**Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   
    	try {
    		processRequest(request, response);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
        	e.printStackTrace();
        }  
    }
   
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		processRequest(request, response);
    	} catch (InstantiationException|IllegalAccessException | ClassNotFoundException | SQLException e) {
    		e.printStackTrace();
    	}  
    }
 
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
 
    public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, MessagingException {     
        UserID    = 0;
        Email   = "eric6cho@gmail.com";    
        EmailC  = "eric6cho@gmail.com";
        Pass    = "RealPassword";                
        PassC   = "RealPassword";
        NameF   = "Eric";
        NameL   = "Cho";
        IsSubbed= 0;
        
        registerUser();
    }    
}