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
        
        // Fetch products based on the selected category
        ProductDao productDao = new ProductDao();
        List<Product> productList;
        
        if ("all".equals(category)) {
            productList = productDao.getAllProducts(); // Fetch all products
        } else {
            productList = productDao.getProductsByCategory(category); // Fetch products by category
        }
        
        // Prepare HTML response
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        for (Product product : productList) {
            out.println("<div class=\"product-card\">");
            out.println("<div class=\"product-image-container\">");
            out.println("<img src=\"" + product.getImage() + "\" alt=\"" + product.getProductName() + "\" class=\"product-image\">");
            out.println("</div>");
            out.println("<h2>" + product.getProductName() + "</h2>");
            out.println("<p>" + product.getBrand() + "</p>");
            out.println("<p class=\"price\">$" + product.getPrice() + "</p>");
            out.println("<div class=\"product-actions\">");
            out.println("<form action=\"CartServlet\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"productId\" value=\"" + product.getProductId() + "\">");
           
            out.println("<button type=\"submit\" class=\"btn-add-to-cart\">Add to Cart</button>");
            out.println("</form>");
            out.println("<form action=\"CheckoutServlet\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"productId\" value=\"" + product.getProductId() + "\">");
            out.println("<button type=\"submit\" class=\"btn-proceed-to-checkout\">Proceed to Checkout</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
        }
        
        out.close();
    }
}
