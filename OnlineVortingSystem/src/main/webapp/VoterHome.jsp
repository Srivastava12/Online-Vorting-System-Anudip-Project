<%@page import="org.apache.jasper.tagplugins.jstl.core.Out"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="true" %> 
<%@ page import="com.servlets.utils.DBConnection" %>
<%@ page import="jakarta.servlet.ServletOutputStream" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Voting System</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin-left:35%;
            padding: 20px;
            background: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 15px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        .candidate-list {
            display: flex;
            flex-direction: column;
            background-color: #0379ff;
        }

        .candidate {
            display: flex;
            align-items: center;
            padding: 10px;
            margin-top: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }
     
        .candidate-info {
            flex-grow: 1;
            margin-left: 25px;
            text-align: right;
        }

        h2 {
            margin: 0 0 10px 0;
            color: #333;
        }

        .vote-button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
        }

        .vote-button:hover {
            background-color: #218838;
        }

        .total-votes,
        .voting-percentage {
            color: #555;
            margin: 5px 0;
        }
         body {
            font-family: Arial, sans-serif;
            background-color: black;
            padding: 20px;
        }

        .voter-card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            padding: 20px;
            margin: 20px auto;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        .voter-detail {
            margin-bottom: 15px;
        }

        .voter-detail label {
            font-weight: bold;
            color: #555;
        }

        .voter-detail p {
            margin: 0;
            color: #333;
        }
        
        .voter-card-square {
            position :fixed;
            width: 400px;
            height: 450px;
            padding: 30px;
            background-color: #fff;
            border-radius: 15px;
            display: flex;
            flex-direction: column;
            justify-content:;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>
    <div class="column">
    <div class="voter-card-square">
        <h2>Your Details</h2>
        <form action="LogOut" method="post">
	        <div class="voter-detail">
	            <label for="voterName">Name:</label>
	            <p id="voterName"><%= session.getAttribute("name") %></p>
	        </div>
	        <div class="voter-detail">
	            <label for="voterID">Voter ID:</label>
	            <p id="voterID"><%= session.getAttribute("id") %></p>
	        </div>
	        <div class="voter-detail">
	            <label for="voterMobile">Mobile Number:</label>
	            <p id="voterMobile"><%= session.getAttribute("number") %></p>
	        </div>
	        <div class="voter-detail">
	            <label for="voterEmail">Email ID:</label>
	            <p id="voterEmail"><%= session.getAttribute("email") %></p>
	        </div>
	         <div class="voter-detail">           
	             <button class='vote-button btn btn-success'>Logout</button>
	        </div>
        </form>
    </div>
    </div>
    <div class="column">
    <div class="container">
        <h1>List of Candidates</h1>
        <div class="candidate-list">
            <% 
                try{
                	
                	Connection conn=DBConnection.conn;
                	String sqlQuery = "select name,mobNumber from candidatesdetail";
                	PreparedStatement ps=conn.prepareStatement(sqlQuery);
                	ResultSet candiadteInfo=ps.executeQuery();
                	
                	while(candiadteInfo.next()){
                		
                		String candidateName = candiadteInfo.getString("name");
                		String cnadidateNumber = candiadteInfo.getString("mobNumber");
                		
                	    session.setAttribute("cnadidateNumber", cnadidateNumber);
                	    
                		out.println("\t\t<div class='candidate d-flex flex-column flex-md-row align-items-center mb-4'>\n" +
                                "\t\t\t\t<img src='webapp"+cnadidateNumber+".png"+"' width='150' height='145' style='padding-left : 15px'>\n" +
                                "\t\t\t\t<div class='candidate-info'>\n" +
                                "\t\t\t\t\t<h2 class='text-center text-md-right'>"+candidateName+"</h2>\n" +
                                "\t\t\t\t\t<button class='vote-button btn btn-success' name='cnadidateNumber' value='"+cnadidateNumber+"'>  Vote   </button>\n" +
                                "\t\t\t\t</div>\n" +
                                "\t\t\t</div>");
                		
                	}
                }catch(Exception e){
                	e.getStackTrace();
                }
            %>
        </div>
    </div>
    </div>
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

    