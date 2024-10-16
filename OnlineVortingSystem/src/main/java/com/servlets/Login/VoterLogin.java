package com.servlets.Login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.servlets.utils.DBConnection;
import com.servlets.utils.ImageLoder;
import com.servlets.utils.PasswardManager;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class VoterLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection conn=null;
	
    public VoterLogin() {
        super();
        conn=DBConnection.conn;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Retrieve mobile number and password from the HTTP request
		String mobNumber = request.getParameter("mobNumber");
		String password = request.getParameter("password");

		// Encrypt the password using the PasswardManager class and an encryption level of 2
		password = new PasswardManager().encryptPassword(password, 2);

		// SQL query to select voter details based on the mobile number
		String voterQuery = "select * from votersdetail where voterMob=?";

		try {
   		 // Prepare the SQL statement for executing the voter query
   		 PreparedStatement voter = conn.prepareStatement(voterQuery);
		 voter.setString(1, mobNumber); // Set the mobile number as the query parameter
    		 ResultSet voterResultSet = voter.executeQuery(); // Execute the query and get the result set

   		 // Check if a voter with the given mobile number exists
   		 if (voterResultSet.next()) {
       		    // Compare the stored password with the entered password
        	   if (voterResultSet.getString("password").equals(password)) {
           	      // If the password is correct, create a new session and store voter details
            	      HttpSession session = request.getSession();
            	      session.setAttribute("name", voterResultSet.getString("voterName")); // Store the voter's name
           	      session.setAttribute("id", voterResultSet.getString("voterId")); // Store the voter's ID
            	      session.setAttribute("number", voterResultSet.getString("voterMob")); // Store the voter's mobile number
            	      session.setAttribute("email", voterResultSet.getString("email")); // Store the voter's email
            
           	      // Create an instance of ImageLoader and call the getImage method
            	      ImageLoder il = new ImageLoder();
            	      il.getImage(); // Assuming this method fetches the voter's image (or performs some related task)

           	      // Forward the request and response to VoterHome.jsp
                      RequestDispatcher rd = request.getRequestDispatcher("VoterHome.jsp");
            	      rd.forward(request, response);
      		  } else {
            		// If the password is incorrect, display an error message
           		 response.getWriter().println("Wrong Password");
        	}
    		} else {
       		 // If no voter is found with the given mobile number, display an error message
        	response.getWriter().println("Mobile Number Wrong / Maybe not registered........!");
    		}
		} catch (Exception e) {
  		  // Print the exception message in case of an error
    		  response.getWriter().println(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
