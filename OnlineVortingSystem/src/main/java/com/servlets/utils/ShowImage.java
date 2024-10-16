package com.servlets.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

/**
 * Servlet implementation class ShowImage
 */
@WebServlet("/ShowImage")
public class ShowImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn=null;
    public ShowImage() {
        super();
        conn=DBConnection.conn;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			response.setContentType("image/gif");
			HttpSession session=request.getSession(true);
			String cnadidateNumber = (String) session.getAttribute("number");
			
			byte[] image=null;
			ServletOutputStream sos=null;
			
		    String sqlQuery ="select images from candidateimage where candidateNumber=?";
			
			PreparedStatement ps = conn.prepareStatement(sqlQuery);
    		ps.setString(1,cnadidateNumber);
    		
    		ResultSet candidateImage=ps.executeQuery();
    		
    		while(candidateImage.next()) {
               image = candidateImage.getBytes("images");
               sos = response.getOutputStream();
               sos.write(image);
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
