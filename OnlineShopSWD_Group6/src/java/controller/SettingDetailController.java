package controller;

import dao.SettingsDAO;
import model.Settings;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "SettingDetailController", urlPatterns = {"/admin/setting-detail"})
public class SettingDetailController extends HttpServlet {

    private SettingsDAO settingsDAO = new SettingsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Giả sử bạn có kiểm tra đăng nhập + quyền Admin tại đây
        HttpSession session = request.getSession();
        // ... kiểm tra user ...

        // Lấy tham số id
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            // Nếu không có id, quay lại trang danh sách
            session.setAttribute("notificationErr", "Không tìm thấy ID cài đặt.");
            response.sendRedirect(request.getContextPath() + "/admin/settings");
            return;
        }

        try {
            int settingID = Integer.parseInt(idParam);
            // Lấy Setting từ DB
            Settings setting = settingsDAO.getSettingByID(settingID);
            if (setting == null) {
                session.setAttribute("notificationErr", "Cài đặt không tồn tại.");
                response.sendRedirect(request.getContextPath() + "/admin/settings");
                return;
            }

            // Đưa Setting vào request attribute
            request.setAttribute("setting", setting);

            // Forward sang trang JSP
            request.getRequestDispatcher("setting-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            session.setAttribute("notificationErr", "ID không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/admin/settings");
        }
    }
}
