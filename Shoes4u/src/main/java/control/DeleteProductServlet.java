package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product.ProductDao;

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao = new ProductDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        if (productDao.deleteProduct(id)) {
            response.sendRedirect("admin/viewProducts.jsp?success=1");
        } else {
            response.sendRedirect("admin/viewProducts.jsp?error=1");
        }
    }
}
