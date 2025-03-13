package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/NewPassword")
public class NewPassword extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String newPassword = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");
        RequestDispatcher dispatcher = null;

        // Kiểm tra xem mật khẩu mới và mật khẩu xác nhận có khớp nhau không
        if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {

            try {
                // Kết nối đến cơ sở dữ liệu
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EcommerceDB", "root", "1234");

                // Thực hiện câu truy vấn cập nhật mật khẩu cho người dùng dựa trên email
                PreparedStatement pst = conn.prepareStatement("UPDATE Users SET Password = ? WHERE Email = ?");
                pst.setString(1, newPassword);
                pst.setString(2, (String) session.getAttribute("email"));

                // Kiểm tra xem có hàng nào được cập nhật không
                int rowCount = pst.executeUpdate();
                if (rowCount > 0) {
                    // Gửi thông báo thành công qua session
                    session.setAttribute("status", "Đã đặt lại mật khẩu thành công!");
                    response.sendRedirect("Login.jsp");  // Chuyển hướng về trang Login.jsp
                } else {
                    request.setAttribute("status", "Đặt lại mật khẩu không thành công!");
                    dispatcher = request.getRequestDispatcher("Login.jsp");
                    dispatcher.forward(request, response);
                }

                // Chuyển hướng về trang đăng nhập
                dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Nếu mật khẩu mới và mật khẩu xác nhận không khớp
            request.setAttribute("status", "Mật khẩu không khớp!");
            dispatcher = request.getRequestDispatcher("NewPassword.jsp");
            dispatcher.forward(request, response);
        }
    }
}
