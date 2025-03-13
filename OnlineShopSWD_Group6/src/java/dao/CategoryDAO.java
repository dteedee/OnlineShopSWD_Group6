package dao;

import model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CategoryDAO extends DBContext {

    public Map<String, Category> getAllCategories() {
        Map<String, Category> categoryList = new HashMap<>();
        String sql = "SELECT * FROM Category";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Category category = extractCategoryFromResultSet(rs);
                categoryList.put(category.getCategoryID(), category);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching categories: " + e.getMessage());
        }
        return categoryList;
    }

    public Vector<Category> getAllCategoriesAsVector() {
        Vector<Category> categories = new Vector<>();
        String sql = "SELECT * FROM Category";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(extractCategoryFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching categories as vector: " + e.getMessage());
        }
        return categories;
    }

    public Category getCategoryByID(String categoryID) {
        String sql = "SELECT * FROM Category WHERE CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoryID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractCategoryFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching category by ID: " + e.getMessage());
        }
        return null;
    }

    public boolean addCategory(Category category) {
        String sql = "INSERT INTO Category (CategoryID, CategoryName) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setCategoryPreparedStatement(ps, category);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding category: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCategory(Category category) {
        String sql = "UPDATE Category SET CategoryName = ? WHERE CategoryID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getCategoryID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating category: " + e.getMessage());
            return false;
        }
    }

    public boolean removeCategory(String categoryID) {
        String sql = "DELETE FROM Category WHERE CategoryID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoryID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing category: " + e.getMessage());
            return false;
        }
    }

    private Category extractCategoryFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryID(rs.getString("CategoryID"));
        category.setCategoryName(rs.getString("CategoryName"));
        return category;
    }

    private void setCategoryPreparedStatement(PreparedStatement ps, Category category) throws SQLException {
        ps.setString(1, category.getCategoryID());
        ps.setString(2, category.getCategoryName());
    }
}
