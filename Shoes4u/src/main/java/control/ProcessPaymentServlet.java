package control;

import model.Order.Order;
import model.Payment.PaymentDao;
import model.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

@WebServlet("/ProcessPaymentServlet")
public class ProcessPaymentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session not found");
            return;
        }

        User user = (User) session.getAttribute("user");
        Order order = (Order) session.getAttribute("order");
        String amountStr = request.getParameter("amount");
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        String cvv = request.getParameter("cvv");

        if (user == null || order == null ||
            amountStr == null || amountStr.trim().isEmpty() ||
            cardNumber == null || cardNumber.trim().isEmpty() ||
            expiryDate == null || expiryDate.trim().isEmpty() ||
            cvv == null || cvv.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid payment data");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr.trim());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid amount format");
            return;
        }

        boolean paymentSuccessful = processPayment(cardNumber, expiryDate, cvv, amount);

        if (paymentSuccessful) {
            try {
                PaymentDao paymentDao = new PaymentDao();
                paymentDao.createPayment(order, amount, "Paid");

                session.removeAttribute("order");
                session.setAttribute("paymentSuccess", true); 
                response.sendRedirect("order_confirmation.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing payment");
            }
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Payment failed. Please try again.");
        }
    }

    private boolean processPayment(String cardNumber, String expiryDate, String cvv, double amount) {
        return true;
    }
}
