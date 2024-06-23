<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>Order Confirmation</title>
    <!-- Includi i tuoi fogli di stile CSS e script JavaScript -->
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/order_confirmation.css">
    <link rel="stylesheet" href="styles/footer.css">
</head>
<body>
    <%@ include file="fragments/header.jsp" %>

    <div class="order-confirmation">
        <h1>Order Confirmation</h1>
        <p>Payment successful!</p>
        <!-- Puoi aggiungere ulteriori dettagli dell'ordine o altre informazioni qui -->
    </div>

    <%@ include file="fragments/footer.jsp" %>

</body>
</html>
