<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
<link rel="stylesheet" href="css/adminHeader.css">
<link rel="stylesheet" href="css/addProduct.css">

<link rel="stylesheet" href="css/footer.css">

<script src="js/login.js"></script>
</head>
<body>
    <%@ include file="adminHeader.jsp" %>
    <center><h1>Add Product</h1></center>
    <form action="AddProductServlet" method="post" enctype="multipart/form-data">
        <label for="productName">Product Name:</label>
        <input type="text" id="productName" name="productName"><br>
        
        <label for="brand">Brand:</label>
        <input type="text" id="brand" name="brand"><br>
        
        <label for="price">Price:</label>
        <input type="text" id="price" name="price"><br>
        
        <label for="availability">Availability:</label>
        <input type="text" id="availability" name="availability"><br>
        
        <label for="category">Category:</label>
        <input type="text" id="category" name="category"><br>
        
        <label for="userType">User Type:</label>
        <input type="text" id="userType" name="userType"><br>
        
        <!-- Image Upload Field -->
        <label for="image">Product Image:</label>
        <input type="file" id="image" name="image"><br>
        
        <button type="submit">Add Product</button>
    </form>
   
    <%@ include file="footer.jsp" %>
</body>
</html>
