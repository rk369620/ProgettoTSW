package control;

import model.Cart.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session not found");
            return;
        }

        int productId;

        try {
            productId = Integer.parseInt(request.getParameter("cartItemId"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
            return;
        }

        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cart is empty");
            return;
        }

        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.getProduct().getProductId() == productId) {
                iterator.remove();
                break;
            }
        }

        session.setAttribute("cartItems", cartItems);
        response.sendRedirect("cart.jsp");
    }
}
