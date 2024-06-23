

<%
    HttpSession s= request.getSession(false);
    String role = null;
    String username = null;
    if (session != null) {
        role = (String) s.getAttribute("role");
        username = (String) s.getAttribute("username");
    }
%>

<header>
    <div class="logo">
        <h1><Marquee>Shoes4u</Marquee></h1>
    </div>
    <nav>
        <ul>
            <% if (role != null && role.equals("admin")) { %>
             <li><a href="admin.jsp">Admin Page</a></li>
            <% } %>
            <% if (role != null && role.equals("admin")) { %>
                <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
               
            <% } else if (role != null && role.equals("user")) { %>
                        <li><a href="index.jsp">Home</a></li>
                                          <li><a href="<%= request.getContextPath() %>/UsersOrdersServlet">Orders</a></li>
                        
             <li><a href="cart.jsp">Cart</a></li>
            <li><a href="products.jsp">Products</a></li>
                <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
            <% } else { %>
                        <li><a href="index.jsp">Home</a></li>
            
             
            <li><a href="products.jsp">Products</a></li>
                <li><a href="login.jsp">Login</a></li>
                <li><a href="register.jsp">Register</a></li>
            <% } %>
        </ul>
    </nav>
</header>
