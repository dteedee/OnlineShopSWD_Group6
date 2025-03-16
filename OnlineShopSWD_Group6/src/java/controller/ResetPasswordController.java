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

            // Nếu email tồn tại trong cơ sở dữ liệu
            if (rs.next()) {
                emailExists = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (emailExists) {
            // Nếu email tồn tại, tiếp tục gửi OTP
            Random rand = new Random();
            otpvalue = rand.nextInt(1255650);
            

            // Thiết lập thông số email
            String to = email;
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tungduongvbhp@gmail.com", "Tungduong121004"); // Đặt email và mật khẩu của bạn
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email)); 
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Shop 4 Electrical");
                message.setText("Your OTP is: " + otpvalue);
                Transport.send(message);
                System.out.println("Tin nhắn đã được gửi thành công");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
            request.setAttribute("message", "OTP được gửi đến email của bạn");
            mySession.setAttribute("otp", otpvalue);
            mySession.setAttribute("email", email);
            mySession.setAttribute("otpGeneratedTime", java.time.Instant.now()); // Thêm thời gian OTP được tạo
            dispatcher.forward(request, response);
        } else {
            // Nếu email không tồn tại, thông báo lỗi
            request.setAttribute("message", "Email không tồn tại!");
            dispatcher = request.getRequestDispatcher("ResetPassword.jsp");
            dispatcher.forward(request, response);
        }
    }
}
