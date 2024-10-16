package com.servlets.candidate;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.servlets.utils.DBConnection;
import com.servlets.utils.DateTime;
import com.servlets.utils.SendOTP;

@MultipartConfig
@WebServlet("/CandidateRegister")
public class CandidateRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DateTime dt=null;
	
    public CandidateRegister() {
        super();
        dt=new DateTime();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Creating a new CandidateBean object to store voter details
		CandidateBean candidate = new CandidateBean();
				
		// Setting the Candidate's name from the form parameter 'name'
		candidate.setName(request.getParameter("name"));
		// Setting the Candidate's ID from the form parameter 'voterId'
		candidate.setId(request.getParameter("voterId"));
		// Setting the Candidate's mobile number from the form parameter 'moblieNo'
		candidate.setMobNo(request.getParameter("moblieNo"));
		// Setting the registration date and time (assumed dt is an instance of a DateTime utility)
		candidate.setRegDateTime(dt.getDateTime());
		// Setting the Candidate's gender from the form parameter 'gender'
		candidate.setGender(request.getParameter("gender"));
		// Setting the email id, from the form parameter 'email'
		candidate.setEmail(request.getParameter("email"));
		// Setting the Candidate's password from the form parameter 'password'
		candidate.setPassword(request.getParameter("password"));
		// Setting the confirmation password from the form parameter 'cpassword'
		candidate.setPasswordCon(request.getParameter("cpassword"));
				
		 try {
						 
			// Checking if the password and the confirmation password match
			 if (candidate.getPassword().equals(candidate.getPasswordCon())) {
				 
				 Connection conn=DBConnection.conn;
				 
		        String query="select votername from votersdetail where voterMob=?";
		        PreparedStatement ps=conn.prepareStatement(query);
		        ps.setString(1, request.getParameter("moblieNo"));
		        ResultSet rs=ps.executeQuery();
		        
		        if(!rs.next()) {
					
					PreparedStatement ps1=conn.prepareStatement("insert into candidateimage values(?,?)");
					
					Part file=(Part)request.getPart("image");
					InputStream is = file.getInputStream();
					byte [] data = new byte[is.available()];
					is.read(data);
					
					ps1.setString(1, candidate.getMobNo());
					ps1.setBytes(2, data);
					ps1.executeUpdate();
					
		        	// Creating an instance of SendOTP class to handle OTP sending
		        	SendOTP otpObject = new SendOTP();
				   
		        	// Sending an OTP to the voter's mobile number and storing the OTP in the voter object
		        	String otp = otpObject.sendOTP(candidate.getMobNo());
		        	candidate.setOtp(otp);
				   
		        	// Starting a new session and storing the voter object in the session
		        	HttpSession session = request.getSession();
		        	session.setAttribute("candidate", candidate);
				   
		        	// Forwarding the request to the 'OTPVerification' page to proceed with OTP verification
		        	RequestDispatcher rd = request.getRequestDispatcher("CdOtpVerification");
		        	rd.forward(request, response);
		        }else {
		        	response.getWriter().println(rs.getString("votername")+" you have already have register as a voter so you can not register your self as a condidate.....!");
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
