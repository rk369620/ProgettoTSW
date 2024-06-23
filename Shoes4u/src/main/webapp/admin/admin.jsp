<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
    HttpSession ss= request.getSession(false);
    if (ss == null || !"admin".equals(ss.getAttribute("username"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/styles/header.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/styles/adminn.css">

<link rel="stylesheet" href="<%= request.getContextPath()%>/styles/footer.css">
</head>
<body>

<%@ include file="../fragments/header.jsp" %>

<div class="container">
    <h1>Welcome Admin</h1>
    <ul>
        <li><a href="addProduct.jsp">Add Product</a></li>
        <li><a href="viewProducts.jsp">View Products</a></li>
        <li><a href="viewOrders.jsp">View Orders</a></li>
    </ul>
</div>
<%@ include file="../fragments/footer.jsp" %>
</body>
</html>
