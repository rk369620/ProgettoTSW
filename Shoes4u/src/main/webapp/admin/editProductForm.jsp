<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Product.ProductDao" %>
<%@ page import="model.Product.Product" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Product</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/styles/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/styles/addProduct.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/styles/footer.css">
    <script src="js/login.js"></script>
</head>
<body>
   <%@ include file="../fragments/header.jsp" %>

    <center><h1>Edit Product</h1></center>

    <%
        int id = Integer.parseInt(request.getParameter("id"));
        ProductDao productDAO = new ProductDao();
        Product product = productDAO.getProductById(id);
    %>

    <form action="<%= request.getContextPath()%>/EditProductServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<%= product.getProductId() %>">
        
        <label for="name">Product Name:</label>
        <input type="text" id="name" name="name" value="<%= product.getProductName() %>"><br>
        
        <label for="brand">Brand:</label>
        <input type="text" id="brand" name="brand" value="<%= product.getBrand() %>"><br>
        
        <label for="price">Price:</label>
        <input type="text" id="price" name="price" value="<%= product.getPrice() %>"><br>
        
        <label for="availability">Availability:</label>
        <input type="text" id="availability" name="availability" value="<%= product.getAvailability() %>"><br>
        
        <label for="category">Category:</label>
        <input type="text" id="category" name="category" value="<%= product.getCategory() %>"><br>
      
        <!-- Display the current image -->
        <label for="currentImage">Current Image:</label>
        <img src="uploads/<%= product.getImage() %>" alt="Product Image" style="max-width: 200px;"><br>

        <!-- Image Upload Field -->
        <label for="image">Upload New Image (if you want to change it):</label>
        <input type="file" id="image" name="image"><br>

        <button type="submit">Update Product</button>
    </form>
   
<%@ include file="../fragments/footer.jsp" %>
</body>
</html>
