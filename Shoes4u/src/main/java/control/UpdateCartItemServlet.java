package control;

import model.Cart.CartItem;
import model.Product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/UpdateCartItemServlet")
public class UpdateCartItemServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session not found");
            return;
        }

        int productId;
        int quantity;

        try {
            productId = Integer.parseInt(request.getParameter("productId"));
            quantity = Integer.parseInt(request.getParameter("quantity"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
            return;
        }

        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cart is empty");
            return;
        }

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId() == productId) {
                cartItem.setQuantity(quantity);
                break;
            }
        }

        session.setAttribute("cartItems", cartItems);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
