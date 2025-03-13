package dao;

import model.Carts;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CartsDAO extends DBContext {

    public Map<Integer, Carts> getAllCarts() {
        Map<Integer, Carts> cartList = new HashMap<>();
        String sql = "SELECT * FROM Carts";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Carts cart = extractCartFromResultSet(rs);
                cartList.put(cart.getCartID(), cart);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching carts: " + e.getMessage());
        }
        return cartList;
    }

    public Vector<Carts> getAllCartsAsVector() {
        Vector<Carts> carts = new Vector<>();
        String sql = "SELECT * FROM Carts";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                carts.add(extractCartFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching carts as vector: " + e.getMessage());
        }
        return carts;
    }

    public Carts getCartByID(int cartID) {
        String sql = "SELECT * FROM Carts WHERE CartID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractCartFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cart by ID: " + e.getMessage());
        }
        return null;
    }

    public Carts getCartByCustomerID(int customerID) {
        String sql = "SELECT * FROM Carts WHERE CustomerID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractCartFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cart by CustomerID: " + e.getMessage());
        }
        return null;
    }

    public boolean addCart(Carts cart) {
        String sql = "INSERT INTO Carts (CustomerID, Status) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setCartPreparedStatement(ps, cart);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding cart: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCart(Carts cart) {
        String sql = "UPDATE Carts SET CustomerID = ?, Status = ? WHERE CartID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setCartPreparedStatement(ps, cart);
            ps.setInt(3, cart.getCartID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating cart: " + e.getMessage());
            return false;
        }
    }

    public boolean removeCart(int cartID) {
        String sql = "DELETE FROM Carts WHERE CartID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing cart: " + e.getMessage());
            return false;
        }
    }

    private Carts extractCartFromResultSet(ResultSet rs) throws SQLException {
        Carts cart = new Carts();
        cart.setCartID(rs.getInt("CartID"));
        cart.setCustomerID(rs.getInt("CustomerID"));
        cart.setStatus(rs.getString("Status"));
        return cart;
    }

    private void setCartPreparedStatement(PreparedStatement ps, Carts cart) throws SQLException {
        ps.setInt(1, cart.getCustomerID());
        ps.setString(2, cart.getStatus());
    }
}
