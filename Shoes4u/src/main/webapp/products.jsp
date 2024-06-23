<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product.ProductDao" %>
<%@ page import="model.Product.Product" %>
<%@ page import="model.User.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Page</title>
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/pro.css">
    
    <link rel="stylesheet" href="styles/footer.css">
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="scripts/script.js"></script>
</head>
<body>
    <%@ include file="fragments/header.jsp" %>

    <h1>Product Page</h1>

   
    <div class="category-dropdown-container">
        <select id="categoryDropdown" class="category-dropdown">
            <option value="all">All Products</option>
            <option value="uomo">Uomo</option>
            <option value="donna">Donna</option>
            <option value="bambino">Bambino</option>
        </select>
    </div>

    
    <div id="productsContainer" class="products-container">
        <%
            ProductDao productDao = new ProductDao();
            List<Product> productList = productDao.getAllProducts(); 
            for (Product product : productList) {
        %>
            <div class="product-card" data-category="<%= product.getCategory() %>">
                <div class="product-image-container">
                    <img src="<%= product.getImage() %>" alt="<%= product.getProductName() %>" class="product-image">
                </div>
                <h2><%= product.getProductName() %></h2>
                <p><%= product.getBrand() %></p>
                <p class="price">€
                <%= product.getPrice() %></p>
                <div class="product-actions">
                   
                    <form action="CartServlet" method="post">
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                        <button type="submit" class="btn-add-to-cart">Add to Cart</button>
                    </form>

                    
                </div>
            </div>
        <% 
            } 
        %>
    </div>

    <%@ include file="fragments/footer.jsp" %>

    

</body>
</html>
