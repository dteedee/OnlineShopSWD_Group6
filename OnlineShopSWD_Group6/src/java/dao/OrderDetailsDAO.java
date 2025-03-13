package dao;

import model.OrderDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class OrderDetailsDAO extends DBContext {

    public Map<Integer, OrderDetails> getAllOrderDetails() {
        Map<Integer, OrderDetails> orderDetailList = new HashMap<>();
        String sql = "SELECT * FROM OrderDetails";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                OrderDetails orderDetail = extractOrderDetailFromResultSet(rs);
                orderDetailList.put(orderDetail.getOrderDetailID(), orderDetail);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order details: " + e.getMessage());
        }
        return orderDetailList;
    }

    public Vector<OrderDetails> getAllOrderDetailsAsVector() {
        Vector<OrderDetails> orderDetails = new Vector<>();
        String sql = "SELECT * FROM OrderDetails";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                orderDetails.add(extractOrderDetailFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order details as vector: " + e.getMessage());
        }
        return orderDetails;
    }

    public OrderDetails getOrderDetailByID(int orderDetailID) {
        String sql = "SELECT * FROM OrderDetails WHERE OrderDetailID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderDetailID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractOrderDetailFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order detail by ID: " + e.getMessage());
        }
        return null;
    }
     public List<OrderDetails> getOrderDetailsByOrderID1(int orderID) {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT "
                + "    od.*, "
                + "    p.ProductName as Name, "
                + "    p.ImageLink as ImgProduct, "
                + "    p.Price as PriceP, "
                + "    p.Description "
                + "FROM OrderDetails od "
                + "LEFT JOIN Products p ON od.ProductID = p.ProductID "
                + "WHERE od.OrderID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetails orderDetail = new OrderDetails();
                    orderDetail.setOrderDetailID(rs.getInt("OrderDetailID"));
                    orderDetail.setOrderID(rs.getInt("OrderID"));
                    orderDetail.setProductID(rs.getInt("ProductID"));
                    orderDetail.setQuantity(rs.getInt("Quantity"));
                    orderDetail.setPrice(rs.getDouble("Price"));
                    orderDetail.setPriceProduct(rs.getDouble("PriceP"));
                    orderDetail.setImgProduct(rs.getString("ImgProduct"));
                    orderDetail.setNameProduct(rs.getString("Name"));
                    orderDetail.setDesProduct(rs.getString("Description"));
                    orderDetailsList.add(orderDetail);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order details by OrderID: " + e.getMessage());
        }
        return orderDetailsList;
    }
    
    public Vector<OrderDetails> getOrderDetailsByOrderID(int orderID) {
        Vector<OrderDetails> orderDetailsList = new Vector<>();
        String sql = "SELECT * FROM OrderDetails WHERE OrderID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orderDetailsList.add(extractOrderDetailFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order details by OrderID: " + e.getMessage());
        }
        return orderDetailsList;
    }

    public boolean addOrderDetail(OrderDetails orderDetail) {
        String sql = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setOrderDetailPreparedStatement(ps, orderDetail);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding order detail: " + e.getMessage());
            return false;
        }
    }

    public boolean updateOrderDetail(OrderDetails orderDetail) {
        String sql = "UPDATE OrderDetails SET OrderID = ?, ProductID = ?, Quantity = ?, Price = ? WHERE OrderDetailID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setOrderDetailPreparedStatement(ps, orderDetail);
            ps.setInt(5, orderDetail.getOrderDetailID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating order detail: " + e.getMessage());
            return false;
        }
    }

    public boolean removeOrderDetail(int orderDetailID) {
        String sql = "DELETE FROM OrderDetails WHERE OrderDetailID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderDetailID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing order detail: " + e.getMessage());
            return false;
        }
    }

    private OrderDetails extractOrderDetailFromResultSet(ResultSet rs) throws SQLException {
        OrderDetails orderDetail = new OrderDetails();
        orderDetail.setOrderDetailID(rs.getInt("OrderDetailID"));
        orderDetail.setOrderID(rs.getInt("OrderID"));
        orderDetail.setProductID(rs.getInt("ProductID"));
        orderDetail.setQuantity(rs.getInt("Quantity"));
        orderDetail.setPrice(rs.getDouble("Price"));
        return orderDetail;
    }

    private void setOrderDetailPreparedStatement(PreparedStatement ps, OrderDetails orderDetail) throws SQLException {
        ps.setInt(1, orderDetail.getOrderID());
        ps.setInt(2, orderDetail.getProductID());
        ps.setInt(3, orderDetail.getQuantity());
        ps.setDouble(4, orderDetail.getPrice());
    }
    public Map<Integer, OrderDetails> getOrderDetailsByOrderIDasMap(int orderID) {
        Map<Integer, OrderDetails> orderDetailList = new HashMap<>();
        String sql = "SELECT * FROM OrderDetails WHERE OrderID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetails orderDetail = extractOrderDetailFromResultSet(rs);
                    orderDetailList.put(orderDetail.getOrderDetailID(), orderDetail);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order details by OrderID: " + e.getMessage());
        }
        return orderDetailList;
    }
    public static void main(String[] args) {
        OrderDetailsDAO odDAO = new OrderDetailsDAO();
        System.out.println(odDAO.getOrderDetailsByOrderIDasMap(6));
    }
}
