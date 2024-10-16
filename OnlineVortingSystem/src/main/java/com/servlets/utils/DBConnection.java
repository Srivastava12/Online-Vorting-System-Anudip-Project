package com.servlets.utils;

import java.sql.*;
public class DBConnection {
     // Class to manage a database connection for the online voting system
     public static Connection conn = null; // Static Connection object to hold the database connection

     // Static block to initialize the database connection when the class is loaded
     static {
         try {
             // Database URL pointing to the local MySQL server with the "online_voting_system" database
             String url = "jdbc:mysql://localhost:3306/online_vorting_syetem"; // Correct the typo in "vorting"
        
             // Database credentials: Username and password for the MySQL server
             String user = "root"; // Username for the database
             String password = "shubham@123"; // Password for the database
        
             // Establishing the connection to the MySQL database
             conn = DriverManager.getConnection(url, user, password);
        
         } catch (Exception e) {
             // If an error occurs during the connection, it is caught here and printed
             System.out.println(e); // Print the exception message
         }
     }
}

