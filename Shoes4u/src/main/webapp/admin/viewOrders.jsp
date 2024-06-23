<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Order.Order" %>
<%@ page import="model.Order.OrderDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Date" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession ss = request.getSession(false);
    if (session == null || !"admin".equals(ss.getAttribute("username"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    String startDateStr = request.getParameter("startDate");
    String endDateStr = request.getParameter("endDate");
    String customer = request.getParameter("customer");

    Date startDate = null;
    Date endDate = null;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    try {
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = new Date(dateFormat.parse(startDateStr).getTime()); // Convert java.util.Date to java.sql.Date
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = new Date(dateFormat.parse(endDateStr).getTime()); // Convert java.util.Date to java.sql.Date
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
        return;
    }

    OrderDao orderDao = new OrderDao();
    List<Order> orders = orderDao.getOrders(startDate, endDate, customer);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Orders</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/styles/header.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/styles/viewOrders.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/styles/footer.css">
</head>
<body>
    <%@ include file="../fragments/header.jsp" %>

    <div class="container">
        <h1>View Orders</h1>
        <form method="get">
            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" name="startDate" value="<%= startDateStr %>"><br>
            
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate" value="<%= endDateStr %>"><br>
            
            <label for="customer">Customer:</label>
            <input type="text" id="customer" name="customer" value="<%= customer %>"><br>
            
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
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (Order order : orders) {
            %>
            <tr>
                <td><%= order.getId() %></td>
                <td><%= order.getCustomer() %></td>
                <td><%= outputDateFormat.format(order.getOrderDate()) %></td>
                <td>€<%= order.getTotalPrice() %></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>

    <%@ include file="../fragments/footer.jsp" %>
</body>
</html>
