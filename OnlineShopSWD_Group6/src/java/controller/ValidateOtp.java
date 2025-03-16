package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

@WebServlet("/ValidateOtp")
public class ValidateOtp extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();


        // Lấy OTP và thời gian tạo OTP từ session
        Integer otp = (Integer) session.getAttribute("otp");
        Instant otpGeneratedTime = (Instant) session.getAttribute("otpGeneratedTime");

        // Kiểm tra OTP từ người dùng
        String otpValue = request.getParameter("otp");
        RequestDispatcher dispatcher = null;

        // Kiểm tra nếu OTP hoặc thời gian tạo OTP là null
        if (otp == null || otpGeneratedTime == null) {
            request.setAttribute("message", "OTP has expired or is invalid. Please try again.");
            dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request, response);
            return; // Kết thúc xử lý nếu OTP hoặc thời gian không hợp lệ
        }

        // Kiểm tra xem OTP có hết hạn hay không
        Instant now = Instant.now();
        Duration timeElapsed = Duration.between(otpGeneratedTime, now);

        // Nếu quá 1 phút, OTP hết hạn
        if (timeElapsed.toMinutes() >= 1) {
            request.setAttribute("message", "OTP has expired. Please try again.");
            dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request, response);
        } // Nếu OTP hợp lệ trong thời gian cho phép
        else if (otpValue != null && !otpValue.isEmpty() && Integer.parseInt(otpValue) == otp) {
            request.setAttribute("email", request.getParameter("email"));
            dispatcher = request.getRequestDispatcher("NewPassword.jsp");
            dispatcher.forward(request, response);
        } // Nếu OTP không hợp lệ
        else {
            request.setAttribute("message", "Wrong OTP. Please try again.");
            dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
            dispatcher.forward(request, response);

        }

    }

}
