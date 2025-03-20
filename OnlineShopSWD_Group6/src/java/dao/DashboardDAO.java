package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OrderStats;
import model.RevenueByCategory;
import model.OrderTrend;

public class DashboardDAO extends DBContext {

    public OrderStats getNewOrdersStats(java.util.Date startDate, java.util.Date endDate) {
        OrderStats stats = new OrderStats();
        String sql = "SELECT " +
                     "SUM(CASE WHEN Status = 'Delivered' THEN 1 ELSE 0 END) AS success_orders, " +
                     "SUM(CASE WHEN Status = 'Cancelled' THEN 1 ELSE 0 END) AS cancelled_orders, " +
                     "SUM(CASE WHEN Status IN ('Pending', 'Processing') THEN 1 ELSE 0 END) AS submitted_orders " +
                     "FROM Orders " +
                     "WHERE OrderDate BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(startDate.getTime()));
            ps.setTimestamp(2, new Timestamp(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stats.setSuccessOrders(rs.getInt("success_orders"));
                stats.setCancelledOrders(rs.getInt("cancelled_orders"));
                stats.setSubmittedOrders(rs.getInt("submitted_orders"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stats;
    }

    public double getTotalRevenue(java.util.Date startDate, java.util.Date endDate) {
        double totalRevenue = 0.0;
        String sql = "SELECT SUM(TotalAmount) AS total_revenue FROM Orders WHERE OrderDate BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(startDate.getTime()));
            ps.setTimestamp(2, new Timestamp(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalRevenue;
    }


    public List<RevenueByCategory> getRevenueByCategory(java.util.Date startDate, java.util.Date endDate) {
        List<RevenueByCategory> list = new ArrayList<>();
        String sql = "SELECT c.CategoryName, SUM(od.Price * od.Quantity) AS revenue " +
                     "FROM Orders o " +
                     "JOIN OrderDetails od ON o.OrderID = od.OrderID " +
                     "JOIN Products p ON od.ProductID = p.ProductID " +
                     "JOIN Category c ON p.CategoryID = c.CategoryID " +
                     "WHERE o.OrderDate BETWEEN ? AND ? " +
                     "GROUP BY c.CategoryName";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(startDate.getTime()));
            ps.setTimestamp(2, new Timestamp(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RevenueByCategory revenue = new RevenueByCategory();
                revenue.setCategoryName(rs.getString("CategoryName"));
                revenue.setRevenue(rs.getDouble("revenue"));
                list.add(revenue);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

 
    public int getNewlyBoughtCustomers(java.util.Date startDate, java.util.Date endDate) {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT CustomerID) AS newly_bought_customers " +
                     "FROM Orders WHERE OrderDate BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(startDate.getTime()));
            ps.setTimestamp(2, new Timestamp(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("newly_bought_customers");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    public double getOverallAverageRating(java.util.Date startDate, java.util.Date endDate) {
        double avgRating = 0.0;
        String sql = "SELECT AVG(Rating) AS average_rating FROM Reviews WHERE ReviewDate BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(startDate.getTime()));
            ps.setTimestamp(2, new Timestamp(endDate.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                avgRating = rs.getDouble("average_rating");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return avgRating;
    }
 
   public List<OrderTrend> getOrderTrendsByDay(java.util.Date startDate, java.util.Date endDate) {
    List<OrderTrend> list = new ArrayList<>();
    String sql = "SELECT DATE(OrderDate) AS order_day, " +
                 "COUNT(*) AS total_orders, " +
                 "SUM(CASE WHEN Status = 'Delivered' THEN 1 ELSE 0 END) AS success_orders " +
                 "FROM Orders " +
                 "WHERE OrderDate BETWEEN ? AND ? " +
                 "GROUP BY DATE(OrderDate) " +
                 "ORDER BY order_day";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setTimestamp(1, new Timestamp(startDate.getTime()));
        ps.setTimestamp(2, new Timestamp(endDate.getTime()));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            OrderTrend trend = new OrderTrend();
            // Set using java.sql.Date which is a subclass of java.util.Date
            trend.setOrderDay(rs.getDate("order_day"));
            trend.setTotalOrders(rs.getInt("total_orders"));
            trend.setSuccessOrders(rs.getInt("success_orders"));
            list.add(trend);
        }
    } catch (SQLException ex) {
        Logger.getLogger(DashboardDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}

}
