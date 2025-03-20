/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DashboardDAO;
import dao.UsersDAO;
import model.OrderStats;
import model.OrderTrend;
import model.RevenueByCategory;
import model.Users;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tùng Dương
 */
@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardController extends HttpServlet {

    UsersDAO userDAO = new UsersDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String emailSession = (String) session.getAttribute("email");
        Users user = userDAO.getUserByEmail(emailSession);
        if (user != null) {
            if (user.getRole().equalsIgnoreCase("Admin")) {
                request.setAttribute("title", "Dashboard");
                Date startDate, endDate;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String startDateParam = request.getParameter("startDate");
                String endDateParam = request.getParameter("endDate");

                // If the date parameters are provided, parse them. Otherwise, default to last 7 days.
                if (startDateParam != null && endDateParam != null
                        && !startDateParam.isEmpty() && !endDateParam.isEmpty()) {
                    try {
                        startDate = sdf.parse(startDateParam);
                        endDate = sdf.parse(endDateParam);
                    } catch (ParseException ex) {
                        // Fallback to default if parsing fails.
                        endDate = new Date();
                        long sevenDaysMillis = 7L * 24 * 60 * 60 * 1000;
                        startDate = new Date(endDate.getTime() - sevenDaysMillis);
                    }
                } else {
                    // Default to the last 7 days.
                    endDate = new Date();
                    long sevenDaysMillis = 7L * 24 * 60 * 60 * 1000;
                    startDate = new Date(endDate.getTime() - sevenDaysMillis);
                }

                // Retrieve dashboard data using the DAO.
                DashboardDAO dao = new DashboardDAO();
                OrderStats orderStats = dao.getNewOrdersStats(startDate, endDate);
                double totalRevenue = dao.getTotalRevenue(startDate, endDate);
                List<RevenueByCategory> revenueByCategoryList = dao.getRevenueByCategory(startDate, endDate);
                int newCustomers = dao.getNewlyBoughtCustomers(startDate, endDate);
                double overallAvgRating = dao.getOverallAverageRating(startDate, endDate);
                List<OrderTrend> orderTrendList = dao.getOrderTrendsByDay(startDate, endDate);

                // Set attributes to be used in the JSP.
                request.setAttribute("orderStats", orderStats);
                request.setAttribute("totalRevenue", totalRevenue);
                request.setAttribute("revenueByCategoryList", revenueByCategoryList);
                request.setAttribute("newCustomers", newCustomers);
                request.setAttribute("overallAvgRating", overallAvgRating);
                request.setAttribute("orderTrendList", orderTrendList);
                request.setAttribute("startDate", sdf.format(startDate));
                request.setAttribute("endDate", sdf.format(endDate));
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);

            } else {
                session.setAttribute("notificationErr", "Access Denined!");
                response.sendRedirect("../Login.jsp");
            }
        } else {
            session.setAttribute("notificationErr", "You must login first!");
            response.sendRedirect("../Login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
