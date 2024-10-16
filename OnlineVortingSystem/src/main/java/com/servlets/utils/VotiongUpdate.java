package com.servlets.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

/**
 * Servlet implementation class VotiongUpdate
 */
@WebServlet("/VotiongUpdate")
public class VotiongUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection conn=null;
    public VotiongUpdate() {
        super();
        conn=DBConnection.conn;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Retrieve the current HTTP session or create a new one if it doesn't exist
		HttpSession session = request.getSession(true);

		// Retrieve voter details from the session (voter's name and ID)
		String voterName = (String) session.getAttribute("name");
		String voterId = (String) session.getAttribute("id");

		// Retrieve the candidate's number (ID) from the request parameter
		String candidateNumber = request.getParameter("cnadidateNumber");

		// SQL query to check if the voter has already voted by looking up their voter ID
		String sqlQuery1 = "select voterName from votersname where voterId=?";

		try {
   		  // Prepare the SQL query to check if the voter has already voted
    		  PreparedStatement ps = conn.prepareStatement(sqlQuery1);
  		  ps.setString(1, voterId); // Set the voterId as the query parameter
    		  ResultSet voter = ps.executeQuery(); // Execute the query

   		  // Check if the voter exists in the votersname table (i.e., has already voted)
    		     if (voter.next()) {
        	 // If the voter has already voted, display a warning message
      		    response.getWriter().println("Warning " + voterName + " Don't try to vote again....!");
    		    } else {
        		// If the voter hasn't voted yet, insert the voter's name and ID into the votersname table
        		String sqlQuery2 = "insert into votersname values(?,?)";
        		PreparedStatement ps1 = conn.prepareStatement(sqlQuery2);
      			  ps1.setString(1, voterName); // Insert the voter's name
        		  ps1.setString(2, voterId); // Insert the voter's ID

       			 // SQL query to retrieve the current total number of votes for the selected candidate
        		String sqlQuery3 = "select total_Vote from numberofvotes where candidate_ID=?";
        		PreparedStatement ps2 = conn.prepareStatement(sqlQuery3);
        		ps2.setString(1, candidateNumber); // Set the candidate's ID as the query parameter
       			ResultSet rs = ps2.executeQuery(); // Execute the query

        		// If the candidate has received votes before, update the vote count
        	 if (rs.next()) {
            		// Get the current total vote count for the candidate
            		String votes = rs.getString("total_Vote");
            		int vote = Integer.parseInt(votes); // Convert vote count from string to integer
            		vote = vote + 1; // Increment the vote count by 1

           		 // Convert the updated vote count back to string
          		  votes = vote + "";

         		 // SQL query to update the total vote count for the candidate
            		String sqlQuery4 = "update numberofvotes set total_Vote=? where candidate_ID=?";
            		PreparedStatement ps3 = conn.prepareStatement(sqlQuery4);
            		ps3.setString(1, votes); // Set the updated vote count
            		ps3.setString(2, candidateNumber); // Set the candidate's ID

            		// Execute the updates: one for the voter's record, one for the vote count
          		ps1.executeUpdate();
            		ps3.executeUpdate();

       		 } else {
           		 // If this is the first vote for the candidate, insert a new record with the candidate's ID and 1 vote
            		String sqlQuery5 = "insert into numberofvotes value(?,?)";
            		PreparedStatement ps4 = conn.prepareStatement(sqlQuery5);
            		ps4.setString(1, candidateNumber); // Set the candidate's ID
            		ps4.setString(2, "1"); // Set the initial vote count to 1

            		// Execute the updates: one for the voter's record, one for the new vote entry
         		ps1.executeUpdate();
            		ps4.executeUpdate();
        	}
    		}
		} catch (Exception e) {
 		   // Print the stack trace if an exception occurs
  		  e.printStackTrace();
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
