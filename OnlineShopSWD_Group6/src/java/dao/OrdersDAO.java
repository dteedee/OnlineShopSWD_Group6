package dao;

import model.Orders;
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

public class OrdersDAO extends DBContext {

    public Map<Integer, Orders> getAllOrders() {
        Map<Integer, Orders> orderList = new HashMap<>();
        String sql = "SELECT * FROM Orders";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Orders order = extractOrderFromResultSet(rs);
                orderList.put(order.getOrderID(), order);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }
        return orderList;
    }
    public boolean updateOrderStatus(int orderId, String status) {
        boolean isUpdated = false;
        String query = "UPDATE Orders SET Status = ? WHERE OrderID = ?";

        try (// Lấy kết nối
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, status); // Set giá trị trạng thái mới
            ps.setInt(2, orderId);   // Set OrderID

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                isUpdated = true; // Nếu có bản ghi được cập nhật
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }
    public List<Orders> searchOrders(String search, String fromDate, String toDate, String saleName, String status) {
    List<Orders> orderList = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT o.*, u.FirstName, u.LastName FROM Orders o ");
    sql.append("JOIN Users u ON o.CustomerID = u.UserID WHERE 1=1");

   
    if (search != null && !search.isEmpty()) {
        sql.append(" AND (o.OrderID LIKE ? OR u.FirstName LIKE ? OR u.LastName LIKE ?)");
    }
    if (fromDate != null && !fromDate.isEmpty()) {
        sql.append(" AND o.OrderDate >= ?");
    }
    if (toDate != null && !toDate.isEmpty()) {
        sql.append(" AND o.OrderDate <= ?");
    }
    if (saleName != null && !saleName.isEmpty()) {
        sql.append(" AND o.SaleName LIKE ?");
    }
    if (status != null && !status.isEmpty()) {
        sql.append(" AND o.Status = ?");
    }

    try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
        int paramIndex = 1;
        
     
        if (search != null && !search.isEmpty()) {
            ps.setString(paramIndex++, "%" + search + "%");  
          
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            ps.setString(paramIndex++, fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            ps.setString(paramIndex++, toDate);
        }
        if (saleName != null && !saleName.isEmpty()) {
            ps.setString(paramIndex++, "%" + saleName + "%");
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }

        // Thực thi câu truy vấn
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Orders order = extractOrderFromResultSet(rs);
                // Thêm thông tin khách hàng (FirstName, LastName, Email) vào đối tượng Order
                order.setCustomerFirstName(rs.getString("FirstName"));
                order.setCustomerLastName(rs.getString("LastName"));
                order.setCustomerEmail(rs.getString("Email"));
                orderList.add(order);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error executing search query: " + e.getMessage());
    }
    return orderList;
}

    public List<Orders> getOrdersPaginated(String search, String fromDate, String toDate, String saleName, String status, int page, int pageSize) {
    List<Orders> orderList = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT o.*, u.FirstName, u.LastName, u.Email FROM Orders o ");
    sql.append("JOIN Users u ON o.CustomerID = u.UserID WHERE 1=1");

    if (search != null && !search.isEmpty()) {
        sql.append(" AND (o.OrderID LIKE ? OR o.CustomerID LIKE ?)");
    }
    if (fromDate != null && !fromDate.isEmpty()) {
        sql.append(" AND o.OrderDate >= ?");
    }
    if (toDate != null && !toDate.isEmpty()) {
        sql.append(" AND o.OrderDate <= ?");
    }
    if (saleName != null && !saleName.isEmpty()) {
        sql.append(" AND o.SaleName LIKE ?");
    }
    if (status != null && !status.isEmpty()) {
        sql.append(" AND o.Status = ?");
    }

    int offset = (page - 1) * pageSize;
    sql.append(" LIMIT ? OFFSET ?");

    try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
        int paramIndex = 1;
        if (search != null && !search.isEmpty()) {
            ps.setString(paramIndex++, "%" + search + "%");
            ps.setString(paramIndex++, "%" + search + "%");
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            ps.setString(paramIndex++, fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            ps.setString(paramIndex++, toDate);
        }
        if (saleName != null && !saleName.isEmpty()) {
            ps.setString(paramIndex++, "%" + saleName + "%");
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }

        ps.setInt(paramIndex++, pageSize);
        ps.setInt(paramIndex++, offset);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Orders order = extractOrderFromResultSet(rs);
                
                order.setCustomerFirstName(rs.getString("FirstName"));
                order.setCustomerLastName(rs.getString("LastName"));
                order.setCustomerEmail(rs.getString("Email"));
                orderList.add(order);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error executing search query: " + e.getMessage());
    }
    return orderList;
}


    
public int getTotalOrders(String search, String fromDate, String toDate, String saleName, String status) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Orders o ");
    sql.append("JOIN Users u ON o.CustomerID = u.UserID WHERE 1=1");

    if (search != null && !search.isEmpty()) {
        sql.append(" AND (o.OrderID LIKE ? OR u.FirstName LIKE ? OR u.LastName LIKE ?)");
    }
    if (fromDate != null && !fromDate.isEmpty()) {
        sql.append(" AND o.OrderDate >= ?");
    }
    if (toDate != null && !toDate.isEmpty()) {
        sql.append(" AND o.OrderDate <= ?");
    }
    if (saleName != null && !saleName.isEmpty()) {
        sql.append(" AND o.SaleName LIKE ?");
    }
    if (status != null && !status.isEmpty()) {
        sql.append(" AND o.Status = ?");
    }

    try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
        int paramIndex = 1;
        if (search != null && !search.isEmpty()) {
            ps.setString(paramIndex++, "%" + search + "%"); // tìm theo OrderID
            ps.setString(paramIndex++, "%" + search + "%"); // tìm theo FirstName
            ps.setString(paramIndex++, "%" + search + "%"); // tìm theo LastName
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            ps.setString(paramIndex++, fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            ps.setString(paramIndex++, toDate);
        }
        if (saleName != null && !saleName.isEmpty()) {
            ps.setString(paramIndex++, "%" + saleName + "%");
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(paramIndex++, status);
        }

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); // Trả về tổng số đơn hàng
            }
        }
    } catch (SQLException e) {
        System.out.println("Error getting total orders: " + e.getMessage());
    }
    return 0;
}

    public List<Orders> getAllOrders1() {
        List<Orders> orderList = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Orders order = extractOrderFromResultSet(rs);
                orderList.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }
        return orderList;
    }

    public Vector<Orders> getAllOrdersAsVector() {
        Vector<Orders> orders = new Vector<>();
        String sql = "SELECT * FROM Orders";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(extractOrderFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders as vector: " + e.getMessage());
        }
        return orders;
    }

    public Orders getOrderByID(int orderID) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractOrderFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Orders> getAllOrderByUser(int customerID, int page, int limit) {
        List<Orders> orderses = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE CustomerID = ? LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            ps.setInt(2, limit);
            ps.setInt(3, (page - 1) * limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orderses.add(extractOrderFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching paginated orders by CustomerID: " + e.getMessage());
        }
        return orderses;
    }

    public int getTotalOrdersByUser(int customerID) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE CustomerID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error counting total orders: " + e.getMessage());
        }
        return 0;
    }

    
    public Vector<Orders> getOrdersByCustomerID(int customerID) {
        Vector<Orders> ordersList = new Vector<>();
        String sql = "SELECT * FROM Orders WHERE CustomerID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ordersList.add(extractOrderFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders by CustomerID: " + e.getMessage());
        }
        return ordersList;
    }

    public boolean addOrder(Orders order) {
        String sql = "INSERT INTO Orders (CustomerID, OrderDate, DeliveryAddress, Status, TotalAmount, BillOfLading) VALUES (?, NOW(), ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order.getCustomerID());
            ps.setString(2, order.getDeliveryAddress());
            ps.setString(3, order.getStatus());
            ps.setDouble(4, order.getTotalAmount());
            ps.setString(5, order.getBillOfLading());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding order: " + e.getMessage());
            return false;
        }
    }

    public boolean updateOrder(Orders order) {
        String sql = "UPDATE Orders SET CustomerID = ?, OrderDate = ?, DeliveryAddress = ?, Status = ?, TotalAmount = ?, BillOfLading = ? WHERE OrderID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setOrderPreparedStatement(ps, order);
            ps.setInt(7, order.getOrderID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating order: " + e.getMessage());
            return false;
        }
    }

    public boolean removeOrder(int orderID) {
        String sql = "DELETE FROM Orders WHERE OrderID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing order: " + e.getMessage());
            return false;
        }
    }

    private Orders extractOrderFromResultSet(ResultSet rs) throws SQLException {
        Orders order = new Orders();
        order.setOrderID(rs.getInt("OrderID"));
        order.setCustomerID(rs.getInt("CustomerID"));
        order.setOrderDate(rs.getString("OrderDate"));
        order.setDeliveryAddress(rs.getString("DeliveryAddress"));
        order.setStatus(rs.getString("Status"));
        order.setTotalAmount(rs.getDouble("TotalAmount"));
        order.setBillOfLading(rs.getString("BillOfLading"));
        return order;
    }

    private void setOrderPreparedStatement(PreparedStatement ps, Orders order) throws SQLException {
        ps.setInt(1, order.getCustomerID());
        ps.setString(2, order.getOrderDate());
        ps.setString(3, order.getDeliveryAddress());
        ps.setString(4, order.getStatus());
        ps.setDouble(5, order.getTotalAmount());
        ps.setString(6, order.getBillOfLading());
    }

    public Map<Integer, Orders> getOrdersByCustomerIDasMap(int customerID) {
        Map<Integer, Orders> orderList = new HashMap<>();
        String sql = "SELECT * FROM Orders WHERE CustomerID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Orders order = extractOrderFromResultSet(rs);
                    orderList.put(order.getOrderID(), order);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders by CustomerID: " + e.getMessage());
        }
        return orderList;
    }

    public Orders getOrderPendingByID(int orderID) {
        String status = "Pending";
        String sql = "SELECT * FROM Orders WHERE CustomerID = ? AND Status = 'Pending'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractOrderFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order by ID: " + e.getMessage());
        }
        return null;
    }

    public Orders getLatestOrder() {
        String sql = "SELECT * FROM Orders ORDER BY OrderID DESC LIMIT 1"; // Sắp xếp giảm dần và lấy 1 bản ghi đầu tiên
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return extractOrderFromResultSet(rs); // Trả về Order mới nhất
            }
        } catch (SQLException e) {
            System.out.println("Error fetching the latest order: " + e.getMessage());
        }
        return null; // Trả về null nếu không tìm thấy
    }

    public static void main(String[] args) {
        OrdersDAO oDAO = new OrdersDAO();
        System.out.println(oDAO.addOrder(new Orders(4, null, "Hanoi", "Submitted", 100000, "No comment")));
    }
}
