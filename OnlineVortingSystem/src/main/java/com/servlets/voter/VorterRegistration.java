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
import java.sql.ResultSet;

import com.servlets.utils.DBConnection;
import com.servlets.utils.DateTime;
import com.servlets.utils.SendOTP;

/**
 * Servlet implementation class VorterRegistration
 */
@WebServlet("/VorterRegistration")
public class VorterRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DateTime dt=null;
	
    public VorterRegistration() {
        super();
        dt=new DateTime();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        // Creating a new VoterBean object to store voter details
				VoterBean voter = new VoterBean();
				
				// Setting the voter's name from the form parameter 'name'
				voter.setVoterName(request.getParameter("name"));
				// Setting the voter's ID from the form parameter 'voterId'
				voter.setVoterId(request.getParameter("voterId"));
				// Setting the voter's mobile number from the form parameter 'moblieNo'
				voter.setVoterMob(request.getParameter("moblieNo"));
				// Setting the registration date and time (assumed dt is an instance of a DateTime utility)
				voter.setRegDateTime(dt.getDateTime());
				// Setting the voter's gender from the form parameter 'gender'
				voter.setGender(request.getParameter("gender"));
				// Setting the email id, from the form parameter 'email'
				voter.setEmail(request.getParameter("email"));
				// Setting the voter's password from the form parameter 'password'
				voter.setPassword(request.getParameter("password"));
				// Setting the confirmation password from the form parameter 'cpassword'
				voter.setPasswordCon(request.getParameter("cpassword"));
				
				try {
					// Checking if the password and the confirmation password match
					if (voter.getPassword().equals(voter.getPasswordCon())) {
						Connection conn=DBConnection.conn;
				        String query="select name from candidatesdetail where mobNumber=?";
				        PreparedStatement ps=conn.prepareStatement(query);
				        ps.setString(1, request.getParameter("moblieNo"));
				        ResultSet rs=ps.executeQuery();
				        if(!rs.next()) {
				        	// Creating an instance of SendOTP class to handle OTP sending
				        	SendOTP otpObject = new SendOTP();
				        	// Sending an OTP to the voter's mobile number and storing the OTP in the voter object
				        	String otp = otpObject.sendOTP(voter.getVoterMob());
				        	voter.setOtp(otp);
				        	// Starting a new session and storing the voter object in the session
				        	HttpSession session = request.getSession();
				        	session.setAttribute("voter", voter);
				        	// Forwarding the request to the 'OTPVerification' page to proceed with OTP verification
				        	RequestDispatcher rd = request.getRequestDispatcher("VtOtpVerification");
				        	rd.forward(request, response);
				        }else {
				        	response.getWriter().println(rs.getString("name")+" you have already have register as a Candidate so you can not register your self as a voter.....!");
				        }
					} 
				}catch(Exception e) {
					e.getStackTrace();
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
