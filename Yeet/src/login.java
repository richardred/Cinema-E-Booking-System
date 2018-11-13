import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.*;
import java.sql.*;
 
@SuppressWarnings("serial")
public class login extends HttpServlet {  
    private static MyDB db = new MyDB();
    private static Connection c = null;
    private static String database = "yeet", table = "account";
    private static String Email, Pass;
    
    /*  loginUser() description:
     *
     *  establishes a connection with the database "ecinema",
     *  creates a user table, if one does not already exist ,
     *  checks if first name, last name, email, and password are valid,
     *  then attempts to add the user information to the user table.
     */
   
    private static void loginUser() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {   
    	c=db.getCon();
        if (!validateCredentials()) return;
        
        //update database so that the user is considered to be logged in
        System.out.println("SUCCESS: user registered.");   
    }
    
     /* validateCredentials() description:
     *  checks if all String fields satisfy conditions:
     *  1. all fields must be non-empty
     *  2. email must exist in table
     *  3. password must match the user password in the table
     */
   
    private static boolean validateCredentials() throws SQLException {  
        boolean status = false;
   
        // check if any field is empty
        if (Email.isEmpty()||Pass .isEmpty()) {   
        	System.out.println("ERROR: All fields are not complete");
            return false;  
        }
   
        // finds user email in the user table
        PreparedStatement ps1= c.prepareStatement("SELECT EMAIL FROM user");
        ResultSet r1 = ps1.executeQuery();
        int a = 0;
        while (r1.next()) { 
        	a++;
            if (r1.getString("EMAIL").equals(Email)) {
            	System.out.println("SUCCESS: Email has been found in the database.");
                status = true;
            }  
        }
       
        // checks password for user email
        PreparedStatement ps2 = c.prepareStatement("SELECT PASSWORD FROM user");
        ResultSet r2 = ps2.executeQuery();
        String s="";
        for (int i=0;i<a;i++)
        	s=r2.getString("PASSWORD");
        if (s.equals(Pass)) {
        	System.out.println("SUCCESS: Password is correct.");
            status = true;
        }
        return status;
    }
   
    protected void processRequest (HttpServletRequest request, HttpServletResponse response)
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
            loginUser();
           
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
    	}
        catch (InstantiationException|IllegalAccessException | ClassNotFoundException|SQLException e) {
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
    	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
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
 
    public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {  
        Email = "eric6cho@gmail.com";  
        Pass  = "RealPassword";
        
        loginUser();
    }   
}