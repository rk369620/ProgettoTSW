package control;

import model.Product.Product;
import model.Product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        
        
        ProductDao productDao = new ProductDao();
        List<Product> productList;
        
        if ("all".equals(category)) {
            productList = productDao.getAllProducts(); 
        } else {
            productList = productDao.getProductsByCategory(category); 
        }
        
       
        
    }
}
