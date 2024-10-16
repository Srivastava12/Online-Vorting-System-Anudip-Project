package com.servlets.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
	// Method to get the current date and time in a formatted string
	public String getDateTime() {
    
    	// Get the current date and time from the system's clock
    	LocalDateTime now = LocalDateTime.now();

    	// Create a formatter to specify the pattern for date and time formatting
    	// The pattern used is "yyyy-MM-dd HH:mm:ss", which will format the output like "2024-10-16 12:45:30"
   	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	// Apply the formatter to the current date and time to get the formatted string
    	String formattedDateTime = now.format(formatter);
	
    	// Return the formatted date and time as a string
    	return formattedDateTime;
	}
}
