<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %> 
<%@ page import="java.sql.*" %>
<%@ page import="com.servlets.utils.DBConnection" %>
<%@ page import="com.servlets.utils.PasswardManager" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>OTP Verification</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100vh;
			margin: 0;
			background-color: none;
			color: #e0e0e0;
		}

		.container {
			background-color: #818181;
			opacity: 10;
			padding: 2rem;
			border-radius: 8px;
			box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
			text-align: center;
		}

		h1 {
			margin-bottom: 1rem;
			color: #ffffff;
		}

		.otp-input {
			display: flex;
			justify-content: center;
			margin-bottom: 1rem;
		}

		.otp-input input {
			width: 40px;
			height: 40px;
			margin: 0 5px;
			text-align: center;
			font-size: 1.2rem;
			border: 1px solid #444;
			border-radius: 4px;
			background-color: #b6b0b0;
			color: #ffffff;
		}

		.otp-input input::-webkit-outer-spin-button,
		.otp-input input::-webkit-inner-spin-button {
			-webkit-appearance: none;
			margin: 0;
		}

		.otp-input input[type=number] {
			-moz-appearance: textfield;
		}

		button {
			background-color: #4CAF50;
			color: white;
			border: none;
			padding: 10px 20px;
			font-size: 1rem;
			border-radius: 4px;
			cursor: pointer;
			margin: 5px;
		}

		button:hover {
			background-color: #45a049;
		}

		button:disabled {
			background-color: #cccccc;
			color: #666666;
			cursor: not-allowed;
		}

		#timer {
			font-size: 1.2rem;
			margin-bottom: 1rem;
			color: #ff9800;
		}
	</style>
</head>
<body>
	<div class="container">
		<h1>OTP Verification</h1>
		<p>Enter the 5-digit code sent to your device</p>
		<div id="timer">Time remaining: 2:00</div>
		<form action="ForgetOTPVerification.jsp" method="post">
			<div class="otp-input">
				<input type="number" min="0" max="9" required name='1'>
				<input type="number" min="0" max="9" required name='2'>
				<input type="number" min="0" max="9" required name='3'>
				<input type="number" min="0" max="9" required name='4'>
				<input type="number" min="0" max="9" required name='5'>
			</div>
			<button type="submit" name='btn' value='verify'>Verify</button>
			<button id="resendButton" type="submit" name='btn' value='resend'>ReEnter Code</button>
		</form>
	</div>
</body>
<script>
	//Select all the input fields inside the element with the class 'otp-input'
	const inputs = document.querySelectorAll('.otp-input input');

	// Select the timer display element and resend button by their IDs
	const timerDisplay = document.getElementById('timer');
	const resendButton = document.getElementById('resendButton');

	// Set the initial countdown time in seconds (120 seconds = 2 minutes)
	let timeLeft = 120;

	// Variable to store the interval ID for the timer, which will be used to clear the timer when necessary
	let timerId;

	/**
	 * Starts the countdown timer that updates every second
	 */
	function startTimer() {
		// Set the timer interval to execute every second (1000 ms)
		timerId = setInterval(() => {
			// If time runs out (timeLeft reaches 0), stop the timer
			if (timeLeft <= 0) {
				clearInterval(timerId); // Stop the timer
				timerDisplay.textContent = "Code expired"; // Update the timer display to indicate expiration
				resendButton.disabled = false; // Enable the 'Resend OTP' button
				inputs.forEach(input => input.disabled = true); // Disable all OTP input fields since the code is expired
			} else {
				// Calculate minutes and seconds left from the total time remaining
				const minutes = Math.floor(timeLeft / 60);
				const seconds = timeLeft % 60;

				// Update the timer display with formatted time in 'MM:SS' format
				timerDisplay.textContent = `Time remaining: ${minutes}:${seconds.toString().padStart(2, '0')}`;

				// Decrement the time left by 1 second
				timeLeft--;
			}
		}, 1000); // Run the function every 1000 milliseconds (1 second)
	}

	/**
	 * Function to handle OTP resend action
	 * Resets the inputs and restarts the countdown timer
	 */
	function resendOTP() {
		// In a real-world application, you would typically send a request to your backend to resend the OTP
		alert("New OTP sent!"); // Alert for demonstration purposes

		// Reset the countdown timer to 120 seconds (2 minutes)
		timeLeft = 120;

		// Clear and re-enable all OTP input fields
		inputs.forEach(input => {
			input.value = ''; // Clear the input field
			input.disabled = false; // Enable the input field
		});

		// Disable the resend button until the current OTP expires
		resendButton.disabled = true;

		// Focus on the first input field
		inputs[0].focus();

		// Clear any previous timer and start a new one
		clearInterval(timerId);
		startTimer();
	}

	// Loop through each OTP input field to handle input and keydown events
	inputs.forEach((input, index) => {

		// Event listener for 'input' event to handle character input
		input.addEventListener('input', (e) => {
			// Ensure that only a single character can be entered in the field
			if (e.target.value.length > 1) {
				e.target.value = e.target.value.slice(0, 1); // If more than one character is entered, keep only the first
			}

			// If a character is entered and it's not the last input field, move to the next input field
			if (e.target.value.length === 1) {
				if (index < inputs.length - 1) {
					inputs[index + 1].focus(); // Focus on the next input field
				}
			}
		});

		// Event listener for 'keydown' event to handle backspace and input restrictions
		input.addEventListener('keydown', (e) => {
			// If the 'Backspace' key is pressed and the current input field is empty
			if (e.key === 'Backspace' && !e.target.value) {
				// Move the focus to the previous input field (if it exists)
				if (index > 0) {
					inputs[index - 1].focus();
				}
			}

			// Prevent the user from entering the letter 'e' (as it's not a valid number input)
			if (e.key === 'e') {
				e.preventDefault(); // Stop the 'e' key from being entered in the input
			}
		});
	});

	// Start the timer when the page loads
	startTimer();
</script>

</html>
<%
     //Variable to store the OTP entered by the user
	 String userOtp="";
	 // Loop through 5 times to retrieve the OTP input by the user from the form fields
	 for(int i=1;i<=5;i++) 
	     // Concatenating the user's input for each digit of the OTP to the 'userOtp' string
	     userOtp=userOtp+request.getParameter(i+"");
	    		 
	 String OTP = (String) session.getAttribute("otp");
	 String number = (String) session.getAttribute("number");   		 
	    		 
	 if(userOtp.equals(OTP)){
		 
		 try{
			 Connection conn=DBConnection.conn;
		 	 PasswardManager pd = new PasswardManager();
			 String sqlQuery1 = "select password from votersdetail where voterMob=?";
			 String sqlQuery2 = "select password from candidatesdetail where voterMob=?";
		 
			 PreparedStatement ps=conn.prepareStatement(sqlQuery1);
			 ps.setString(1, number);
     		 ResultSet voterPassword = ps.executeQuery();
     	 
     		 if(voterPassword.next())
     			 response.sendRedirect("Your Password is :- "+pd.decryptPassword(voterPassword.getString("password"), 2));
     		 
     		 PreparedStatement ps1=conn.prepareStatement(sqlQuery2);
			 ps1.setString(1, number);
			 ResultSet candidatePassword = ps.executeQuery();
		 
			 if(candidatePassword.next())
				 response.sendRedirect("Your Password is :- "+pd.decryptPassword(candidatePassword.getString("password"), 2));
     			 
		 }catch(Exception e){
			 e.getStackTrace();
		 }
		 
	 }else{
		 out.println("Wrong OTP Go back and enter again OTP............!");
	 }
%>