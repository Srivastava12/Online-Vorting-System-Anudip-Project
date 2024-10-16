package com.servlets.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
	public String getDateTime() {
		// Get current date and time
		
		LocalDateTime now = LocalDateTime.now();

		// Format the date and time
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = now.format(formatter);

		return formattedDateTime;
		
	}
}
