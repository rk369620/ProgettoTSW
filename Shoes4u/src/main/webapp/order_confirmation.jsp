<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Order.OrderDao" %>
<%@ page import="model.Order.Order" %>

<%
    Order order = (Order) session.getAttribute("orderId");
    if (order == null) {
        response.sendRedirect("cart.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
    <!-- Includi i tuoi fogli di stile CSS e script JavaScript -->
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/order_confirmation.css">
    <link rel="stylesheet" href="styles/footer.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="scripts/order_confirmation.js"></script>
</head>
<body>
    <%@ include file="fragments/header.jsp" %>

    <div class="order-confirmation">
        <h1>Order Confirmation</h1>
        <% 
            try {
                // Recupera l'ID dell'ordine dalla sessione
                int orderId = (int) session.getAttribute("orderId");
                OrderDao orderDao = new OrderDao();
                order = orderDao.getOrderById(orderId); // Usa la variabile già dichiarata

                // Verifica se l'ordine è valido
                if (order != null) {
        %>
                    <div>
                        <h2>Order Details</h2>
                        <p>Order ID: <%= order.getId() %></p>
                        <p>Order Date: <%= order.getOrderDate() %></p>
                        <!-- Altri dettagli dell'ordine -->
                    </div>
        <% 
                } else {
                    out.println("<p>Invalid order ID. Please provide a valid order ID.</p>");
                }
            } catch (NumberFormatException e) {
                out.println("<p>Invalid order ID format. Please provide a valid order ID.</p>");
            } catch (Exception e) {
                out.println("<p>An error occurred: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }
        %>
    </div>

    <%@ include file="fragments/footer.jsp" %>

</body>
</html>
