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
import java.util.List;
import java.util.UUID;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.InputStream;
import java.util.Map;

@WebServlet(name = "ProductManagementController", urlPatterns = {"/admin/product-management"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10) // 10MB
public class ProductManagementController extends HttpServlet {

    private UsersDAO userDAO = new UsersDAO();
    private ProductsDAO productDAO = new ProductsDAO();
    private Cloudinary cloudinary;

    @Override
    public void init() throws ServletException {
        super.init();
        // Không khởi tạo Cloudinary ở đây để tránh làm hỏng servlet
        // Khởi tạo sẽ được thực hiện khi cần (lazy initialization)
    }

    private void initializeCloudinary() throws ServletException {
        if (cloudinary == null) {
            synchronized (this) {
                if (cloudinary == null) {
                    try {
                        cloudinary = new Cloudinary(ObjectUtils.asMap(
                                "cloud_name", "dzutsz5ym",
                                "api_key", "717311866718988",
                                "api_secret", "Bgrn_HbsmA6Vv7x4CWq_JZmGnpo",
                                "secure", "true"
                        ));
                    } catch (Exception e) {
                        throw new ServletException("Không thể khởi tạo Cloudinary: " + e.getMessage(), e);
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String emailSession = (String) session.getAttribute("email");
        Users user = userDAO.getUserByEmail(emailSession);
        if (user != null) {
            if (user.getRole().equalsIgnoreCase("admin")) {
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

        if (currentUser != null && currentUser.getRole().equalsIgnoreCase("admin")) {
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
                    response.sendRedirect(request.getContextPath() + "/admin/product-management");
                    return;
                }

                int productID = Integer.parseInt(productIdStr);
                float price = Float.parseFloat(priceStr);
                float oldprice = Float.parseFloat(oldpriceStr);
                int amount = Integer.parseInt(amountStr);

                Products product = productDAO.getProductByID(productID);
                if (product == null) {
                    session.setAttribute("notificationErr", "Sản phẩm không tồn tại.");
                    response.sendRedirect(request.getContextPath() + "/admin/product-management");
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
                    try {
                        initializeCloudinary();

                        InputStream inputStream = filePart.getInputStream();
                        byte[] fileBytes = inputStream.readAllBytes(); 

                        Map<String, Object> uploadResult = cloudinary.uploader().upload(
                                fileBytes, 
                                ObjectUtils.asMap(
                                        "folder", "products",
                                        "public_id", UUID.randomUUID().toString(),
                                        "resource_type", "auto"
                                )
                        );

                        // Lấy URL ảnh
                        String imageUrl = (String) uploadResult.get("secure_url");
                        System.out.println("Image URL from Cloudinary: " + imageUrl); 
                        product.setImageLink(imageUrl);

                    } catch (Exception e) {
                        session.setAttribute("notificationErr", "Lỗi khi upload ảnh: " + e.getMessage());
                        response.sendRedirect(request.getContextPath() + "/admin/product-management");
                        return;
                    }
                }

                boolean updated = productDAO.updateProducts(product);
                if (updated) {
                    session.setAttribute("notification", "Cập nhật sản phẩm thành công!");
                } else {
                    session.setAttribute("notificationErr", "Cập nhật sản phẩm thất bại.");
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
                Part filePart = request.getPart("imageFile");

                if (productName == null || productName.trim().isEmpty()
                        || categoryID == null || categoryID.trim().isEmpty()
                        || provider == null || provider.trim().isEmpty()
                        || priceStr == null || !priceStr.matches("\\d+(\\.\\d+)?")
                        || oldpriceStr == null || !oldpriceStr.matches("\\d+(\\.\\d+)?")
                        || amountStr == null || !amountStr.matches("\\d+")) {

                    session.setAttribute("notificationErr", "Vui lòng điền đầy đủ thông tin hợp lệ.");
                    response.sendRedirect(request.getContextPath() + "/admin/product-management");
                    return;
                }

                float price = Float.parseFloat(priceStr);
                float oldprice = Float.parseFloat(oldpriceStr);
                int amount = Integer.parseInt(amountStr);

                Products product = new Products();
                product.setCategoryID(categoryID);
                product.setProductName(productName);
                product.setDescription(description);
                product.setProvider(provider);
                product.setPrice(price);
                product.setOldprice(oldprice);
                product.setWarrantyPeriod(warrantyPeriod + " năm");
                product.setAmount(amount);

                if (filePart != null && filePart.getSize() > 0) {
                    try {
                        initializeCloudinary();

                        InputStream inputStream = filePart.getInputStream();
                        byte[] fileBytes = inputStream.readAllBytes(); 

                        Map<String, Object> uploadResult = cloudinary.uploader().upload(
                                fileBytes, 
                                ObjectUtils.asMap(
                                        "folder", "products",
                                        "public_id", UUID.randomUUID().toString(),
                                        "resource_type", "auto"
                                )
                        );

                        // Lấy URL ảnh
                        String imageUrl = (String) uploadResult.get("secure_url");
                        product.setImageLink(imageUrl);

                    } catch (Exception e) {
                        session.setAttribute("notificationErr", "Lỗi khi upload ảnh: " + e.getMessage());
                        response.sendRedirect(request.getContextPath() + "/admin/product-management");
                        return;
                    }
                } else {
                    product.setImageLink(null);
                }

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
                    response.sendRedirect(request.getContextPath() + "/admin/product-management");
                    return;
                }

                int productID = Integer.parseInt(productIdStr);
                Products product = productDAO.getProductByID(productID);

                if (product == null) {
                    session.setAttribute("notificationErr", "Sản phẩm không tồn tại.");
                    response.sendRedirect(request.getContextPath() + "/admin/product-management");
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
            response.sendRedirect(request.getContextPath() + "/admin/product-management");
        } else {
            session.setAttribute("notificationErr", "Bạn không có quyền truy cập vào trang này");
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
        }
    }
}
