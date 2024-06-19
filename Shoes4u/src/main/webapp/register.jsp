<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
<link rel="stylesheet" href="styles/login.css">
<link rel="stylesheet" href="styles/header.css">
<link rel="stylesheet" href="styles/footer.css">

<script src="scripts/registration.js"></script>
</head>
<body>

<%@include file="fragments/header.jsp" %>

<div class="container">
    <h1>Register</h1>
    <form action="RegisterServlet" method="post" onsubmit="return validateForm()">
    
    	<label for="first-name">First Name:</label>
		<input type="text" id="first-name" name="first-name" placeholder="Enter first name">
		<p id="first-name-error" style="color: red;"></p>
		
		<label for="last-name">Last Name:</label>
		<input type="text" id="last-name" name="last-name" placeholder="Enter last name">
		<p id="last-name-error" style="color: red;"></p>
        
		
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" placeholder="Enter username">
        <p id="username-error" style="color: red;"></p>
        
        
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" placeholder="Enter email">
        <p id="email-error" style="color: red;"></p>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" placeholder="Enter password">
        <p id="password-error" style="color: red;"></p>
        
        <label for="confirm-password">Confirm Password:</label>
		<input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm password">
		<p id="confirm-password-error" style="color: red;"></p>


        
        <button type="submit">Register</button>
    </form>
    
    <p><a href="index.jsp">Back to Home</a></p>
    
    <%@ include file="fragments/footer.jsp" %>
    
</div>
</body>
</html>
              