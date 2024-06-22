package control;

import model.User.UserDao;
import model.Cart.CartDao;

import model.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        boolean isValidUser = userDao.isValidUser(username, password);

        if (isValidUser) {
        	CartDao cartDao = new CartDao();
            User user = userDao.getUserByUsername(username); // Assuming you have a method to fetch user details
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("cartDao", cartDao);
            session.setAttribute("role", "user"); // This can be dynamically set based on user details
            session.setAttribute("username", username);

            System.out.println("User " + username + " logged in with role user");

            response.sendRedirect("index.jsp");
        } else if (username.equals("admin") && password.equals("admin1")) {
            HttpSession session = request.getSession();
            session.setAttribute("role", "admin");
            session.setAttribute("username", username);

            System.out.println("Admin logged in");

            response.sendRedirect("admin/admin.jsp");
        } else {
            System.out.println("Invalid login attempt for username: " + username);
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
