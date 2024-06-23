package control;

import model.Order.Order;
import model.Order.OrderDao;
import model.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session not found");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found");
            return;
        }

        // Retrieve the list of orders for the user
        OrderDao orderDao = new OrderDao();
        List<Order> orders = orderDao.getOrdersByUserId(user.getId());

        // Set the orders in the request attribute and forward to JSP
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }
}
