package dao;

import model.Category;
import model.Products;
import model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ProductsDAO extends DBContext {

    public Map<Integer, Products> getAllProducts() {
        Map<Integer, Products> list = new HashMap<>();
        try {
            String sql = "SELECT * "
                    + "FROM Products";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Products b = new Products();
                b.setProductID(rs.getInt("ProductID"));
                b.setCategoryID(rs.getString("CategoryID"));
                b.setProductName(rs.getString("ProductName"));
                b.setDescription(rs.getString("Description"));
                b.setProvider(rs.getString("Provider"));
                b.setPrice(rs.getFloat("Price"));
                b.setWarrantyPeriod(rs.getString("WarrantyPeriod"));
                b.setAmount(rs.getInt("Amount"));
                b.setImageLink(rs.getString("ImageLink"));
                b.setIsPromoted(rs.getBoolean("IsPromoted"));
                b.setCreateAt(rs.getDate("CreateAt"));
                b.setOldprice(rs.getFloat("OldPrice"));
                list.put(b.getProductID(), b);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Vector<Products> getAllProductsAsVector() {
        Vector<Products> products = new Vector<>();
        String sql = "SELECT * FROM Products";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                products.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products as vector: " + e.getMessage());
        }
        return products;
    }

    public List<Products> getPromotedProducts() {
        List<Products> promotedProducts = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE IsPromoted = 1";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                promotedProducts.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching promoted products: " + e.getMessage());
        }
        return promotedProducts;
    }

    public List<Products> getNewProducts() {
        List<Products> newProducts = new ArrayList<>();
        String sql = "SELECT * FROM Products ORDER BY CreateAt DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                newProducts.add(extractProductFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching new products: " + e.getMessage());
        }
        return newProducts;
    }

    public Products getProductByID(int id) {
        String sql = "SELECT * FROM Products WHERE ProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractProductFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Products> getProductsByCategory(String categoryID) {
        List<Products> productList = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE CategoryID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoryID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productList.add(extractProductFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products by category: " + e.getMessage());
        }
        return productList;
    }

    public boolean addProduct(Products product) {
        String sql = "INSERT INTO Products (CategoryID, ProductName, Description, Provider, Price, WarrantyPeriod, Amount, ImageLink, IsPromoted, OldPrice, CreateAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setProductPreparedStatement(ps, product);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    public boolean addProducts(Products product) {
        String sql = "INSERT INTO Products (CategoryID, ProductName, Description, Provider, Price, WarrantyPeriod, Amount, ImageLink, IsPromoted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getCategoryID());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setString(4, product.getProvider());
            ps.setFloat(5, product.getPrice());
            ps.setString(6, product.getWarrantyPeriod());
            ps.setInt(7, product.getAmount());
            ps.setString(8, product.getImageLink());
            ps.setBoolean(9, true);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    public boolean updateProduct(Products product) {
        String sql = "UPDATE Products SET CategoryID = ?, ProductName = ?, Description = ?, Provider = ?, Price = ?, WarrantyPeriod = ?, Amount = ?, ImageLink = ?, IsPromoted = ?, OldPrice = ?, CreateAt = ? WHERE ProductID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setProductPreparedStatement(ps, product);
            ps.setInt(12, product.getProductID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    public boolean updateProducts(Products product) {
        String sql = "UPDATE Products SET CategoryID = ?, ProductName = ?, Description = ?, Provider = ?, Price = ?, WarrantyPeriod = ?, Amount = ?, ImageLink = ?, IsPromoted = ?, OldPrice = ? WHERE ProductID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setProductPreparedStatement(ps, product);
            ps.setInt(11, product.getProductID());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    public boolean removeProduct(int id) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing product: " + e.getMessage());
            return false;
        }
    }

    public List<Products> getProductsByPaging(int index, int pageSize, String name, List<String> categoryIds, String orderBy) {
        List<Products> productList = new LinkedList<>();
        String sql = "SELECT * FROM Products WHERE ProductName like ? ";
        if (categoryIds.size() != 0) {
            sql += " AND ";
            String conditionCategory = "(";
            for (String categoryId : categoryIds) {
                conditionCategory += "CategoryID = ? OR ";
            }
            conditionCategory = conditionCategory.substring(0, conditionCategory.length() - 3) + ")";
            sql += conditionCategory;
        }
        sql += " ORDER BY " + orderBy;
        sql += " LIMIT ? OFFSET ? ";
        System.out.println(sql);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int indexQuery = 1;
            ps.setString(indexQuery++, "%" + name + "%");
            if (categoryIds.size() != 0) {
                for (String categoryId : categoryIds) {
                    ps.setString(indexQuery++, categoryId);
                }
            }
//            ps.setString(indexQuery++, orderBy);
            ps.setInt(indexQuery++, pageSize);
            ps.setInt(indexQuery++, (index - 1) * pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productList.add(extractProductFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products by category: " + e.getMessage());
        }
        return productList;
    }

    public int getTotalProducts(String name, List<String> categoryIds, String orderBy) {
        String sql = "SELECT COUNT(*) FROM Products WHERE ProductName like ? ";
        if (categoryIds.size() != 0) {
            sql += " AND ";
            String conditionCategory = "(";
            for (String categoryId : categoryIds) {
                conditionCategory += "CategoryID = ? OR ";
            }
            conditionCategory = conditionCategory.substring(0, conditionCategory.length() - 3) + ")";
            sql += conditionCategory;
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int indexQuery = 1;
            ps.setString(indexQuery++, "%" + name + "%");
            if (categoryIds.size() != 0) {
                for (String categoryId : categoryIds) {
                    ps.setString(indexQuery++, categoryId);
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products by category: " + e.getMessage());
        }
        return 0;
    }

    private Products extractProductFromResultSet(ResultSet rs) throws SQLException {
        Products product = new Products();
        product.setProductID(rs.getInt("ProductID"));
        product.setCategoryID(rs.getString("CategoryID"));
        product.setProductName(rs.getString("ProductName"));
        product.setDescription(rs.getString("Description"));
        product.setProvider(rs.getString("Provider"));
        product.setPrice(rs.getFloat("Price"));
        product.setOldprice(rs.getFloat("OldPrice"));
        product.setWarrantyPeriod(rs.getString("WarrantyPeriod"));
        product.setAmount(rs.getInt("Amount"));
        product.setImageLink(rs.getString("ImageLink"));
        product.setIsPromoted(rs.getBoolean("IsPromoted"));
        product.setStatus(rs.getString("status"));
        return product;
    }

    private void setProductPreparedStatement(PreparedStatement ps, Products product) throws SQLException {
        ps.setString(1, product.getCategoryID());
        ps.setString(2, product.getProductName());
        ps.setString(3, product.getDescription());
        ps.setString(4, product.getProvider());
        ps.setFloat(5, product.getPrice());
        ps.setString(6, product.getWarrantyPeriod());
        ps.setInt(7, product.getAmount());
        ps.setString(8, product.getImageLink());
        ps.setBoolean(9, product.getIsPromoted());
        ps.setFloat(10, product.getOldprice());
    }

    public Products GetProductbyID(int id) {
        try {
            String sql = "Select * from Products where ProductID = " + id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                Products b = new Products();
                b.setProductID(rs.getInt("ProductID"));
                b.setCategoryID(rs.getString("CategoryID"));
                b.setProductName(rs.getString("ProductName"));
                b.setDescription(rs.getString("Description"));
                b.setProvider(rs.getString("Provider"));
                b.setPrice(rs.getFloat("Price"));
                b.setWarrantyPeriod(rs.getString("WarrantyPeriod"));
                b.setAmount(rs.getInt("Amount"));
                b.setImageLink(rs.getString("ImageLink"));
                b.setIsPromoted(rs.getBoolean("IsPromoted"));
                b.setCreateAt(rs.getDate("CreateAt"));
                b.setOldprice(rs.getFloat("OldPrice"));
                b.setStatus(rs.getString("status"));
                return b;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public boolean updateProduct1(Products product) {
        String sql = "UPDATE Products SET CategoryID = ?, ProductName = ?, Description = ?, Provider = ?, Price = ?, WarrantyPeriod = ?, Amount = ?, ImageLink = ?, IsPromoted = ?, OldPrice = ? WHERE ProductID = ?";

        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setString(1, product.getCategoryID());
            pre.setString(2, product.getProductName());
            pre.setString(3, product.getDescription());
            pre.setString(4, product.getProvider());
            pre.setFloat(5, product.getPrice());
            pre.setString(6, product.getWarrantyPeriod());
            pre.setInt(7, product.getAmount());
            pre.setString(8, product.getImageLink());
            pre.setBoolean(9, product.getIsPromoted());
            pre.setFloat(10, product.getOldprice());
            pre.setInt(11, product.getProductID());

            pre.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    public Category GetCategorybyID(String id) {
        try {
            String sql = "Select * from Category where CategoryID = " + "'" + id + "'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                Category b = new Category();
                b.setCategoryID(rs.getString("CategoryID"));
                b.setCategoryName(rs.getString("CategoryName"));
                return b;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            String query = "SELECT * FROM Category";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(new Category(rs.getString("CategoryID"), rs.getString("CategoryName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
    public List<Products> getAllProducts1() {
    List<Products> products = new ArrayList<>();
    try {
        String query = "SELECT * FROM products ORDER BY ProductID DESC";
        PreparedStatement st = connection.prepareStatement(query);

        try (ResultSet rs = st.executeQuery()) {
            CategoryDAO cdao = new CategoryDAO();
            while (rs.next()) {
                Products product = new Products();
                product.setProductID(rs.getInt("ProductID"));
                product.setCategoryID(rs.getString("CategoryID"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setProvider(rs.getString("Provider"));
                product.setPrice(rs.getFloat("Price"));
                product.setOldprice(rs.getFloat("OldPrice"));
                product.setWarrantyPeriod(rs.getString("WarrantyPeriod"));
                product.setAmount(rs.getInt("Amount"));
                product.setImageLink(rs.getString("ImageLink"));
                product.setIsPromoted(rs.getBoolean("IsPromoted"));
                product.setCreateAt(rs.getDate("CreateAt"));
                Category c = cdao.getCategoryByID(rs.getString("CategoryID"));
                product.setStatus(rs.getString("status"));
                product.setCategory(c);
                products.add(product);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error in getAllProducts: " + e.getMessage());
    }
    return products;
}

    public List<Products> getAllProduct(String search, String category) {
        List<Products> products = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM products WHERE 1 = 1");

            if (search != null && !search.trim().isEmpty()) {
                query.append(" AND Products LIKE ? ");
                params.add("%" + search + "%");
            }

            if (category != null && !category.trim().isEmpty()) {
                query.append(" AND CategoryID = ? ");
                params.add(category);
            }

            query.append(" ORDER BY ProductID DESC");
            PreparedStatement st = connection.prepareStatement(query.toString());
            mapParams(st, params);

            try (ResultSet rs = st.executeQuery()) {
                CategoryDAO cdao = new CategoryDAO();
                while (rs.next()) {
                    Products product = new Products();
                    product.setProductID(rs.getInt("ProductID"));
                    product.setCategoryID(rs.getString("CategoryID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setDescription(rs.getString("Description"));
                    product.setProvider(rs.getString("Provider"));
                    product.setPrice(rs.getFloat("Price"));
                    product.setOldprice(rs.getFloat("OldPrice"));
                    product.setWarrantyPeriod(rs.getString("WarrantyPeriod"));
                    product.setAmount(rs.getInt("Amount"));
                    product.setImageLink(rs.getString("ImageLink"));
                    product.setIsPromoted(rs.getBoolean("IsPromoted"));
                    product.setCreateAt(rs.getDate("CreateAt"));
                    Category c = cdao.getCategoryByID(rs.getString("CategoryID"));
                    product.setStatus(rs.getString("status"));
                    product.setCategory(c);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return products;
    }

    public boolean updateProductStatus(int productID, String newStatus) {
        String sql = "UPDATE Products SET status = ? WHERE ProductID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, productID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating product status: " + e.getMessage());
            return false;
        }
    }

    public void mapParams(PreparedStatement ps, List<Object> args) throws SQLException {
        int i = 1;
        for (Object arg : args) {
            if (arg instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                ps.setInt(i++, (Integer) arg);
            } else if (arg instanceof Long) {
                ps.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                ps.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                ps.setFloat(i++, (Float) arg);
            } else {
                ps.setString(i++, (String) arg);
            }

        }
    }

    public static void main(String[] args) {
        ProductsDAO p = new ProductsDAO();
        Products p1 = p.GetProductbyID(1);
        p1.setProductName("Quat dien aaa");
        System.out.println(p.getAllProducts());
    }

}
