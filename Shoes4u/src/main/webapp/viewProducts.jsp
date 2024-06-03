<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
    HttpSession s= request.getSession(false);
    if (s == null || !"admin".equals(s.getAttribute("username"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="model.ProductDao" %>
<%
    ProductDao productDao = new ProductDao();
    List<Product> productList = productDao.getAllProducts();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Products</title>
<link rel="stylesheet" href="css/adminHeader.css">
<link rel="stylesheet" href="css/viewProducts.css">
<link rel="stylesheet" href="css/footer.css">
</head>
<body>
<%@ include file="adminHeader.jsp" %>
<div class="container">
    <h1>View Products</h1>
   
    <table border="1">
        <tr>
            <th>Image</th> <!-- Nuova colonna per l'immagine -->
            <th>ID</th>
            <th>Product Name</th>
            <th>Brand</th>
            <th>Price</th>
            <th>Availability</th>
            <th>Category</th>
            <th>User Type</th>
            <th>Actions</th>
        </tr>
        <%
            for (Product product : productList) {
        %>
        <tr>
            <td><img src="<%= product.getImage() %>" alt="Product Image" class="product-image"></td> <!-- Mostra l'immagine -->
            <td><%= product.getProductId() %></td>
            <td><%= product.getProductName() %></td>
            <td><%= product.getBrand() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= product.getAvailability() %></td>
            <td><%= product.getCategory() %></td>
            <td><%= product.getUserType() %></td>
            <td>
                <a href="editProductForm.jsp?id=<%= product.getProductId() %>">Edit</a>
                <a href="DeleteProductServlet?id=<%= product.getProductId() %>" onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    
</div>

<%@ include file="footer.jsp" %>
</body>
</html>
