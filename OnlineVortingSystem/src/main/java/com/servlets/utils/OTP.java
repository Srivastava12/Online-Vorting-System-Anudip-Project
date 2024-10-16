package com.servlets.utils;

import java.util.Random;

public class OTP {
    public String getOTP(){
    	// Creating a new Random object to generate random numbers
    	Random random = new Random();

    	// Using StringBuilder to construct the OTP by appending random digits
    	StringBuilder sb = new StringBuilder();

    	// Loop to generate a 5-digit OTP
    	for (int i = 0; i < 5; i++) {
    	    // Generating a random integer between 0 and 9 (inclusive) and appending it to the StringBuilder
    	    sb.append(random.nextInt(10));
    	}

    	// Converting the StringBuilder object to a String to get the final OTP
    	String otp = sb.toString();

    	// Returning the generated OTP
    	return otp;

    }
}

