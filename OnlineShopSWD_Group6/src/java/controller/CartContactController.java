/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.*;
import model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author admin
 */
@WebServlet(name = "CartContactController", urlPatterns = {"/CartContact"})
public class CartContactController extends HttpServlet {

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
            out.println("<title>Servlet CartContactController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartContactController at " + request.getContextPath() + "</h1>");
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
    public Orders getOrderPendingByID(int userID) {
        // Giả sử bạn có một danh sách đơn hàng
        OrdersDAO oDAO = new OrdersDAO();
        Map<Integer, Orders> orderList = oDAO.getOrdersByCustomerIDasMap(userID);
        for (Orders order : orderList.values()) {
            if ("Pending".equals(order.getStatus())) {
                return order;
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tổng giá trị giỏ hàng
        //double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        OrderDetailsDAO odDAO = new OrderDetailsDAO();
        // Lấy danh sách sản phẩm đã chọn
        List<Map<String, Object>> selectedItems = new ArrayList<>();
        int index = 0;
        while (true) {
            String productID = request.getParameter("productID" + index);
            String quantity = request.getParameter("quantity" + index);
            String price = request.getParameter("price" + index);

            if (productID == null || quantity == null || price == null) {
                break; // Kết thúc khi không còn tham số
            }

            // Thêm sản phẩm vào danh sách
            Map<String, Object> item = new HashMap<>();
            item.put("productID", Integer.parseInt(productID));
            item.put("quantity", Integer.parseInt(quantity));
            item.put("price", Double.parseDouble(price));
            selectedItems.add(item);

            index++;
        }

        Map<Integer, Integer> order = new HashMap<>();
        // Thêm các sản phẩm vào đơn hàng
        for (Map<String, Object> item : selectedItems) {
            int productID = (int) item.get("productID");
            int quantity = (int) item.get("quantity");
            double price = (double) item.get("price");
            order.put(productID, quantity);
        }

        // Chuyển hướng đến trang CartContact.jsp
        request.setAttribute("list", order);
        request.getRequestDispatcher("CartContact.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String selectedItemsJson = request.getParameter("selectedItems");

        // Chuyển đổi JSON thành List<Map<String, Object>>
        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String, Object>>>() {
        }.getType();
        List<Map<String, Object>> selectedItems = gson.fromJson(selectedItemsJson, type);

        Map<Integer, Integer> order = new HashMap<>();
        // Thêm các sản phẩm vào đơn hàng
        for (Map<String, Object> item : selectedItems) {
            Object productIDObj = item.get("productID");
            Object quantityObj = item.get("quantity");

            int productID = 0;
            int quantity = 0;

            // Kiểm tra và chuyển đổi productID
            if (productIDObj instanceof Integer) {
                productID = (Integer) productIDObj;
            } else if (productIDObj instanceof String) {
                productID = Integer.parseInt((String) productIDObj);
            }

            // Kiểm tra và chuyển đổi quantity
            if (quantityObj instanceof Integer) {
                quantity = (Integer) quantityObj;
            } else if (quantityObj instanceof Double) {
                double quantityDouble = (Double) quantityObj;
                if (quantityDouble % 1 == 0) { // Kiểm tra xem có phần thập phân không
                    quantity = (int) quantityDouble; // Chuyển đổi Double sang Integer
                } else {
                    System.err.println("Quantity has decimal places. Truncating to integer: " + quantityDouble);
                    quantity = (int) quantityDouble; // Làm tròn xuống
                }
            } else if (quantityObj instanceof String) {
                try {
                    double quantityDouble = Double.parseDouble((String) quantityObj);
                    if (quantityDouble % 1 == 0) { // Kiểm tra xem có phần thập phân không
                        quantity = (int) quantityDouble; // Chuyển đổi Double sang Integer
                    } else {
                        System.err.println("Quantity has decimal places. Truncating to integer: " + quantityDouble);
                        quantity = (int) quantityDouble; // Làm tròn xuống
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error converting quantity to Double: " + e.getMessage());
                }
            } else if (quantityObj == null) {
                System.err.println("Quantity is null. Using default value 0.");
            }

            order.put(productID, quantity);
        }

        // Chuyển hướng đến trang CartContact.jsp
        request.setAttribute("list", order);
        request.getRequestDispatcher("CartContact.jsp").forward(request, response);
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
