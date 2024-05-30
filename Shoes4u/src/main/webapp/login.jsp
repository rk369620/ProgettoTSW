<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/footer.css">

<script src="js/login.js"></script>
</head>
<body>

<%@include file="header.jsp" %>

<div class="container">
    <h1>Login</h1>
    <form action="LoginServlet" method="post" onsubmit="return validateForm()">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" placeholder="Enter username">
        <p id="username-error" style="color: red;"></p>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" placeholder="Enter password">
        <p id="password-error" style="color: red;"></p>
        
        <button type="submit">Login</button>
    </form>
    
    <p><a href="index.jsp">Back to Home</a></p>
    
    <% 
        String error = request.getParameter("error");
        if (error != null && error.equals("1")) {
    %>
    <p style="color:red;">Invalid Username or Password. Please try again.</p>
    <% 
        } 
    %>
    
    <% 
        String success = request.getParameter("registration");
        if (success != null && success.equals("success")) {
    %>
    <p style="color:green;">Your registration is successful. Please login.</p>
    <% 
        } 
    %>
    
  <%@ include file="footer.jsp" %>
  
</div>
</body>
</html>
