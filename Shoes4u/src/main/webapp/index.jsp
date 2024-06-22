<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Shoes4U</title>
<link rel="stylesheet" href="styles/styles.css">
<link rel="stylesheet" href="styles/header.css">
<link rel="stylesheet" href="styles/footer.css">


</head>
<body>
<%@ include file="fragments/header.jsp" %>

<main>
    <div class="main-image">
        <div class="h-text">
            <h1>Welcome to Shoes4U</h1>
            <p>Your one-stop shop for the latest and greatest in footwear.</p>
            <a href="products.jsp" class="shop-button">Shop Now</a>
        </div>
    </div>
</main>

<%@ include file="fragments/footer.jsp" %>
</body>
</html>
