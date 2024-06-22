package control;

import model.Cart.CartItem;
import model.Product.Product;
import model.Product.ProductDao;
import model.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if the user is authenticated
        if (user == null) {
            // Redirect to login page if user is not logged in
            response.sendRedirect("login.jsp?error=2"); // Use error=2 to indicate unauthorized access to cart
            return;
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));

            // Retrieve or initialize cartItems list from session
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
                session.setAttribute("cartItems", cartItems);
            }

            // Fetch product details from database
            ProductDao productDao = new ProductDao();
            Product product = productDao.getProductById(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            // Check if the product is already in the cart
            boolean productExistsInCart = false;
            for (CartItem item : cartItems) {
                if (item.getProduct().getProductId() == product.getProductId()) {
                    // If product is already in the cart, increment the quantity by 1
                    item.setQuantity(item.getQuantity() + 1);
                    productExistsInCart = true;
                    break;
                }
            }

            if (!productExistsInCart) {
                // Create a new CartItem with the product and quantity set to 1
                CartItem cartItem = new CartItem(product, 1);
                // Add the cartItem to cartItems list
                cartItems.add(cartItem);
            }

            // Update the cartItems in the session
            session.setAttribute("cartItems", cartItems);

            // Redirect back to the product page or another appropriate page
            response.sendRedirect("cart.jsp");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
        }
    }
}
