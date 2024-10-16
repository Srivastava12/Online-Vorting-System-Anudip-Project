package com.servlets.Login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

import com.servlets.utils.DBConnection;
import com.servlets.utils.PasswardManager;

@WebServlet("/CandidateLogin")
public class CandidateLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn=null;
	
    public CandidateLogin() {
        super();
        conn=DBConnection.conn;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Retrieve mobile number and password from the HTTP request
		String mobNumber = request.getParameter("mobNumber");
		String password = request.getParameter("password");

		// Encrypt the user's password using a custom PasswordManager class and encryption level 2
		password = new PasswardManager().encryptPassword(password, 2);

		// SQL queries for retrieving candidate details and vote counts
		String sqlQuery = "select * from candidatesdetail where mobNumber=?";
		String sqlQuery1 = "select total_Vote from numberofvotes where candidate_ID=?";
		String sqlQuery2 = "select total_Vote from numberofvotes";

		try {
  		     // Prepare and execute the first query to get the candidate details based on mobile number
    		     PreparedStatement ps = conn.prepareStatement(sqlQuery);
    		     ps.setString(1, mobNumber);
    		     ResultSet candidateResultSet = ps.executeQuery();

   		     // Prepare and execute the second query to get the total votes for the specific candidate
   		     PreparedStatement ps1 = conn.prepareStatement(sqlQuery1);
    		     ps1.setString(1, mobNumber);
    		     ResultSet totalvote = ps1.executeQuery();
    
  		     int totalVote;
    
    		     // If the candidate has votes, retrieve them, otherwise set to 0
    		     if (totalvote.next()) {
        		totalVote = Integer.parseInt(totalvote.getString("total_Vote"));
   		     } else {
                        totalVote = 0;
   		     }

    		     // Prepare and execute the third query to get the total votes across all candidates
    			PreparedStatement ps2 = conn.prepareStatement(sqlQuery2);
    			ResultSet ovrallVote = ps2.executeQuery();
    
   			int ovrallVotes = 0;
    
    			// Calculate the overall vote count by summing up the total votes from all candidates
    			while (ovrallVote.next()) {
        		ovrallVotes += Integer.parseInt(ovrallVote.getString("total_Vote"));
    			}

    			// Calculate the voting percentage for the candidate (candidate's votes divided by overall votes, multiplied by 100)
    			int votingPercentage = (totalVote * 100) / ovrallVotes;

    			// Convert totalVote to a string for storing in session
    			String totalVoteS = totalVote + "";

    			// Check if the candidate exists in the result set from the first query
    			if (candidateResultSet.next()) {
        
       	 		// Compare the encrypted password from the database with the entered encrypted password
        		if (candidateResultSet.getString("password").equals(password)) {
            
           		       // If password is correct, create a new session and store candidate details
            		       HttpSession session = request.getSession();
            		       session.setAttribute("name", candidateResultSet.getString("name"));
            		       session.setAttribute("id", candidateResultSet.getString("Id"));
            		       session.setAttribute("number", candidateResultSet.getString("mobNumber"));
            		       session.setAttribute("email", candidateResultSet.getString("email"));
            		       session.setAttribute("totalVote", totalVoteS);
            		       session.setAttribute("votingPercentage", votingPercentage);
            
            			// Forward the request and response to CandidateHome.jsp
            			RequestDispatcher rd = request.getRequestDispatcher("CandidateHome.jsp");
            			rd.forward(request, response);
            
        		} else {
            			// If the password is incorrect, display an error message
            			response.getWriter().println("Wrong Password");
        		}
    			} else {
        		// If the mobile number is incorrect or the candidate is not registered, display an error message
        		response.getWriter().println("Mobile Number Wrong / Maybe not registered........!");
			}
		} catch (Exception e) {
    			// Print the stack trace if an exception occurs
    			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
