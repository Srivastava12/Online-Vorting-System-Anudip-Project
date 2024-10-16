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
		
		String mobNumber=request.getParameter("mobNumber");
		String password=request.getParameter("password");
		password=new PasswardManager().encryptPassword(password, 2);
		
		String voterQuery="select * from votersdetail where voterMob=?";
		
		try {	
			
			PreparedStatement voter=conn.prepareStatement(voterQuery);
	        voter.setString(1, mobNumber);
	        ResultSet voterResultSet = voter.executeQuery();
	    
	    	if(voterResultSet.next()) {
	    		if(voterResultSet.getString("password").equals(password)) {
	  
	    			HttpSession session = request.getSession();
	    			session.setAttribute("name", voterResultSet.getString("voterName"));
	    			session.setAttribute("id", voterResultSet.getString("voterId"));
	    			session.setAttribute("number", voterResultSet.getString("voterMob"));
	    			session.setAttribute("email", voterResultSet.getString("email"));
	    			
	    			ImageLoder il = new ImageLoder();
	    			il.getImage();
	    			
	    			RequestDispatcher rd = request.getRequestDispatcher("VoterHome.jsp");
		        	rd.forward(request, response);
	    		} 
	    		else
	    	    	response.getWriter().println("Wrong Passeord");
	    	}else {
	    		response.getWriter().println("Mobile Number Wrong / Maybe not registered........!");
	    	}
	    	
		} catch (Exception e) {
			
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
