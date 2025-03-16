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
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(name = "ResetPasswordController", urlPatterns = {"/ResetPassword"})
public class ResetPasswordController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        RequestDispatcher dispatcher = null;
        int otpvalue = 0;
        HttpSession mySession = request.getSession();

        // Kiểm tra xem email có tồn tại không
        boolean emailExists = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/OSSDB", "root", "1234");
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM Users WHERE Email = ?");
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                emailExists = true;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi kết nối cơ sở dữ liệu!");
            dispatcher = request.getRequestDispatcher("ResetPassword.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (emailExists) {
            Random rand = new Random();
            otpvalue = rand.nextInt(900000) + 100000; // Tạo OTP 6 chữ số

            String to = email;
            String from = "tungduongjob@gmail.com";
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.localhost", "localhost"); // Ghi đè hostname thành "localhost"
            props.put("mail.debug", "true"); // Bật debug chi tiết

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tungduongjob@gmail.com", "wivb rycz hnot cvxo"); 
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Shop 4 Electrical - Password Reset OTP");
                message.setText("Mã OTP để đặt lại mật khẩu của bạn là: " + otpvalue);
                Transport.send(message);
                System.out.println("Đã gửi OTP thành công đến: " + to);

                dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
                request.setAttribute("message", "OTP đã được gửi đến email của bạn!");
                mySession.setAttribute("otp", otpvalue);
                mySession.setAttribute("email", email);
                mySession.setAttribute("otpGeneratedTime", java.time.Instant.now());
                dispatcher.forward(request, response);

            } catch (MessagingException e) {
                e.printStackTrace();
                String errorMessage = "Lỗi khi gửi email OTP. Chi tiết: " + e.getMessage();
                System.err.println(errorMessage);
                request.setAttribute("message", errorMessage);
                dispatcher = request.getRequestDispatcher("ResetPassword.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("message", "Email không tồn tại!");
            dispatcher = request.getRequestDispatcher("ResetPassword.jsp");
            dispatcher.forward(request, response);
        }
    }
}