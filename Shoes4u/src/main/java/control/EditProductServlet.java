package control;

import model.Product.ProductDao;
import model.Product.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/EditProductServlet")
@MultipartConfig
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Path where the uploaded files will be stored
    private static final String UPLOAD_DIRECTORY = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Create path components to save the file
        final String path = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        final Part filePart = request.getPart("image");
        final String fileName = getFileName(filePart);

        // Retrieve product details from the form
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        double price = Double.parseDouble(request.getParameter("price"));
        String availability = request.getParameter("availability");
        String category = request.getParameter("category");

        // Retrieve existing product from database
        ProductDao productDAO = new ProductDao();
        Product product = productDAO.getProductById(id);

        if (product == null) {
            // Handle case where product with given id doesn't exist
            response.sendRedirect("error.jsp?msg=Product not found");
            return;
        }

        // Update product fields only if they are changed
        if (name != null && !name.isEmpty()) {
            product.setProductName(name);
        }
        if (brand != null && !brand.isEmpty()) {
            product.setBrand(brand);
        }
        if (price > 0) {
            product.setPrice(price);
        }
        if (availability != null) {
            product.setAvailability(availability);
        }
        if (category != null) {
            product.setCategory(category);
        }

        // Save the file if a new image is uploaded
        if (fileName != null && !fileName.isEmpty()) {
            File uploadDir = new File(path);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            File file = new File(path + File.separator + fileName);
            try (InputStream fileContent = filePart.getInputStream();
                 FileOutputStream out = new FileOutputStream(file)) {
                int read;
                final byte[] bytes = new byte[1024];
                while ((read = fileContent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                product.setImage(fileName); // Update the product image field
            }
        }

        // Update product in the database
        productDAO.updateProduct(product);

        // Redirect to the edit product form page
        response.sendRedirect("admin/viewProducts.jsp");
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
