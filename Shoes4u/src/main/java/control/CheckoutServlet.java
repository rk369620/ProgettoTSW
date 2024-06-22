package control;

import model.Cart.CartItem;
import model.Order.Order;
import model.Order.OrderDao;
import model.Order.OrderItem;
import model.Product.Product;
import model.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session not found");
            return;
        }

        // Retrieve necessary objects from session
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        User user = (User) session.getAttribute("user");

        if (cartItems == null || user == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid session data");
            return;
        }

        // Update quantities in cartItems based on form input
        String[] quantities = request.getParameterValues("quantities");
        if (quantities != null && quantities.length == cartItems.size()) {
            for (int i = 0; i < cartItems.size(); i++) {
                int quantity = Integer.parseInt(quantities[i]);
                cartItems.get(i).setQuantity(quantity);
            }
        }

        // Create a new order instance
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));

        // Create order items
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProduct().getProductId());
            orderItem.setProductName(cartItem.getProduct().getProductName());
            orderItem.setProductPrice(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems); // Set order items in the order

        try {
            // Calculate total price from cart data
            double totalPrice = calculateTotalPrice(cartItems);

            // Set total price in the order
            order.setTotalPrice(totalPrice);

            // Save order to database using OrderDao
            OrderDao orderDao = new OrderDao();
            int orderId = orderDao.placeOrder(order);

            // Retrieve the saved order with its items
            order = orderDao.getOrderById(orderId);
            order.setOrderItems(orderDao.getOrderItemsByOrderId(orderId));

            // Set the order in the session
            session.setAttribute("order", order);

            // Clear cart after successful checkout
            session.setAttribute("cartItems", null);

            // Redirect to payment page or order confirmation page
            response.sendRedirect("payment.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing order");
        }
    }

    private double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}
