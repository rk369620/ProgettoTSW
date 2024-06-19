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
    <link rel="stylesheet" href="styles/products.css">
    <link rel="stylesheet" href="styles/footer.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="scripts/script.js"></script>
</head>
<body>
    <%@ include file="fragments/header.jsp" %>

    <h1>Product Page</h1>

    <!-- Category Selector -->
    <select id="categorySelector">
        <option value="">Tutte le categorie</option>
        <option value="Uomo">Uomo</option>
        <option value="Donna">Donna</option>
        <option value="Bambini">Bambini</option>
    </select>

    <div id="productsContainer" class="products-container">
        <% 
            String selectedCategory = request.getParameter("category");
            ProductDao productDao = new ProductDao();
            List<Product> productList;

            if (selectedCategory == null || selectedCategory.isEmpty()) {
                productList = productDao.getAllProducts(); // Ottieni tutti i prodotti se la categoria non è selezionata
            } else {
                productList = productDao.getProductsByCategory(selectedCategory);
            }

            for (Product product : productList) {
        %>
            <div class="product-card" data-category="<%= product.getCategory() %>" data-product-id="<%= product.getProductId() %>">
                <div class="product-image-container">
                    <img src="<%= product.getImage() %>" alt="<%= product.getProductName() %>" class="product-image">
                </div>
                <h2><%= product.getProductName() %></h2>
                <p><%= product.getBrand() %></p>
                <p class="price">$<%= product.getPrice() %></p>
                <p><%= product.getAvailability() %></p>
                <p><%= product.getCategory() %></p>
                <div class="product-actions">
                    <!-- Form per l'aggiunta al carrello -->
                    <form action="CartServlet" method="post" class="add-to-cart-form">
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                        <label for="quantity_<%= product.getProductId() %>">Quantità:</label>
                        <input type="number" id="quantity_<%= product.getProductId() %>" name="quantity" value="1" min="1" required>
                        <button type="submit" class="btn-add-to-cart">Aggiungi al carrello</button>
                    </form>
                    <!-- Form per il checkout -->
                    <form action="CheckoutServlet" method="post" class="proceed-to-checkout-form">
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                        <button type="submit" class="btn-proceed-to-checkout">Procedi al pagamento</button>
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
