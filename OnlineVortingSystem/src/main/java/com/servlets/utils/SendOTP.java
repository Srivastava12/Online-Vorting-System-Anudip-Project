package com.servlets.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SendOTP {
	public String sendOTP(String number)
	{
		// Generating a new OTP using the OTP class's getOTP() method
		String OTP = new OTP().getOTP();

		try {
			
		    // Constructing the URL string for sending the OTP via the Fast2SMS API
		    String URL = "https://www.fast2sms.com/dev/bulkV2?authorization="
		            + "Add your api key"
		            + "&route=otp&variables_values=" + OTP       // Adding the OTP to the API request
		            + "&flash=0&numbers=" + number;             // Adding the recipient's mobile number to the request
		    
		    // Creating a new URL object using the constructed URL string
		    URL url = new URL(URL);
		    
		    // Opening a connection to the Fast2SMS server using HTTPS
		    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		    
		    // Setting the request method to "GET" for the API call
		    con.setRequestMethod("GET");
		    
		    // Setting request properties (headers), including the User-Agent and cache control
		    con.setRequestProperty("User-Agent", "Mozilla/5.0"); // Emulates a browser-like request
		    con.setRequestProperty("cache-control", "no-cache"); // Disables caching for the API request
		    
		    // Creating a StringBuffer to store the response from the server
		    StringBuffer response = new StringBuffer();
		    
		    // Reading the input stream from the connection (response from Fast2SMS API)
		    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    
		    // Reading the response line by line and appending it to the StringBuffer
		    while (true) {
		        String line = br.readLine();
		        
		        // If no more lines are available, exit the loop
		        if (line == null) {
		            break;
		        }
		        
		        // Append the current line to the response buffer
		        response.append(line);
		    }
		    
		    // Printing the response from the API to the console for debugging or logging purposes
		    System.out.println(response);
		    
		} catch (Exception e) {
		    // Catching and printing any exceptions that occur during the API call
		    System.out.println(e);
		}

		// Returning the generated OTP to be used elsewhere in the program
		return OTP;

	}
}
