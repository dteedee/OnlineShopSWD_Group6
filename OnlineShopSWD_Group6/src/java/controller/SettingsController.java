package controller;

import dao.*;
import model.*;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "SettingsController", urlPatterns = {"/admin/settings"})
public class SettingsController extends HttpServlet {

    private final UsersDAO userDAO = new UsersDAO();
    private final SettingsDAO settingsDAO = new SettingsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Giả sử bạn có cơ chế check login + quyền Admin
        HttpSession session = request.getSession();
        String emailSession = (String) session.getAttribute("email");
        Users user = userDAO.getUserByEmail(emailSession);
        if (user != null) {
            if (user.getRole().equalsIgnoreCase("Admin")) {

                // Lấy tham số lọc
                String type = request.getParameter("type");
                String status = request.getParameter("status");
                String searchValue = request.getParameter("searchValue");

                // Lấy danh sách settings theo lọc
                List<Settings> listSettings = settingsDAO.getAllSettings(type, status, searchValue);

                // Đưa vào request attribute
                request.setAttribute("listSettings", listSettings);
                // Đưa lại type, status, searchValue cho form filter
                request.setAttribute("type", type);
                request.setAttribute("status", status);
                request.setAttribute("searchValue", searchValue);

                // Forward sang trang JSP
                request.getRequestDispatcher("settings-list.jsp").forward(request, response);
            } else {
                session.setAttribute("notificationErr", "Bạn không có quyền truy cập vào trang này");
                response.sendRedirect("../Login.jsp");
            }
        } else {
            session.setAttribute("notificationErr", "Bạn cần đăng nhập trước!");
            response.sendRedirect("../Login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        // Kiểm tra quyền Admin (nếu cần)...

        // Lấy action
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Thêm mới Setting
            String settingType = request.getParameter("settingType");
            String settingValue = request.getParameter("settingValue");
            String status = request.getParameter("status");
            String idType = request.getParameter("idType");
            String priority = request.getParameter("priority");

            Settings s = new Settings();
            s.setSettingType(settingType);
            s.setSettingValue(settingValue);
            s.setStatus(status);
            s.setIdType(idType);
            s.setPriority(Integer.parseInt(priority));
            boolean added = settingsDAO.addSettings(s);
            if (added) {
                session.setAttribute("notification", "Thêm Setting mới thành công!");
            } else {
                session.setAttribute("notificationErr", "Thêm Setting mới thất bại!");
            }
            response.sendRedirect(request.getContextPath() + "/admin/settings");
            return;
        }

        if ("update".equals(action)) {
            // Cập nhật Setting
            try {
                int id = Integer.parseInt(request.getParameter("settingID"));
                String settingType = request.getParameter("settingType");
                String settingValue = request.getParameter("settingValue");
                String status = request.getParameter("status");
                String idType = request.getParameter("idType");
                String priority = request.getParameter("priority");

                Settings s = new Settings(id, settingType, settingValue, status);
                s.setIdType(idType);
                s.setPriority(Integer.parseInt(priority));
                boolean updated = settingsDAO.updateSettings(s);
                if (updated) {
                    session.setAttribute("notification", "Cập nhật Setting thành công!");
                } else {
                    session.setAttribute("notificationErr", "Cập nhật Setting thất bại!");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("notificationErr", "ID không hợp lệ!");
            }
            response.sendRedirect(request.getContextPath() + "/admin/settings");
            return;
        }

        if ("toggle".equals(action)) {
            // Chuyển trạng thái (Active <-> Inactive)
            try {
                int id = Integer.parseInt(request.getParameter("settingID"));
                boolean toggled = settingsDAO.toggleStatus(id);
                if (toggled) {
                    session.setAttribute("notification", "Chuyển trạng thái thành công!");
                } else {
                    session.setAttribute("notificationErr", "Chuyển trạng thái thất bại!");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("notificationErr", "ID không hợp lệ!");
            }
            response.sendRedirect(request.getContextPath() + "/admin/settings");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/settings");
    }
}
