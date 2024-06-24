<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Cart.CartItem" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession ss = request.getSession(false);
    List<CartItem> cartItems = (List<CartItem>) ss.getAttribute("cartItems");
    double totalCartPrice = 0.0;

    if (cartItems == null || cartItems.isEmpty()) { 
        cartItems = new ArrayList<>();
    } else { 
        for (CartItem cartItem : cartItems) { 
            if (cartItem != null && cartItem.getProduct() != null) { 
                totalCartPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity(); 
            } 
        } 
    } 
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart Page</title>
   
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/cart1.css">
    
    <link rel="stylesheet" href="styles/footer.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script >
    $(document).ready(function() {
        $('.btn-increment').click(function() {
            var input = $(this).siblings('.quantity-input');
            var newValue = parseInt(input.val()) + 1;
            input.val(newValue);
            updateItemTotalPrice($(this));
            updateCartItem(input.data('product-id'), newValue);
        });

        $('.btn-decrement').click(function() {
            var input = $(this).siblings('.quantity-input');
            var newValue = parseInt(input.val()) - 1;
            if (newValue >= 1) {
                input.val(newValue);
                updateItemTotalPrice($(this));
                updateCartItem(input.data('product-id'), newValue);
            }
        });

        $('.quantity-input').change(function() {
            var newValue = $(this).val();
            if (newValue < 1) {
                $(this).val(1);
                newValue = 1;
            }
            updateItemTotalPrice($(this));
            updateCartItem($(this).data('product-id'), newValue);
        });

        function updateItemTotalPrice(element) {
            var quantity = element.siblings('.quantity-input').val();
            var price = parseFloat(element.closest('.cart-item').find('.price').text().replace('€', ''));
            var total = quantity * price;
            element.closest('.cart-item').find('.item-total-price').text('Total: €' + total.toFixed(2));
            updateTotalCartPrice();
        }

        function updateTotalCartPrice() {
            var totalCartPrice = 0.0;
            $('.cart-item').each(function() {
                var totalPrice = parseFloat($(this).find('.item-total-price').text().replace('Total: €', ''));
                totalCartPrice += totalPrice;
            });
            $('.total-cart-price h3').text('Total Cart Price: €' + totalCartPrice.toFixed(2));
        }

        function updateCartItem(productId, quantity) {
            $.ajax({
                url: 'UpdateCartItemServlet',
                method: 'POST',
                data: { productId: productId, quantity: quantity },
                success: function(response) {
                    console.log('Cart updated successfully');
                },
                error: function(xhr, status, error) {
                    console.error('Error updating cart:', error);
                }
            });
        }
    });
    </script>
</head>
<body>
    <%@ include file="fragments/header.jsp" %>
   <center> <h1>Your Cart</h1></center>
    <div class="cart-container">
        <% if (cartItems.isEmpty()) { %>
            <p>Your cart is empty.</p>
        <% } else { %>
            <% for (CartItem cartItem : cartItems) { %>
                <div class="cart-item">
                    <img src="<%= cartItem.getProduct().getImage() %>" alt="<%= cartItem.getProduct().getProductName() %>">
                    <h2><%= cartItem.getProduct().getProductName() %></h2>
                    <p><%= cartItem.getProduct().getBrand() %></p>
                    <p class="price">€<%= cartItem.getProduct().getPrice() %></p>
                    <div class="quantity-control">
                        <button class="btn-decrement">-</button>
                        <input type="number" class="quantity-input" value="<%= cartItem.getQuantity() %>" min="1" data-product-id="<%= cartItem.getProduct().getProductId() %>">
                        <button class="btn-increment">+</button>
                    </div>
                    <% double itemTotalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity(); %>
                    <p class="item-total-price">Total: €<%= itemTotalPrice %></p>
                    <form action="RemoveFromCartServlet" method="post">
                        <input type="hidden" name="cartItemId" value="<%= cartItem.getProduct().getProductId() %>">
                        <button type="submit" class="btn-remove">Remove</button>
                    </form>
                </div>
            <% } %>
            <div class="total-cart-price">
                <h3>Total Cart Price:€ <%= totalCartPrice %></h3>
            </div>
           <div class="cart-actions">
    <div class="continue-shopping">
        <a href="products.jsp">Continue Shopping</a>
    </div>
    <form action="CheckoutServlet" method="post">
        <button type="submit" class="btn-proceed-to-checkout">Proceed to Checkout</button>
    </form>
</div>
        <% } %>
    </div>
    <%@ include file="fragments/footer.jsp" %>
</body>
</html>
