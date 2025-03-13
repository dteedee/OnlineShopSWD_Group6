package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/ChangePasswordServlet"})
public class ChangePasswordServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/EcommerceDB";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1234";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy các tham số từ form
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");  // Email đã lưu trong session

        if (email == null) {
            response.sendRedirect("Login.jsp");  // Nếu không có session email, chuyển về trang đăng nhập
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận có khớp không
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu mới và mật khẩu xác nhận không khớp.");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra mật khẩu mới có chứa khoảng trắng không
        if (newPassword.contains(" ")) {
            request.setAttribute("errorMessage", "Mật khẩu mới không được chứa khoảng trắng.");
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
            return;
        }

        // Kết nối tới cơ sở dữ liệu
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EcommerceDB", "root", "1234")) {
            // Kiểm tra mật khẩu hiện tại có đúng không
            String checkPasswordQuery = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkPasswordQuery)) {
                stmt.setString(1, email);
                stmt.setString(2, currentPassword);  // Bạn nên mã hóa mật khẩu trước khi so sánh

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Nếu mật khẩu hiện tại đúng, tiến hành cập nhật mật khẩu mới
                    String updatePasswordQuery = "UPDATE Users SET Password = ? WHERE Email = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updatePasswordQuery)) {
                        updateStmt.setString(1, newPassword);  // Nên mã hóa mật khẩu trước khi lưu
                        updateStmt.setString(2, email);
                        int rowsAffected = updateStmt.executeUpdate();

                        if (rowsAffected > 0) {
                            // Đổi mật khẩu thành công, chuyển hướng tới trang thành công
                            response.sendRedirect("changePasswordSuccess.jsp");
                        } else {
                            // Không cập nhật được mật khẩu
                            request.setAttribute("errorMessage", "Thay đổi mật khẩu không thành công.");
                            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
                        }
                    }
                } else {
                    // Mật khẩu hiện tại không đúng
                    request.setAttribute("errorMessage", "Mật khẩu hiện tại không đúng.");
                    request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("ChangePassword.jsp").forward(request, response);
        }
    }
}
