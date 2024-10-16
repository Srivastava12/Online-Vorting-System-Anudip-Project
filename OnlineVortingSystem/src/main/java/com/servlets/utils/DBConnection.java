package com.servlets.utils;

import java.sql.*;
public class DBConnection {
     public static Connection conn=null;
     static {
    	 try {
    		 String url="jdbc:mysql://localhost:3306/online_vorting_syetem";
    		 String user="root";
    		 String password="shubham@123";
    		 conn=DriverManager.getConnection(url, user, password);
    	 }catch(Exception e) {
    		 System.out.println(e);
    	 }
     }
}

