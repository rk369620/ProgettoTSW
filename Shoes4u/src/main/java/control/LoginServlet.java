package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDao;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static UserDao userDao = new UserDao();

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Recupera o crea una nuova sessione
        HttpSession session = request.getSession();

        if ("admin".equals(username) && "admin1".equals(password)) {
            session.setAttribute("username", username);
            response.sendRedirect("admin.jsp");
        } else if (userDao.isValidUser(username, password)) {
            session.setAttribute("username", username);
            response.sendRedirect("products.jsp");
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
