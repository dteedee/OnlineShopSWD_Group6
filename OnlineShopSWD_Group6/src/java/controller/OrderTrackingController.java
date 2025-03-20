/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDetailsDAO;
import dao.OrdersDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import model.OrderDetails;
import model.Orders;

@WebServlet(name = "OrderTrackingController", urlPatterns = {"/OrderTrackingController"})
public class OrderTrackingController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String trackingCode = request.getParameter("trackingCode");
        OrdersDAO ordersDAO = new OrdersDAO();
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
        Orders order = null;

        try {
            // Thử parse trackingCode thành OrderID (mã số)
            int orderId = Integer.parseInt(trackingCode);
            order = ordersDAO.getOrderByID(orderId);
        } catch (NumberFormatException e) {
            // Nếu không phải số, tìm theo BillOfLading
            order = findOrderByBillOfLading(ordersDAO, trackingCode);
        }

        if (order != null) {
            // Lấy chi tiết đơn hàng
            List<OrderDetails> orderDetailsList = orderDetailsDAO.getOrderDetailsByOrderID1(order.getOrderID());
            request.setAttribute("order", order);
            request.setAttribute("orderDetails", orderDetailsList);
        } else {
            request.setAttribute("error", "Không tìm thấy đơn hàng với mã: " + trackingCode);
        }

        // Chuyển hướng về trang JSP
        request.getRequestDispatcher("OrderTracking.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang OrderTracking.jsp khi truy cập bằng GET
        request.getRequestDispatcher("OrderTracking.jsp").forward(request, response);
    }

    // Hàm tìm đơn hàng theo BillOfLading
    private Orders findOrderByBillOfLading(OrdersDAO ordersDAO, String billOfLading) {
        Map<Integer, Orders> allOrders = ordersDAO.getAllOrders();
        for (Orders order : allOrders.values()) {
            if (order.getBillOfLading() != null && order.getBillOfLading().equalsIgnoreCase(billOfLading)) {
                return order;
            }
        }
        return null;
    }
}
