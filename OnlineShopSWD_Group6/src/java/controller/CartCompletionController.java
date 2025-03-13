package controller;

import dao.*;
import model.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(name = "CartCompletionController", urlPatterns = {"/CartCompletion"})
public class CartCompletionController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CartCompletionController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartCompletionController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        String email = null;
        HttpSession session = request.getSession();
        // Nếu email tồn tại, tiếp tục gửi OTP
        OrdersDAO oDAO = new OrdersDAO();
        Orders o = oDAO.getLatestOrder();
        OrderDetailsDAO odDAO = new OrderDetailsDAO();
        ProductsDAO pDAO = new ProductsDAO();
        Random rand = new Random();
        Users user = new Users();
        
        if(session.getAttribute("user")!=null){
            user = (Users) session.getAttribute("user");
            email = user.getEmail();
        }
        // Thiết lập thông số email
        String to = email;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session sessions = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("luuthequangbkvip@gmail.com", "oouvorqzkguxbmez"); // Đặt email và mật khẩu của bạn
            }
        });

        try {
            MimeMessage message = new MimeMessage(sessions);
            message.setFrom(new InternetAddress(email)); // Đặt lại email gửi
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Shop 4 Electrical");
            // Tạo nội dung email
            String emailContent = "<html><body>"
                    + "<p>Kính gửi " + user.getFirstName()+ " " + user.getLastName()+ ",</p>"
                    + "<p>Cảm ơn bạn đã đặt hàng tại <strong>Shop 4 Electrical</strong>. Dưới đây là thông tin chi tiết về đơn hàng của bạn:</p>"
                    + "<p><strong>Mã đơn hàng:</strong> " + o.getOrderID()+ "</p>"
                    + "<p><strong>Ngày đặt hàng:</strong> " + o.getOrderDate() + "</p>"
                    + "<p><strong>Tổng thanh toán:</strong> " + getPriceFormat(o.getTotalAmount()) + " VND</p>"
                    + "<p><strong>Chi tiết đơn hàng:</strong></p>"
                    + "<ul>";

            Map<Integer, OrderDetails> list = odDAO.getOrderDetailsByOrderIDasMap(o.getOrderID());
                for (int id : list.keySet()) {
                    OrderDetails oi = odDAO.getOrderDetailByID(id);
                    emailContent += "- " + pDAO.GetProductbyID(oi.getProductID()).getProductName() + " x " + oi.getQuantity() + " = " + getPriceFormat(oi.getPrice()) + " VND\n";
                }

            emailContent += "</ul>"
                    + "<p><strong>Địa chỉ giao hàng:</strong></p>"
                    + "<p>" + user.getAddress() + "</p>"
                    + "<p>Nếu bạn có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi qua email <strong>support@shop4electrical.com</strong> hoặc số điện thoại <strong>0123 456 789</strong>.</p>"
                    + "<p>Trân trọng,</p>"
                    + "<p><strong>Shop 4 Electrical</strong></p>"
                    + "</body></html>";

            // Thiết lập nội dung email với mã hóa UTF-8
            message.setContent(emailContent, "text/html; charset=UTF-8");
            Transport.send(message);
            System.out.println("Message sent successfully");
            request.getRequestDispatcher("CartCompletion.jsp").forward(request, response);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPriceFormat(double price) {
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(price);
        return formattedPrice;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        HttpSession mySession = request.getSession();
        // Nếu email tồn tại, tiếp tục gửi OTP
        Orders o = (Orders) request.getAttribute("order");
        OrderDetailsDAO odDAO = new OrderDetailsDAO();
        ProductsDAO pDAO = new ProductsDAO();
        Random rand = new Random();
        Users user = (Users) mySession.getAttribute("user");
        String email = user.getEmail();
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
                return new PasswordAuthentication("luuthequangbkvip@gmail.com", "oouvorqzkguxbmez"); // Đặt email và mật khẩu của bạn
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email)); // Đặt lại email gửi
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Shop 4 Electrical");
            // Nội dung email
            String emailContent = "Kính gửi " + user.getFirstName() + user.getLastName() + ",\n\n"
                    + "Cảm ơn bạn đã đặt hàng tại Shop 4 Electrical. Dưới đây là thông tin chi tiết về đơn hàng của bạn:\n\n"
                    + "Mã đơn hàng: " + o.getOrderID() + "\n"
                    + "Ngày đặt hàng: " + o.getOrderDate() + "\n"
                    + "Tổng thanh toán: " + o.getTotalAmount() + " VND\n\n"
                    + "Chi tiết đơn hàng:\n";
            Map<Integer, OrderDetails> list = odDAO.getOrderDetailsByOrderIDasMap(o.getOrderID());
            for (int id : list.keySet()) {
                OrderDetails oi = odDAO.getOrderDetailByID(id);
                emailContent += "- " + pDAO.GetProductbyID(oi.getProductID()).getProductName() + " x " + oi.getQuantity() + " = " + oi.getPrice() + " VND\n";
            }

            emailContent += "\nĐịa chỉ giao hàng:\n"
                    + user.getAddress() + "\n\n"
                    + "Nếu bạn có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi qua email support@shop4electrical.com hoặc số điện thoại 0123 456 789.\n\n"
                    + "Trân trọng,\n"
                    + "Shop 4 Electrical";

            message.setText(emailContent);
            Transport.send(message);
            System.out.println("Message sent successfully");
            request.getRequestDispatcher("CartCompletion.jsp").forward(request, response);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
