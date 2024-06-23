<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Order.Order" %>
<%@ page import="model.Order.OrderItem" %>

<%
    Order order = (Order) session.getAttribute("order");
    if (order == null) {
        response.sendRedirect("cart.jsp"); 
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Page</title>
   
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/viewProducts.css">
        <link rel="stylesheet" href="styles/addProduct.css">
    
        <link rel="stylesheet" href="styles/footer.css">
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
</head>
<body>
    <%@ include file="fragments/header.jsp" %>

    <h1>Payment Page</h1>

    <div class="order-summary">
        <h2>Order Summary</h2>
        <table>
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <% for (OrderItem orderItem : order.getOrderItems()) { %>
                    <tr>
                        <td><%= orderItem.getProductName() %></td>
                        <td><%= orderItem.getQuantity() %></td>
                        <td>€<%= orderItem.getProductPrice() %></td>
                        <td>€<%= orderItem.getProductPrice() * orderItem.getQuantity() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <h3>Total Price: €<%= order.getTotalPrice() %></h3>
    </div>

    <div class="payment-form">
        <center><h2>Payment Details</h2></center>
        <form action="ProcessPaymentServlet" method="post">
            <input type="hidden" name="amount" value="<%= order.getTotalPrice() %>">
            
            <label for="cardNumber">Card Number:</label>
            <input type="text" id="cardNumber" name="cardNumber" required>

            <label for="expiryDate">Expiry Date:</label>
            <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YYYY" required>

            <label for="cvv">CVV:</label>
            <input type="text" id="cvv" name="cvv" required>

            <label for="billingAddress">Billing Address:</label>
            <input type="text" id="billingAddress" name="billingAddress">

            <button type="submit">Pay Now</button>
        </form>
    </div>

    <%@ include file="fragments/footer.jsp" %>
</body>
</html>
