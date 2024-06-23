package control;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.Product.Product;
import model.Product.ProductDao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/AddProductServlet")
@MultipartConfig
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ProductDao productDao = new ProductDao();
    private static final String UPLOAD_DIRECTORY = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String brand = request.getParameter("brand");
        String priceStr = request.getParameter("price");
        String availability = request.getParameter("availability");
        String category = request.getParameter("category");
        String userType = request.getParameter("userType");

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("admin/addProduct.jsp?error=2");
            return;
        }

        Part filePart = request.getPart("image"); // Retrieves <input type="file" name="image">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        Product product = new Product();
        product.setProductName(productName);
        product.setBrand(brand);
        product.setPrice(price);
        product.setAvailability(availability);
        product.setCategory(category);
        product.setImage(UPLOAD_DIRECTORY + File.separator + fileName);
        product.setQuantity(0); // Impostare la quantità iniziale a zero

        if (productDao.addProduct(product)) {
            response.sendRedirect("admin/viewProducts.jsp");
        } else {
            response.sendRedirect("admin/addProduct.jsp?error=5");
        }
    }
}
