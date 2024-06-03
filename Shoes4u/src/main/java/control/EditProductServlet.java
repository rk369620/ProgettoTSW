package control;

import model.ProductDao;
import model.Product;

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
        int availability = Integer.parseInt(request.getParameter("availability"));
        String category = request.getParameter("category");
        String userType = request.getParameter("userType");

        // Save the file
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
            }
        }

        // Update the product information in the database
        ProductDao productDAO = new ProductDao();
        Product product = new Product();
        if (fileName != null && !fileName.isEmpty()) {
            product.setImage(fileName); // Assuming Product has an image field
        }
        productDAO.updateProduct(product);

        // Redirect to the products page
        response.sendRedirect("editProducts.jsp");
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
