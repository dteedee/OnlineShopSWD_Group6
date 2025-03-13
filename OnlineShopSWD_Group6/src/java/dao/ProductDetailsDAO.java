package dao;

import model.ProductDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ProductDetailsDAO extends DBContext {

    public Map<Integer, ProductDetails> getAllProductDetails() {
        Map<Integer, ProductDetails> productDetailList = new HashMap<>();
        String sql = "SELECT * FROM ProductDetails";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ProductDetails productDetail = extractProductDetailFromResultSet(rs);
                productDetailList.put(productDetail.getProductDetailID(), productDetail);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product details: " + e.getMessage());
        }
        return productDetailList;
    }

    public Vector<ProductDetails> getAllProductDetailsAsVector() {
        Vector<ProductDetails> productDetails = new Vector<>();
        String sql = "SELECT * FROM ProductDetails";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                productDetails.add(extractProductDetailFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product details as vector: " + e.getMessage());
        }
        return productDetails;
    }

    public ProductDetails getProductDetailByID(int productDetailID) {
        String sql = "SELECT * FROM ProductDetails WHERE ProductDetailID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productDetailID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractProductDetailFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product detail by ID: " + e.getMessage());
        }
        return null;
    }

    public boolean addProductDetail(ProductDetails productDetail) {
        String sql = "INSERT INTO ProductDetails (ProductID, ProductDetailName, Value) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setProductDetailPreparedStatement(ps, productDetail);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding product detail: " + e.getMessage());
            return false;
        }
    }

    public boolean updateProductDetail(ProductDetails productDetail) {
        String sql = "UPDATE ProductDetails SET ProductID = ?, ProductDetailName = ?, Value = ? WHERE ProductDetailID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setProductDetailPreparedStatement(ps, productDetail);
            ps.setInt(4, productDetail.getProductDetailID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating product detail: " + e.getMessage());
            return false;
        }
    }

    public boolean removeProductDetail(int productDetailID) {
        String sql = "DELETE FROM ProductDetails WHERE ProductDetailID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productDetailID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing product detail: " + e.getMessage());
            return false;
        }
    }

    public Vector<ProductDetails> getProductDetailsByProductID(int productID) {
        Vector<ProductDetails> productDetailsList = new Vector<>();
        String sql = "SELECT * FROM ProductDetails WHERE ProductID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productDetailsList.add(extractProductDetailFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product details by ProductID: " + e.getMessage());
        }
        return productDetailsList;
    }

    private ProductDetails extractProductDetailFromResultSet(ResultSet rs) throws SQLException {
        ProductDetails productDetail = new ProductDetails();
        productDetail.setProductDetailID(rs.getInt("ProductDetailID"));
        productDetail.setProductID(rs.getInt("ProductID"));
        productDetail.setProductDetailName(rs.getString("ProductDetailName"));
        productDetail.setValue(rs.getString("Value"));
        return productDetail;
    }

    private void setProductDetailPreparedStatement(PreparedStatement ps, ProductDetails productDetail) throws SQLException {
        ps.setInt(1, productDetail.getProductID());
        ps.setString(2, productDetail.getProductDetailName());
        ps.setString(3, productDetail.getValue());
    }
}
