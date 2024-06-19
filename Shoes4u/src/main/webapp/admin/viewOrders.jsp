<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="model.Order.Order" %>
<%@ page import="model.Order.OrderDao" %>
<%@ page import="java.util.List" %>
<%
    HttpSession ss = request.getSession(false);
    if (ss== null || !"admin".equals(ss.getAttribute("username"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String customer = request.getParameter("customer");
    OrderDao orderDao = new OrderDao();
    List<Order> orders = orderDao.getOrders(startDate, endDate, customer);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Orders</title>
<link rel="stylesheet" href="<%= request.getContextPath()%>/styles/adminHeader.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/styles/viewProducts.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/styles/footer.css">

</head>
<body>
<%@ include file="../fragments/header.jsp" %>

<div class="container">
    <h1>View Orders</h1>
    <form method="get">
        <label for="startDate">Start Date:</label>
        <input type="date" id="startDate" name="startDate"><br>
        
        <label for="endDate">End Date:</label>
        <input type="date" id="endDate" name="endDate"><br>
        
        <label for="customer">Customer:</label>
        <input type="text" id="customer" name="customer"><br>
        
        <button type="submit">Filter Orders</button>
    </form>
    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>Customer</th>
            <th>Order Date</th>
            <th>Total</th>
        </tr>
        <%
            for (Order order : orders) {
        %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= order.getCustomer() %></td>
            <td><%= order.getOrderDate() %></td>
            <td><%= order.getTotal() %></td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<%@ include file="../fragments/footer.jsp" %>
</body>
</html>
