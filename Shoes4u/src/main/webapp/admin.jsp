<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
    HttpSession s= request.getSession(false);
    if (s == null || !"admin".equals(s.getAttribute("username"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="css/adminHeader.css">

<link rel="stylesheet" href="css/admin.css">
<link rel="stylesheet" href="css/footer.css">
</head>
<body>
<%@ include file="adminHeader.jsp" %>



<div class="container">
    <h1>Welcome Admin</h1>
    <ul>
        <li><a href="addProduct.jsp">Add Product</a></li>
        <li><a href="viewProducts.jsp">View Products</a></li>
        <li><a href="viewOrders.jsp">View Orders</a></li>
    </ul>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
