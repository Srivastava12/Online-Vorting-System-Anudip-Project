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
		
		HttpSession session = request.getSession(true);
		
		String voterName = (String)session.getAttribute("name");
		String voterId = (String)session.getAttribute("id");
		String candidateNumber = request.getParameter("cnadidateNumber");
		
		String sqlQuery1 = "select voterName from votersname where voterId=?";
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(sqlQuery1);
			ps.setString(1, voterId);
			ResultSet voter = ps.executeQuery();
			
			if(voter.next()) {
				response.getWriter().println("Warning "+voterName+" Don't try to vote again....!");
			}else {
				
				String sqlQuery2 = "insert into votersname values(?,?)";
				PreparedStatement ps1 = conn.prepareStatement(sqlQuery2);
				ps1.setString(1, voterName);
				ps1.setString(2, voterId);
				
				String sqlQuery3 = "select total_Vote from numberofvotes where candidate_ID=?";
				PreparedStatement ps2 = conn.prepareStatement(sqlQuery3);
				ps2.setString(1, candidateNumber);
				ResultSet rs=ps2.executeQuery();
				
				if(rs.next()) {
					
					String votes = rs.getString("total_Vote");
					int vote = Integer.parseInt(votes);
					vote=vote+1;
					
					votes=vote+"";
					
					String sqlQuery4 = "update numberofvotes set total_Vote=? where candidate_ID=?";
					PreparedStatement ps3 = conn.prepareStatement(sqlQuery4);
					ps3.setString(1, votes);
					ps3.setString(2, candidateNumber);
					
					ps1.executeUpdate();
					ps3.executeUpdate();
					
				}else {
					
					String sqlQuery5 = "insert into numberofvotes value(?,?)";
					PreparedStatement ps4 = conn.prepareStatement(sqlQuery5);
					ps4.setString(1, candidateNumber);
					ps4.setString(2, "1");
					ps1.executeUpdate();
					ps4.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
