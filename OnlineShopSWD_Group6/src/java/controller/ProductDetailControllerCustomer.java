package controller;

import dao.CategoryDAO;
import dao.ProductsDAO;
import model.Category;
import model.Products;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
/**
 *
 * @author admin
 */
@WebServlet(name="ProductDetailControllerCustomer", urlPatterns={"/ProductDetailControllerCustomer"})
public class ProductDetailControllerCustomer extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductDetailController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductsDAO pDAO = new ProductsDAO();
         // Khởi tạo DAO
        ProductsDAO productsDAO = new ProductsDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        // Lấy danh sách danh mục sản phẩm từ database dưới dạng Map
        Map<String, Category> categoryMap = categoryDAO.getAllCategories();
        List<Category> categories = categoryMap.values().stream().toList(); // Chuyển từ Map sang List

        // Lấy danh sách 5 sản phẩm khuyến mãi
        List<Products> promotedProducts = productsDAO.getPromotedProducts();

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

        Products p = pDAO.getProductByID(id);
        request.setAttribute("product", p);
        request.getRequestDispatcher("ProductDetailCustomer.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}