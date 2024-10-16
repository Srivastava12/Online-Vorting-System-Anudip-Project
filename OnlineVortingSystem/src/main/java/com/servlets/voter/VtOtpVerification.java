package com.servlets.voter;

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
import java.sql.SQLException;

import com.servlets.utils.DBConnection;
import com.servlets.utils.PasswardManager;

/**
 * Servlet implementation class VtOtpVerification
 */
@WebServlet("/VtOtpVerification")
public class VtOtpVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn=null;
	
    public VtOtpVerification() {
        super();
        conn=DBConnection.conn;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieving the existing HttpSession without creating a new one if it doesn't exist
				HttpSession session = request.getSession(false);
				// Getting the 'voter' attribute from the session, which should be a VoterBean object
				VoterBean voter=(VoterBean) session.getAttribute("voter");
				
				// Variable to store the OTP entered by the user
				String userOtp="";
				// Loop through 5 times to retrieve the OTP input by the user from the form fields
				for(int i=1;i<=5;i++) 
				    // Concatenating the user's input for each digit of the OTP to the 'userOtp' string
				   userOtp=userOtp+request.getParameter(i+"");
				
				// Checking if the OTP entered by the user matches the OTP stored in the VoterBean object
				if(userOtp.equals(voter.getOtp())) {
					
			    	try {
			    		
			            // Preparing a SQL query to insert the voter details into the 'votersdetails' table
						PreparedStatement ps=conn.prepareStatement("insert into VotersDetail values(?,?,?,?,?,?,?)");
						
				        // Setting the values for each column in the prepared statement
						ps.setString(1,voter.getVoterId() );
						ps.setString(2, voter.getVoterName());
						ps.setString(3, voter.getVoterMob());
						ps.setString(4, voter.getRegDateTime());
						ps.setString(5, voter.getGender());
						
				        // Encrypting the voter's password using a PasswordManager and setting it in the prepared statement
						String pass=new PasswardManager().encryptPassword(voter.getPassword(),2);
						ps.setString(6, pass); // Encrypted Password
						
				        // Setting the email id from the VoterBean object
						ps.setString(7,voter.getEmail()); // Ward Number
						
				        // Executing the update to insert the voter details into the database
						ps.executeUpdate();
						
				        // Forwarding the request to the 'RegistrationSuccess.html' page after successful registration
						RequestDispatcher rd1=request.getRequestDispatcher("RegistrationSucces.html");
					    rd1.forward(request, response);
					    
					} catch (SQLException e) {
						// Catching and printing any SQL exceptions that occur during the process
						e.printStackTrace();
					}
			    }else {
			        // If the OTP doesn't match, forward the request back to the OTP verification page
			    	RequestDispatcher rd1=request.getRequestDispatcher("VtOTPPage.html");
				    rd1.forward(request, response);  
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
