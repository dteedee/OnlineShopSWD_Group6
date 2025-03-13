package dao;

import model.CartItems;
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

public class CartItemsDAO extends DBContext {

    public Map<Integer, CartItems> getAllCartItems() {
        Map<Integer, CartItems> cartItemList = new HashMap<>();
        String sql = "SELECT * FROM CartItems";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                CartItems cartItem = extractCartItemFromResultSet(rs);
                cartItemList.put(cartItem.getCartItemID(), cartItem);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cart items: " + e.getMessage());
        }
        return cartItemList;
    }

    public List<CartItems> getCartItemsByCustomerID(int customerID) {
        String sql = "SELECT ci.CartItemID, ci.CartID, ci.ProductID, ci.Quantity, "
                + "p.ProductName, p.Price "
                + "FROM CartItems ci "
                + "Left JOIN Carts c ON ci.CartID = c.CartID "
                + "left JOIN Products p ON ci.ProductID = p.ProductID "
                + "WHERE c.CustomerID = ?";

        List<CartItems> cartItems = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItems cartItem = new CartItems();
                    cartItem.setCartItemID(rs.getInt("CartItemID"));
                    cartItem.setCartID(rs.getInt("CartID"));
                    cartItem.setProductID(rs.getInt("ProductID"));
                    cartItem.setQuantity(rs.getInt("Quantity"));

                    // Thêm dữ liệu từ bảng Products
                    cartItem.setProductName(rs.getString("ProductName"));
                    cartItem.setPrice(rs.getDouble("Price"));

                    cartItems.add(cartItem);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cart items by customer ID: " + e.getMessage());
        }
        return cartItems;
    }

    public Vector<CartItems> getAllCartItemsAsVector() {
        Vector<CartItems> cartItems = new Vector<>();
        String sql = "SELECT * FROM CartItems";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                cartItems.add(extractCartItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cart items as vector: " + e.getMessage());
        }
        return cartItems;
    }

    public CartItems getCartItemByID(int cartItemID) {
        String sql = "SELECT * FROM CartItems WHERE CartItemID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartItemID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractCartItemFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cart item by ID: " + e.getMessage());
        }
        return null;
    }

    public Vector<CartItems> getCartItemsByCartID(int cartID) {
        Vector<CartItems> cartItemsList = new Vector<>();
        String sql = "SELECT * FROM CartItems WHERE CartID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cartItemsList.add(extractCartItemFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cart items by CartID: " + e.getMessage());
        }
        return cartItemsList;
    }

    public boolean addCartItem(CartItems cartItem) {
        String sql = "INSERT INTO CartItems (CartID, ProductID, Quantity) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setCartItemPreparedStatement(ps, cartItem);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding cart item: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCartItem(CartItems cartItem) {
        String sql = "UPDATE CartItems SET CartID = ?, ProductID = ?, Quantity = ? WHERE CartItemID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setCartItemPreparedStatement(ps, cartItem);
            ps.setInt(4, cartItem.getCartItemID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating cart item: " + e.getMessage());
            return false;
        }
    }

    public boolean removeCartItem(int cartItemID) {
        String sql = "DELETE FROM CartItems WHERE CartItemID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartItemID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing cart item: " + e.getMessage());
            return false;
        }
    }

    private CartItems extractCartItemFromResultSet(ResultSet rs) throws SQLException {
        CartItems cartItem = new CartItems();
        cartItem.setCartItemID(rs.getInt("CartItemID"));
        cartItem.setCartID(rs.getInt("CartID"));
        cartItem.setProductID(rs.getInt("ProductID"));
        cartItem.setQuantity(rs.getInt("Quantity"));
        return cartItem;
    }

    private void setCartItemPreparedStatement(PreparedStatement ps, CartItems cartItem) throws SQLException {
        ps.setInt(1, cartItem.getCartID());
        ps.setInt(2, cartItem.getProductID());
        ps.setInt(3, cartItem.getQuantity());
    }

    public Map<Integer, CartItems> getCartItemsByCartIDasMap(int cartID) {
        Map<Integer, CartItems> cartItemList = new HashMap<>();
        String sql = "SELECT * FROM CartItems WHERE CartID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cartID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItems cartItem = extractCartItemFromResultSet(rs);
                    cartItemList.put(cartItem.getCartItemID(), cartItem);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cart items by CartID: " + e.getMessage());
        }
        return cartItemList;
    }

    public static void main(String[] args) {
        CartItemsDAO cDAO = new CartItemsDAO();
        System.out.println(cDAO.getCartItemsByCartIDasMap(1));
    }

    public boolean updateQuantityCartItem(int quantity, int id) {
        String sql = "UPDATE CartItems SET Quantity = ? WHERE CartItemID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating cart item: " + e.getMessage());
            return false;
        }
    }
}
