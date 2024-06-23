<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Order.Order" %>
<%@ page import="java.util.List" %>
<% 
HttpSession ss= request.getSession(false); 
if (ss== null || ss.getAttribute("user") == null) {
    
    response.sendRedirect("login.jsp");
    return;
}%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Orders</title>
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/viewOrders.css">
        
    
        <link rel="stylesheet" href="styles/footer.css">
    
</head>
<body>
    <%@ include file="fragments/header.jsp" %>

    <div class="orders-container">
        <h1>My Orders</h1>
        <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders == null || orders.isEmpty()) {
        %>
            <p>You have no orders.</p>
        <% } else { %>
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Date</th>
                        <th>Total</th>
                       
                    </tr>
                </thead>
                <tbody>
                    <% for (Order order : orders) { %>
                        <tr>
                            <td><a href="orderDetails.jsp?orderId=<%= order.getId() %>"><%= order.getId() %></a></td>
                            <td><%= order.getOrderDate() %></td>
                            
                            <td>€
                            <%= order.getTotalPrice() %></td>
                            
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
    </div>

    <%@ include file="fragments/footer.jsp" %>
</body>
</html>
