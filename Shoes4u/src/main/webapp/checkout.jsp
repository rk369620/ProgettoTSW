<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Cart.CartItem" %>
<%@ page import="model.Product.Product" %>
<%@ page import="model.User.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/checkout.css">
    <link rel="stylesheet" href="styles/footer.css">
</head>
<body>
    <%@ include file="fragments/header.jsp" %>

    <h1>Checkout</h1>
    
    <div class="checkout-container">
        <div class="cart-items">
            <h2>Cart Items</h2>
            <table>
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Brand</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
                        double totalCartPrice = 0.0;
                        if (cartItems != null && !cartItems.isEmpty()) {
                            for (CartItem cartItem : cartItems) {
                                Product product = cartItem.getProduct();
                    %>
                                <tr>
                                    <td><%= product.getProductName() %></td>
                                    <td><%= product.getBrand() %></td>
                                    <td>$<%= product.getPrice() %></td>
                                    <td><%= cartItem.getQuantity() %></td>
                                    <% double itemTotalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity(); %>
                                    <td>$<%= itemTotalPrice %></td>
                                </tr>
                                <% totalCartPrice += itemTotalPrice; %>
                    <% 
                            }
                        }
                    %>
                </tbody>
            </table>
            <div class="total-cart-price">
                <h3>Total Cart Price: $<%= totalCartPrice %></h3>
            </div>
        </div>

        <div class="checkout-form">
            <h2>Shipping Information</h2>
            <!-- Form for shipping information -->
            <form action="PaymentServlet" method="post">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" required><br><br>

                <label for="address">Address</label>
                <textarea id="address" name="address" rows="4" required></textarea><br><br>

                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="phone" required><br><br>

                <button type="submit" class="btn-proceed-to-payment">Proceed to Payment</button>
            </form>
        </div>
    </div>

    <%@ include file="fragments/footer.jsp" %>
</body>
</html>
