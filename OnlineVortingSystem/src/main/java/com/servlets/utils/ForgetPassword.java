package com.servlets.utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/ForgetPassword")
public class ForgetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ForgetPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Retrieve the user's phone number from the HTTP request
		String number = request.getParameter("number");

		// Send an OTP (One-Time Password) to the provided phone number using the SendOTP class
		String otp = new SendOTP().sendOTP(number);

		// Get the current HTTP session or create one if it doesn't exist
		HttpSession session = request.getSession();

		// Store the phone number and generated OTP in the session attributes for future use
		session.setAttribute("number", number);  // Store the phone number
		session.setAttribute("otp", otp);        // Store the generated OTP

		// Forward the request and response to the ForgetOTPVerification.jsp page for further processing
		RequestDispatcher rd = request.getRequestDispatcher("ForgetOTPVerification.jsp");
		rd.forward(request, response);  // Forward the request and response objects to the JSP
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
