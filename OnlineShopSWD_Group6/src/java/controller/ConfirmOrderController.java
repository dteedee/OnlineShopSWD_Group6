/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.*;
import model.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author admin
 */
@WebServlet(name = "ConfirmOrderController", urlPatterns = {"/ConfirmOrder2"})
public class ConfirmOrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet ConfirmOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConfirmOrderController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        float total = 0;
        CartItemsDAO ciDAO = new CartItemsDAO();
        HttpSession session = request.getSession();
        OrdersDAO oDAO = new OrdersDAO();
        OrderDetailsDAO odDAO = new OrderDetailsDAO();
        ProductsDAO pDAO = new ProductsDAO();
        UsersDAO uDAO = new UsersDAO();
        Users user = (Users) session.getAttribute("user");
        try {
            // Đọc dữ liệu JSON từ request
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String jsonData = jsonBuilder.toString();

            // Sử dụng JsonParser để phân tích JSON
            JsonParser parser = new JsonParser();
            JsonObject orderData = parser.parse(jsonData).getAsJsonObject();

            // Lấy các giá trị từ JsonObject
            String comment = "";
            if (orderData.has("comment")) { // Kiểm tra xem trường "comment" có tồn tại không
                comment = orderData.get("comment").getAsString();
            } else {
                comment = "No comment provided"; // Giá trị mặc định nếu trường không tồn tại
            }
            String paymentMethod = "";
            if (orderData.has("paymentMethod")) { // Kiểm tra xem trường "comment" có tồn tại không
                paymentMethod = orderData.get("paymentMethod").getAsString();
            } else {
                paymentMethod = "Cash on delivery"; // Giá trị mặc định nếu trường không tồn tại
            }
            String firstname = orderData.get("firstname").getAsString();
            String lastname = orderData.get("lastname").getAsString();
            String email = orderData.get("email").getAsString();
            String phone = orderData.get("phone").getAsString();
            String gender = orderData.get("gender").getAsString();
            String address = orderData.get("address").getAsString();
            JsonArray productsArray = orderData.get("products").getAsJsonArray();
            
            // Xử lý danh sách sản phẩm
            for (int i = 0; i < productsArray.size(); i++) {
                JsonObject product = productsArray.get(i).getAsJsonObject();
                int productId = product.get("productId").getAsInt();
                int quantity = product.get("quantity").getAsInt();
                System.out.println(quantity);
                double price = pDAO.getProductByID(ciDAO.getCartItemByID(productId).getProductID()).getPrice() * quantity;
                System.out.println(price);
                total += price;
            }

                // Cập nhật thông tin người dùng
                user.setFirstName(firstname);
                user.setLastName(lastname);
                user.setEmail(email);
                user.setPhoneNumber(phone);
                user.setGender(gender);
                user.setAddress(address);
                uDAO.updateUser(user);
                System.out.println(oDAO.addOrder(new Orders(user.getUserID(), null, user.getAddress(), "Submitted", total, comment)));
                // Thêm đơn hàng mới
                boolean isOrderAdded = oDAO.addOrder(new Orders(user.getUserID(), null, user.getAddress(), "Submitted", total, comment));
                
                if (!isOrderAdded) {
                    
                    throw new Exception("Failed to add order.");
                }

                // Thêm chi tiết đơn hàng
                Orders latestOrder = oDAO.getLatestOrder();
                if (latestOrder == null) {
                    throw new Exception("Failed to retrieve the latest order.");
                }

                for (int i = 0; i < productsArray.size(); i++) {
                    JsonObject product = productsArray.get(i).getAsJsonObject();
                    int cartitid = product.get("productId").getAsInt();
                    int quantity = product.get("quantity").getAsInt();
                    double price = pDAO.getProductByID(ciDAO.getCartItemByID(cartitid).getProductID()).getPrice() * quantity;
                    boolean isDetailAdded = odDAO.addOrderDetail(new OrderDetails(latestOrder.getOrderID(), ciDAO.getCartItemByID(cartitid).getProductID(), quantity, price));
                    if (!isDetailAdded) {
                        throw new Exception("Failed to add order details.");
                    }
                    ciDAO.removeCartItem(cartitid);
                }
            session.setAttribute("order", latestOrder);
            // Trả về JSON hợp lệ
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String responseJson = "{\"success\": true, \"message\": \"Order processed successfully!\"}";
            response.getWriter().write(responseJson);
        } catch (Exception e) {
            // Trả về thông báo lỗi dưới dạng văn bản thuần
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
