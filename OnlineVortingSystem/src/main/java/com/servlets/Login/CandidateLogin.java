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
		
		String mobNumber=request.getParameter("mobNumber");
		String password=request.getParameter("password");
		password=new PasswardManager().encryptPassword(password, 2);
		
		String sqlQuery="select * from candidatesdetail where mobNumber=?";
		String sqlQuery1="select total_Vote from numberofvotes where candidate_ID=?";
		String sqlQuery2="select total_Vote from numberofvotes";
		try {
			
			PreparedStatement ps=conn.prepareStatement(sqlQuery);
	        ps.setString(1, mobNumber);
	        ResultSet candidateResultSet = ps.executeQuery();
	        
	        PreparedStatement ps1=conn.prepareStatement(sqlQuery1);
	        ps1.setString(1, mobNumber);
	        ResultSet totalvote = ps1.executeQuery();
	        int totalVote;
	        if(totalvote.next())
	        	totalVote = Integer.parseInt(totalvote.getString("total_Vote"));
	        else
	        	totalVote=0;
	        PreparedStatement ps2=conn.prepareStatement(sqlQuery2);
	        ResultSet ovrallVote = ps2.executeQuery();
	        int ovrallVotes=0;
	        while(ovrallVote.next()) {
	        	ovrallVotes=ovrallVotes+Integer.parseInt(ovrallVote.getString("total_Vote"));
	        }
	        
	        int vortingPercentag = (totalVote/ovrallVotes)*100;
	        
	        String totalVoteS=totalVote+"";
	    	if(candidateResultSet.next()) {
	    		
	    		if(candidateResultSet.getString("password").equals(password)) {
	  
	    			HttpSession session = request.getSession();
	    			session.setAttribute("name", candidateResultSet.getString("name"));
	    			session.setAttribute("id", candidateResultSet.getString("Id"));
	    			session.setAttribute("number", candidateResultSet.getString("mobNumber"));
	    			session.setAttribute("email", candidateResultSet.getString("email"));
	    			session.setAttribute("totalVort",totalVoteS);
	    			session.setAttribute("vortingPercentag",vortingPercentag);
	    			
	    			RequestDispatcher rd = request.getRequestDispatcher("CandidateHome.jsp");
		        	rd.forward(request, response);
	    		} 
	    		else
	    	    	response.getWriter().println("Wrong Passeord");
	    	}else {
	    		response.getWriter().println("Mobile Number Wrong / Maybe not registered........!");
	    	}
	    	
		}catch(Exception e) {
			e.getStackTrace();
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
