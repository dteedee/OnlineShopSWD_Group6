package controller;

import dao.CategoryDAO;
import dao.ProductsDAO;
import model.Category;
import model.Products;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller xử lý trang chủ
 *
 * @author Tùng Dương
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Khởi tạo DAO
        ProductsDAO productsDAO = new ProductsDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        // Lấy danh sách danh mục sản phẩm từ database dưới dạng Map
        Map<String, Category> categoryMap = categoryDAO.getAllCategories();
        List<Category> categories = categoryMap.values().stream().toList(); // Chuyển từ Map sang List

        // Lấy danh sách 5 sản phẩm khuyến mãi
        List<Products> promotedProducts = productsDAO.getPromotedProducts();

        for (Products pro : promotedProducts) {
            System.out.println("AAAAAAAAAA" + pro);
        }
        // Lấy danh sách sản phẩm mới
        List<Products> newProducts = productsDAO.getNewProducts();

        // Lấy tất cả sản phẩm dưới dạng danh sách
        List<Products> allProducts = productsDAO.getAllProductsAsVector();

        // Lấy sản phẩm thiết bị quạt
        List<Products> fanProducts = productsDAO.getProductsByCategory("TBQ");

        // Lấy sản phẩm thiết bị chiếu sáng
        List<Products> lightingProducts = productsDAO.getProductsByCategory("TBCS");

        // Lấy sản phẩm công tắc điện
        List<Products> electricalSwitchProducts = productsDAO.getProductsByCategory("CTD");

        // Lấy các sản phẩm thiết bị thông minh
        List<Products> smartDevices = productsDAO.getProductsByCategory("TBTM");

        // Đưa dữ liệu vào request để truyền sang JSP
        request.setAttribute("categories", categories);
        request.setAttribute("promotedProducts", promotedProducts);
        request.setAttribute("newProducts", newProducts);
        request.setAttribute("allProducts", allProducts);
        request.setAttribute("fanProducts", fanProducts);
        request.setAttribute("lightingProducts", lightingProducts);
        request.setAttribute("electricalSwitchProducts", electricalSwitchProducts);
        request.setAttribute("smartDevices", smartDevices);

        // Chuyển hướng đến trang HomePage.jsp
        request.getRequestDispatcher("HomePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
