package dao;

import model.Settings;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SettingsDAO extends DBContext {

    public Map<Integer, Settings> getAllSettings() {
        Map<Integer, Settings> settingsList = new HashMap<>();
        String sql = "SELECT * FROM Settings";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Settings setting = extractSettingFromResultSet(rs);
                settingsList.put(setting.getSettingID(), setting);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching settings: " + e.getMessage());
        }
        return settingsList;
    }

    public Vector<Settings> getAllSettingsAsVector() {
        Vector<Settings> settings = new Vector<>();
        String sql = "SELECT * FROM Settings";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                settings.add(extractSettingFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching settings as vector: " + e.getMessage());
        }
        return settings;
    }

    public Settings getSettingByID(int id) {
        String sql = "SELECT * FROM Settings WHERE SettingID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractSettingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching setting by ID: " + e.getMessage());
        }
        return null;
    }

    public boolean addSetting(Settings setting) {
        String sql = "INSERT INTO Settings (SettingType, SettingValue, Status) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setSettingPreparedStatement(ps, setting);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding setting: " + e.getMessage());
            return false;
        }
    }

    public boolean updateSetting(Settings setting) {
        String sql = "UPDATE Settings SET SettingType = ?, SettingValue = ?, Status = ? WHERE SettingID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setSettingPreparedStatement(ps, setting);
            ps.setInt(4, setting.getSettingID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating setting: " + e.getMessage());
            return false;
        }
    }

    public boolean removeSetting(int id) {
        String sql = "DELETE FROM Settings WHERE SettingID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing setting: " + e.getMessage());
            return false;
        }
    }

    private Settings extractSettingFromResultSet(ResultSet rs) throws SQLException {
        Settings setting = new Settings();
        setting.setSettingID(rs.getInt("SettingID"));
        setting.setSettingType(rs.getString("SettingType"));
        setting.setSettingValue(rs.getString("SettingValue"));
        setting.setStatus(rs.getString("Status"));
        setting.setIdType(rs.getString("id_type"));
        setting.setPriority(rs.getInt("priority"));

        return setting;
    }

    private void setSettingPreparedStatement(PreparedStatement ps, Settings setting) throws SQLException {
        ps.setString(1, setting.getSettingType());
        ps.setString(2, setting.getSettingValue());
        ps.setString(3, setting.getStatus());
    }

    /**
     * Lấy danh sách Settings có thể lọc theo type, status, và tìm kiếm (search)
     * theo SettingValue.
     */
    public List<Settings> getAllSettings(String type, String status, String searchValue) {
        List<Settings> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Settings WHERE 1=1");

        // Danh sách tham số cho PreparedStatement
        List<Object> params = new ArrayList<>();

        // Lọc theo type
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND SettingType = ?");
            params.add(type);
        }

        // Lọc theo status
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND Status = ?");
            params.add(status);
        }

        // Tìm kiếm trong SettingValue
        if (searchValue != null && !searchValue.trim().isEmpty()) {
            sql.append(" AND SettingValue LIKE ?");
            params.add("%" + searchValue + "%");
        }

        // Sắp xếp (ví dụ sắp xếp theo SettingID giảm dần)
        sql.append(" ORDER BY SettingID DESC");

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            // Gắn tham số
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Settings s = new Settings();
                s.setSettingID(rs.getInt("SettingID"));
                s.setSettingType(rs.getString("SettingType"));
                s.setSettingValue(rs.getString("SettingValue"));
                s.setStatus(rs.getString("Status"));
                s.setIdType(rs.getString("id_type"));
                s.setPriority(rs.getInt("priority"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy 1 Setting theo ID
     */
    public boolean addSettings(Settings s) {
        String sql = "INSERT INTO Settings (SettingType, SettingValue, Status, id_type, priority) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, s.getSettingType());
            st.setString(2, s.getSettingValue());
            st.setString(3, s.getStatus());
            st.setString(4, s.getIdType());
            st.setInt(5, s.getPriority());
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật Setting
     */
    public boolean updateSettings(Settings s) {
        String sql = "UPDATE Settings SET SettingType = ?, SettingValue = ?, Status = ? , id_type = ? , priority = ? "
                + "WHERE SettingID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, s.getSettingType());
            st.setString(2, s.getSettingValue());
            st.setString(3, s.getStatus());
            st.setString(4, s.getIdType());
            st.setInt(5, s.getPriority());
            st.setInt(6, s.getSettingID());
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xoá Setting (tuỳ chọn) hoặc chuyển trạng thái
     */
    public boolean deleteSetting(int id) {
        String sql = "DELETE FROM Settings WHERE SettingID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Chuyển trạng thái Setting (VD: Activate <-> Deactivate)
     */
    public boolean toggleStatus(int id) {
        // Lấy setting hiện tại
        Settings s = getSettingByID(id);
        if (s == null) {
            return false;
        }

        String newStatus = s.getStatus().equalsIgnoreCase("Active") ? "Inactive" : "Active";

        String sql = "UPDATE Settings SET Status = ? WHERE SettingID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, newStatus);
            st.setInt(2, id);
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
