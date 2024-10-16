package com.servlets.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;

public class ImageLoder {
     private FileOutputStream fos;

	public void getImage() {
    	 
    	 // SQL query to retrieve mobile numbers of candidates from the "candidatesdetail" table
	String sqlQuery = "select mobNumber from candidatesdetail";

	// SQL query to retrieve candidate images from the "candidateimage" table using the candidate's number as a parameter
	String sqlQuery1 = "select images from candidateimage where candidateNumber=?";

	// Establish a connection to the database through the DBConnection class
	Connection conn = DBConnection.conn;

	try {
    		// Prepare the SQL statement for the first query to get all candidate mobile numbers
    		PreparedStatement ps = conn.prepareStatement(sqlQuery);
    
    		// Execute the query to get the result set of mobile numbers
    		ResultSet rs = ps.executeQuery();

    		// Loop through the result set to process each candidate's mobile number
    		while(rs.next()) {
        
        		// Get the candidate's mobile number from the result set
        		String candidateNumber = rs.getString("mobNumber");
        
       			 // Create a new file to store the image using the candidate's mobile number as the filename
        		File file = new File("C:/Users/Shubham Kumar/Desktop/Java WebProjects/OnlineVortingSystem/src/main/webapp" + candidateNumber + ".png");
        
       			 // Create a FileOutputStream to write the image bytes into the file
        		fos = new FileOutputStream(file);
        
        		byte b[];  // Array to store image bytes
        		Blob blob; // Blob object to hold the image from the database

        		// Prepare the second SQL statement to get the image for the candidate using their mobile number
        		ps = conn.prepareStatement(sqlQuery1);
        		ps.setString(1, candidateNumber); // Set the candidate's number as the parameter for the query
        
        		// Execute the query to get the result set containing the image
        		ResultSet img = ps.executeQuery();

      			  // If an image is found for the candidate
      			  if (img.next()) {
            
         		   // Get the Blob (binary data) containing the image from the result set
         		   blob = img.getBlob("images"); // Fetch the image Blob from the "images" column
            
          		  // Convert the Blob into a byte array
           		 b = blob.getBytes(1, (int) blob.length());

          		  // Write the byte array into the file
          		  fos.write(b);
       		 	}
   		 }
	} catch (Exception e) {
  	  // Print the stack trace if an exception occurs
  	  e.printStackTrace();
	}
     }
}
