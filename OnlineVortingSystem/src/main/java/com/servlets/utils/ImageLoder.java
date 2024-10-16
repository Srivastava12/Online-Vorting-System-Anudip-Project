package com.servlets.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;

public class ImageLoder {
     private FileOutputStream fos;

	public void getImage() {
    	 
    	 String sqlQuery = "select mobNumber from candidatesdetail";
    	 String sqlQuery1 = "select images from candidateimage where candidateNumber=?";
    	 Connection conn = DBConnection.conn;
    	 try {
    		 
			PreparedStatement ps = conn.prepareStatement(sqlQuery);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				
				String candidateNumber = rs.getString("mobNumber");
				File file = new File("C:/Users/Shubham Kumar/Desktop/Java WebProjects/OnlineVortingSystem/src/main/webapp"+candidateNumber+".png");
				fos = new FileOutputStream(file);
				byte b[];
				Blob blob;
				
				ps = conn.prepareStatement(sqlQuery1);
				ps.setString(1, candidateNumber);
				ResultSet img = ps.executeQuery();
				
				if(img.next()) {
					blob = img.getBlob(candidateNumber);
					b = blob.getBytes(1,(int)blob.length());
					fos.write(b);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 
     }
}
