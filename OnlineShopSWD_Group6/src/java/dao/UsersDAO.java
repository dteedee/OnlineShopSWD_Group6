package dao;

import model.Users;
import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class UsersDAO extends DBContext {

    public Map<Integer, Users> getAllUsers() {
        Map<Integer, Users> userList = new HashMap<>();
        String sql = "SELECT * FROM Users";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Users user = extractUserFromResultSet(rs);
                userList.put(user.getUserID(), user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all users: " + e.getMessage());
        }
        return userList;
    }
    

    public Users getUserByID(int id) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by ID: " + e.getMessage());
        }
        return null;
    }
    
    public Users getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by Email: " + e.getMessage());
        }
        return null;
    }

    public boolean isEmailExists(String email) {
        String query = "SELECT * FROM Users WHERE Email = ?";
        try (java.sql.Connection con = makeConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // Trả về true nếu email đã tồn tại
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUsernameExists(String username) {
        String query = "SELECT * FROM Users WHERE UserName = ?";
        try (java.sql.Connection con = makeConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // Trả về true nếu username đã tồn tại
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isPhoneNumberExists(String phoneNumber) {
        String query = "SELECT * FROM Users WHERE PhoneNumber = ?";
        try (java.sql.Connection con = makeConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, phoneNumber);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // Trả về true nếu số điện thoại đã tồn tại
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserRole(int userID, String role) {
        String sql = "UPDATE Users SET Role = ? WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, role);
            ps.setInt(2, userID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(int userID, String status) {
        String sql = "UPDATE Users SET status = ? WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, userID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addUser(Users user) {
        String sql = "INSERT INTO Users (FirstName, LastName, Gender, DateOfBirth, UserName, Password, Role, Email, PhoneNumber, Address, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setUserPreparedStatement(ps, user);
            ps.setString(11, user.getStatus()); // Thêm Status
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(Users user) {
        String sql = "UPDATE Users SET FirstName = ?, LastName = ?, Gender = ?, DateOfBirth = ?, UserName = ?, Password = ?, Role = ?, Email = ?, PhoneNumber = ?, Address = ? WHERE UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setUserPreparedStatement(ps, user);
            ps.setInt(11, user.getUserID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin người dùng chỉ cho phép sửa username, firstname, lastname, gender, DateOfBirth, PhoneNumber, Address
    public boolean updateUserProfile(Users user) {
        String sql = "UPDATE Users SET UserName = ?, FirstName = ?, LastName = ?, Gender = ?, DateOfBirth = ?, PhoneNumber = ?, Address = ? WHERE UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getGender());
            ps.setString(5, user.getDateOfBirth());
            ps.setString(6, user.getPhoneNumber());
            ps.setString(7, user.getAddress());
            ps.setInt(8, user.getUserID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating user profile: " + e.getMessage());
            return false;
        }
    }

    public boolean removeUser(int id) {
        String sql = "DELETE FROM Users WHERE UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing user: " + e.getMessage());
            return false;
        }
    }

    private void setUserPreparedStatement(PreparedStatement ps, Users user) throws SQLException {
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getGender());
        ps.setString(4, user.getDateOfBirth());
        ps.setString(5, user.getUserName());
        ps.setString(6, user.getPassword());
        ps.setString(7, user.getRole());
        ps.setString(8, user.getEmail());
        ps.setString(9, user.getPhoneNumber());
        ps.setString(10, user.getAddress());
    }

    private Users extractUserFromResultSet(ResultSet rs) throws SQLException {
        Users user = new Users();
        user.setUserID(rs.getInt("UserID"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setGender(rs.getString("Gender"));
        user.setDateOfBirth(rs.getString("DateOfBirth"));
        user.setUserName(rs.getString("UserName"));
        user.setPassword(rs.getString("Password"));
        user.setRole(rs.getString("Role"));
        user.setEmail(rs.getString("Email"));
        user.setPhoneNumber(rs.getString("PhoneNumber"));
        user.setAddress(rs.getString("Address"));
        user.setStatus(rs.getString("Status"));
        return user;
    }

    public List<Users> getAllUser(String role, String gender, String status) {
        List<Users> listUsers = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder("SELECT * FROM Users WHERE 1 = 1");

            if (role != null && !role.trim().isEmpty()) {
                query.append(" AND Role = ? ");
                params.add(role);
            }
            if (status != null && !status.trim().isEmpty()) {
                query.append(" AND Status = ? "); // Sửa từ Role thành Status
                params.add(status);
            }
            if (gender != null && !gender.trim().isEmpty()) {
                query.append(" AND Gender = ? ");
                params.add(gender);
            }

            query.append(" ORDER BY UserID DESC");
            PreparedStatement st = connection.prepareStatement(query.toString());
            mapParams(st, params);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Users u = new Users();
                    u.setUserID(rs.getInt("UserID"));
                    u.setFirstName(rs.getString("FirstName"));
                    u.setLastName(rs.getString("LastName"));
                    u.setGender(rs.getString("Gender"));
                    u.setDateOfBirth(rs.getString("DateOfBirth"));
                    u.setUserName(rs.getString("UserName"));
                    u.setPassword(rs.getString("Password"));
                    u.setRole(rs.getString("Role"));
                    u.setEmail(rs.getString("Email"));
                    u.setPhoneNumber(rs.getString("PhoneNumber"));
                    u.setAddress(rs.getString("Address"));
                    u.setStatus(rs.getString("Status"));
                    listUsers.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return listUsers;
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

    // Lấy người dùng theo tên đăng nhập (UserName)
    public Users getUserByUserName(String userName) {
        String sql = "SELECT * FROM Users WHERE UserName = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by UserName: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        UsersDAO dao = new UsersDAO();

        Users u = new Users();
        System.out.println(dao.getAllUsers());
    }
}
