//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.sql.ResultSet;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.PreparedStatement;
//
///**
// * Servlet implementation class login
// */
//@WebServlet("/login")
//public class login extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public login() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//		String user = request.getParameter("username");
//		String pass = request.getParameter("password");
//		// Connect to mysql and verify username password
//		
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		
//			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "bjlw1227");
//			
//			//PreparedStatement ps = c.prepareStatement("select username,password from student where userame=? and password=?");
//			PreparedStatement ps = c.prepareStatement("INSERT INTO `logindatabase`.`user` (`username`) VALUES ('8');");
//
////			ps.setString(1, user);
////			ps.setString(2, pass);
//	 
//			ResultSet rs = ps.executeQuery();
//	 
//			while (rs.next()) {
//				response.sendRedirect("success.html");
//				return;
//			}
//			response.sendRedirect("error.html");
//			return;
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//		if(user.equals("admin") && pass.equals("admin")) {
//			response.sendRedirect("success.html");
//			return;
//		}
//		else {
//			response.sendRedirect("error.html");
//			return;
//		}
//	}
//
//}

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
    private static String
    database = "yeet",
    table    = "account"   ,
    Email  ,
    Pass   ;
   
   
   
   
   
    /*  loginUser() description:
     *
     *  establishes a connection with the database "ecinema",
     *  creates a user table, if one does not already exist ,
     *  checks if first name, last name, email, and password are valid,
     *  then attempts to add the user information to the user table.
     */
   
    private static void loginUser() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {   
    	c=db.getCon();
       
        if (!validateCredentials())return;
       
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
        boolean status=false;
   
        // check if any field is empty
 
        if (Email.isEmpty()||Pass .isEmpty()) {   
        	System.out.println("ERROR: All fields are not complete");
            return false;  
        }
   
        // finds user email in the user table
   
        PreparedStatement ps1= c.prepareStatement("SELECT EMAIL FROM user");
        ResultSet r1 = ps1.executeQuery();
        int a=0;
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
        for (int i=0;i<a;i++)   s=r2.getString("PASSWORD");
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
 
    public static void main(String[] args)
    throws SQLException, InstantiationException,
    IllegalAccessException, ClassNotFoundException
    {  
        Email = "eric6cho@gmail.com";  
        Pass  = "RealPassword";    
 
        loginUser();
}   
}