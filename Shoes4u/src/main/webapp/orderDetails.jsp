<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Order.Order" %>
<%@ page import="model.Order.OrderDao" %>
<%@ page import="model.Order.OrderItem" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Details</title>
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/viewOrders.css">
    <link rel="stylesheet" href="styles/footer.css">
</head>
<body>
    <%@ include file="fragments/header.jsp" %>

    <div class="order-details-container">
        <h1>Order Details</h1>
        <% 
            try {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                OrderDao orderDao = new OrderDao();
                Order order = orderDao.getOrderById(orderId);
                
                if (order != null) {
                    List<OrderItem> orderItems = orderDao.getOrderItemsByOrderId(orderId);
        %>
                    <div class="order-info">
                        <p><strong>Order ID:</strong> <%= order.getId() %></p>
                        <p><strong>Order Date:</strong> <%= order.getOrderDate() %></p>
                        <p><strong>Total Price:</strong> € <%= order.getTotalPrice() %></p>
                    </div>
                    
                    <div class="order-items">
                        <h2>Order Items</h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                   
                                </tr>
                            </thead>
                            <tbody>
                                <% for (OrderItem item : orderItems) { %>
                                    <tr>
                                        <td><%= item.getProductName() %></td>
                                        <td><%= item.getQuantity() %></td>
                                        
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
        <% 
                } else {
                    out.println("<p>Order not found or invalid order ID.</p>");
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
