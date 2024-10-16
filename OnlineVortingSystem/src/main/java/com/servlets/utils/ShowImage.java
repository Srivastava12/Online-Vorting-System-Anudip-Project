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
			
			// Set the content type of the response to "image/gif" as the output is an image
			response.setContentType("image/gif");

			// Get the current HTTP session or create a new one if it doesn't exist
			HttpSession session = request.getSession(true);

			// Retrieve the candidate's number stored in the session (from previous actions)
			String candidateNumber = (String) session.getAttribute("number");

			byte[] image = null;  // Byte array to hold the image data from the database
			ServletOutputStream sos = null;  // Output stream to write the image back to the client (browser)

			// SQL query to fetch the image associated with the candidate's number from the "candidateimage" table
			String sqlQuery = "select images from candidateimage where candidateNumber=?";

			// Prepare the SQL query
			PreparedStatement ps = conn.prepareStatement(sqlQuery);

			// Set the candidate's number as the query parameter
			ps.setString(1, candidateNumber);

			// Execute the query and get the result set containing the image
			ResultSet candidateImage = ps.executeQuery();

			// Loop through the result set to retrieve the image (if available)
			while (candidateImage.next()) {
   			 // Get the image data as a byte array from the "images" column
    			image = candidateImage.getBytes("images");

   			 // Get the output stream to write the image to the HTTP response
    			sos = response.getOutputStream();

    			// Write the image data to the output stream, sending it to the client
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
