package controller;

import dao.ProductsDAO;
import dao.UsersDAO;
import model.Category;
import model.Products;
import model.Users;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ProductManagementController", urlPatterns = {"/admin/product-management"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10) // 10MB
public class ProductManagementController extends HttpServlet {

    UsersDAO userDAO = new UsersDAO();
    ProductsDAO productDAO = new ProductsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String emailSession = (String) session.getAttribute("email");
        Users user = userDAO.getUserByEmail(emailSession);
        if (user != null) {
            if (user.getRole().equalsIgnoreCase("marketing")) {
                String search = request.getParameter("search");
                String categoryStr = request.getParameter("category");
                List<Category> listCategory = productDAO.getAllCategories();
                List<Products> products = productDAO.getAllProduct(search, categoryStr);
                request.setAttribute("products", products);
                request.setAttribute("listCategory", listCategory);
                request.setAttribute("currentUser", user);
                request.setAttribute("title", "Quản lý sản phẩm");
                request.getRequestDispatcher("product-management.jsp").forward(request, response);
            } else {
                session.setAttribute("notificationErr", "Bạn không có quyền truy cập vào trang này");
                response.sendRedirect("../Login.jsp");
            }
        } else {
            session.setAttribute("notificationErr", "Bạn cần đăng nhập trước!");
            response.sendRedirect("../Login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String emailSession = (String) session.getAttribute("email");
        Users currentUser = userDAO.getUserByEmail(emailSession);

        if (currentUser != null && currentUser.getRole().equalsIgnoreCase("marketing")) {
            String action = request.getParameter("action");
            if ("edit".equals(action)) {
                String productIdStr = request.getParameter("productID");
                String productName = request.getParameter("productName");
                String categoryID = request.getParameter("categoryID");
                String description = request.getParameter("description");
                String provider = request.getParameter("provider");
                String priceStr = request.getParameter("price");
                String oldpriceStr = request.getParameter("oldprice");
                String warrantyPeriod = request.getParameter("warrantyPeriod");
                String amountStr = request.getParameter("amount");
                Part filePart = request.getPart("imageFile");

                if (productIdStr == null || productIdStr.trim().isEmpty()
                        || productName == null || productName.trim().isEmpty()
                        || categoryID == null || categoryID.trim().isEmpty()
                        || provider == null || provider.trim().isEmpty()
                        || priceStr == null || !priceStr.matches("\\d+(\\.\\d+)?")
                        || oldpriceStr == null || !oldpriceStr.matches("\\d+(\\.\\d+)?")
                        || warrantyPeriod == null || warrantyPeriod.trim().isEmpty()
                        || amountStr == null || !amountStr.matches("\\d+")) {
                    session.setAttribute("notificationErr", "Vui lòng nhập đầy đủ thông tin hợp lệ.");
                    response.sendRedirect(request.getContextPath() + "/marketing/product-management");
                    return;
                }

                int productID = Integer.parseInt(productIdStr);
                float price = Float.parseFloat(priceStr);
                float oldprice = Float.parseFloat(oldpriceStr);
                int amount = Integer.parseInt(amountStr);

                Products product = productDAO.getProductByID(productID);
                if (product == null) {
                    session.setAttribute("notificationErr", "Sản phẩm không tồn tại.");
                    response.sendRedirect(request.getContextPath() + "/marketing/product-management");
                    return;
                }

                product.setProductName(productName);
                product.setCategoryID(categoryID);
                product.setDescription(description);
                product.setProvider(provider);
                product.setPrice(price);
                product.setOldprice(oldprice);
                product.setWarrantyPeriod(warrantyPeriod + " năm");
                product.setAmount(amount);

                if (filePart != null && filePart.getSize() > 0) {
                    String uploadDir = getServletContext().getRealPath("/") + "assets/img/";
                    File uploadDirFile = new File(uploadDir);
                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdir();
                    }
                    String fileName = UUID.randomUUID().toString() + "-" + filePart.getSubmittedFileName();
                    String filePath = uploadDir + fileName;
                    filePart.write(filePath);
                    product.setImageLink("assets/img/" + fileName);
                }

                // Cập nhật vào DB
                boolean updated = productDAO.updateProducts(product);
                if (updated) {
                    session.setAttribute("notification", "Cập nhật sản phẩm thành công!");
                } else {
                    session.setAttribute("notificationErr", "Cập nhật sản phẩm thất bại. Vui lòng kiểm tra lại.");
                }
            } else if ("add".equals(action)) {
                String productName = request.getParameter("productName");
                String categoryID = request.getParameter("categoryID");
                String description = request.getParameter("description");
                String provider = request.getParameter("provider");
                String priceStr = request.getParameter("price");
                String oldpriceStr = request.getParameter("oldprice");
                String warrantyPeriod = request.getParameter("warrantyPeriod");
                String amountStr = request.getParameter("amount");

                Part filePart = request.getPart("imageFile"); // Uploaded file

                // Input validation
                if (productName == null || productName.trim().isEmpty()
                        || categoryID == null || categoryID.trim().isEmpty()
                        || provider == null || provider.trim().isEmpty()
                        || priceStr == null || !priceStr.matches("\\d+(\\.\\d+)?")
                        || oldpriceStr == null || !oldpriceStr.matches("\\d+(\\.\\d+)?")
                        || amountStr == null || !amountStr.matches("\\d+")) {

                    session.setAttribute("notificationErr", "Vui lòng điền đầy đủ thông tin hợp lệ.");
                    response.sendRedirect(request.getContextPath() + "/marketing/product-management");
                    return;
                }

                float price = Float.parseFloat(priceStr);
                float oldprice = Float.parseFloat(oldpriceStr);
                int amount = Integer.parseInt(amountStr);

                // Handle file upload
                String fileName = null;
                if (filePart != null && filePart.getSize() > 0) {
                    String uploadDir = getServletContext().getRealPath("/") + "assets/img/";
                    File uploadDirFile = new File(uploadDir);
                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdir();
                    }

                    fileName = UUID.randomUUID().toString() + "-" + filePart.getSubmittedFileName();
                    String filePath = uploadDir + fileName;
                    filePart.write(filePath);
                }

                // Create Product object
                Products product = new Products();
                product.setCategoryID(categoryID);
                product.setProductName(productName);
                product.setDescription(description);
                product.setProvider(provider);
                product.setPrice(price);
                product.setOldprice(oldprice);
                product.setWarrantyPeriod(warrantyPeriod + " năm");
                product.setAmount(amount);
                product.setImageLink("assets/img/" + fileName); // Save relative path

                // Add product to database
                boolean added = productDAO.addProducts(product);
                if (added) {
                    session.setAttribute("notification", "Thêm sản phẩm thành công!");
                } else {
                    session.setAttribute("notificationErr", "Thêm sản phẩm thất bại.");
                }
            } else if ("toggleStatus".equals(action)) {
                String productIdStr = request.getParameter("productID");
                String newStatus = request.getParameter("status");

                if (productIdStr == null || productIdStr.trim().isEmpty()) {
                    session.setAttribute("notificationErr", "ID sản phẩm không hợp lệ.");
                    response.sendRedirect(request.getContextPath() + "/marketing/product-management");
                    return;
                }

                int productID = Integer.parseInt(productIdStr);
                Products product = productDAO.getProductByID(productID);

                if (product == null) {
                    session.setAttribute("notificationErr", "Sản phẩm không tồn tại.");
                    response.sendRedirect(request.getContextPath() + "/marketing/product-management");
                    return;
                }

                product.setStatus(newStatus);
                boolean updated = productDAO.updateProductStatus(productID, newStatus);

                if (updated) {
                    session.setAttribute("notification", "Cập nhật trạng thái sản phẩm thành công!");
                } else {
                    session.setAttribute("notificationErr", "Cập nhật trạng thái sản phẩm thất bại.");
                }

            }
            response.sendRedirect(request.getContextPath() + "/marketing/product-management");
        } else {
            session.setAttribute("notificationErr", "Bạn không có quyền truy cập vào trang này");
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
        }
    }
}
