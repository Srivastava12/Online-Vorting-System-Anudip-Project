package com.servlets.candidate;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.servlets.utils.DBConnection;
import com.servlets.utils.PasswardManager;

/**
 * Servlet implementation class CdOtpVerification
 */
@MultipartConfig
@WebServlet("/CdOtpVerification")
public class CdOtpVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 Connection conn=null;
	 
    public CdOtpVerification() {
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
		CandidateBean candidate=(CandidateBean) session.getAttribute("candidate");
						
		// Variable to store the OTP entered by the user
		String userOtp="";
		// Loop through 5 times to retrieve the OTP input by the user from the form fields
		for(int i=1;i<=5;i++) 
		// Concatenating the user's input for each digit of the OTP to the 'userOtp' string
		userOtp=userOtp+request.getParameter(i+"");
						
		// Checking if the OTP entered by the user matches the OTP stored in the VoterBean object
		if(userOtp.equals(candidate.getOtp())) {
					
		try {
					    		
			// Preparing a SQL query to insert the voter details into the 'Candidatesdetail' table
			PreparedStatement ps=conn.prepareStatement("insert into candidatesdetail values(?,?,?,?,?,?,?,?)");
			
			
								
			// Setting the values for each column in the prepared statement
			ps.setString(1, candidate.getId() );
			ps.setString(2, candidate.getName());
			ps.setString(3, candidate.getMobNo());
			ps.setString(4, candidate.getRegDateTime());
			ps.setString(5, candidate.getGender());
								
			// Encrypting the candidate's password using a PasswordManager and setting it in the prepared statement
			String pass=new PasswardManager().encryptPassword(candidate.getPassword(),2);
			ps.setString(6, pass); // Encrypted Password
								
			// Setting the email id from the candidateBean object
			ps.setString(7,candidate.getEmail()); // Ward Number
			     
			ps.setString(8,"");
			// Executing the update to insert the candidate details into the database
			
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
				RequestDispatcher rd1=request.getRequestDispatcher("CdOTPPage.html");
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
