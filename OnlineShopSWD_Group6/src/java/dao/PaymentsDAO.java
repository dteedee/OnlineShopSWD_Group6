package dao;

import model.Payments;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PaymentsDAO extends DBContext {

    public Map<Integer, Payments> getAllPayments() {
        Map<Integer, Payments> paymentList = new HashMap<>();
        String sql = "SELECT * FROM Payments";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Payments payment = extractPaymentFromResultSet(rs);
                paymentList.put(payment.getPaymentID(), payment);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching payments: " + e.getMessage());
        }
        return paymentList;
    }

    public Vector<Payments> getAllPaymentsAsVector() {
        Vector<Payments> payments = new Vector<>();
        String sql = "SELECT * FROM Payments";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                payments.add(extractPaymentFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching payments as vector: " + e.getMessage());
        }
        return payments;
    }

    public Payments getPaymentByID(int paymentID) {
        String sql = "SELECT * FROM Payments WHERE PaymentID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, paymentID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractPaymentFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching payment by ID: " + e.getMessage());
        }
        return null;
    }

    public Payments getPaymentByOrderID(int orderID) {
        String sql = "SELECT * FROM Payments WHERE OrderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractPaymentFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching payment by OrderID: " + e.getMessage());
        }
        return null;
    }

    public boolean addPayment(Payments payment) {
        String sql = "INSERT INTO Payments (OrderID, PaymentMethod, Price, PaymentDate, PaymentStatus, TransactionCode) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setPaymentPreparedStatement(ps, payment);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding payment: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePayment(Payments payment) {
        String sql = "UPDATE Payments SET OrderID = ?, PaymentMethod = ?, Price = ?, PaymentDate = ?, PaymentStatus = ?, TransactionCode = ? WHERE PaymentID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setPaymentPreparedStatement(ps, payment);
            ps.setInt(7, payment.getPaymentID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating payment: " + e.getMessage());
            return false;
        }
    }

    public boolean removePayment(int paymentID) {
        String sql = "DELETE FROM Payments WHERE PaymentID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, paymentID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing payment: " + e.getMessage());
            return false;
        }
    }

    private Payments extractPaymentFromResultSet(ResultSet rs) throws SQLException {
        Payments payment = new Payments();
        payment.setPaymentID(rs.getInt("PaymentID"));
        payment.setOrderID(rs.getInt("OrderID"));
        payment.setPaymentMethod(rs.getString("PaymentMethod"));
        payment.setPrice(rs.getDouble("Price"));
        payment.setPaymentDate(rs.getString("PaymentDate"));
        payment.setPaymentStatus(rs.getString("PaymentStatus"));
        payment.setTransactionCode(rs.getString("TransactionCode"));
        return payment;
    }

    private void setPaymentPreparedStatement(PreparedStatement ps, Payments payment) throws SQLException {
        ps.setInt(1, payment.getOrderID());
        ps.setString(2, payment.getPaymentMethod());
        ps.setDouble(3, payment.getPrice());
        ps.setString(4, payment.getPaymentDate());
        ps.setString(5, payment.getPaymentStatus());
        ps.setString(6, payment.getTransactionCode());
    }
}
