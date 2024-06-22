package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User.User;

import model.User.UserDao;

/**
 * Servlet implementation class registerServlet
 */
@WebServlet("/RegisterServlet")

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UserDao userDao=new UserDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName=request.getParameter("first-name");
		String lastName=request.getParameter("last-name");
		String username=request.getParameter("username");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		
		User user=new User();
		
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);

		if(userDao.addUser(user)) {
			response.sendRedirect("login.jsp?registration=success");
		}else {
			response.sendRedirect("register.jsp?error=1");
		}
	}

}
