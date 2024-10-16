<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %> 
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Candidate Dashboard</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        body {
            background-color: #000000;
        }
        .candidate-card {
            margin-top: 50px;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: #ffffff;
        }
        .candidate-image img {
            width: 150px;
            height: 145px;
            border: 5px solid black;
        }
        .info {
            margin-top: 20px;
        }
        .info h3 {
            font-weight: bold;
        }
        .info p {
            font-size: 16px;
            color: #555;
        }
        .votes-info {
            margin-top: 30px;
        }
        .votes-info .progress {
            height: 25px;
        }
        .votes-info h5 {
            font-weight: bold;
        }
        .footer {
            margin-top: 20px;
            font-size: 14px;
            text-align: center;
            color: #777;
        }
        .vote-button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="candidate-card text-center">
                 <form action="LogOut" method="post">
                    <!-- Candidate Image -->
                    <div class="candidate-image">
                        <img src="./ShowImage" alt="Candidate Image" id="candidateImage">
                    </div>

                    <!-- Candidate Info -->
                    <div class="info">
                        <h3 id="candidateName"><%= session.getAttribute("name") %></h3>
                        <p>Candidate ID: <span id="candidateId"><%= session.getAttribute("id") %></span></p>
                        <p>Email: <span id="candidateEmail"><%= session.getAttribute("email") %></span></p>
                        <p>Phone: <span id="candidatePhone"><%= session.getAttribute("number") %></span></p>
                    </div>

                    <!-- Voting Info -->
                    <div class="votes-info">
                        <h5>Total Votes: <span id="totalVotes"><%=session.getAttribute("totalVort") %></span></h5>
                        <h5>Winning Percentage: <span id="winningPercentage"><%=session.getAttribute("vortingPercentag") %></span></h5>
                    </div>
                    
                    <div class="voter-detail">           
	                     <button class='vote-button btn btn-success'>Logout</button>
	                </div>
	             </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS (optional) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>
