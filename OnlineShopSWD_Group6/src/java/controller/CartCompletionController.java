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

/**
 *
 * @author admin
 */
@WebServlet(name = "CartCompletionController", urlPatterns = {"/CartCompletion"})
public class CartCompletionController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        String email = null;
        HttpSession session = request.getSession();
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        // Nếu email tồn tại, tiếp tục gửi OTP
        OrdersDAO oDAO = new OrdersDAO();
        Orders o = oDAO.getLatestOrder();
        OrderDetailsDAO odDAO = new OrderDetailsDAO();
        ProductsDAO pDAO = new ProductsDAO();
        Random rand = new Random();
        Users user = (Users) session.getAttribute("user");
        email = user.getEmail();

        // Thiết lập thông số email
        String to = email;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        Session sessions = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("duongdthe181160@fpt.edu.vn", "zxobeuirttxaibge");
            }
        });

        try {
            MimeMessage message = new MimeMessage(sessions);
            message.setFrom(new InternetAddress("duongdthe181160@fpt.edu.vn")); // Sender email
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Shop 4 Electrical");
            // Tạo nội dung email
            String emailContent = "<html><body>"
                    + "<p>Kính gửi " + user.getFirstName() + " " + user.getLastName() + ",</p>"
                    + "<p>Cảm ơn bạn đã đặt hàng tại <strong>Shop 4 Electrical</strong>. Dưới đây là thông tin chi tiết về đơn hàng của bạn:</p>"
                    + "<p><strong>Mã đơn hàng:</strong> " + o.getOrderID() + "</p>"
                    + "<p><strong>Ngày đặt hàng:</strong> " + o.getOrderDate() + "</p>"
                    + "<p><strong>Tổng thanh toán:</strong> " + getPriceFormat(o.getTotalAmount()) + " VND</p>"
                    + "<p><strong>Chi tiết đơn hàng:</strong></p>"
                    + "<ul>";

            Map<Integer, OrderDetails> list = odDAO.getOrderDetailsByOrderIDasMap(o.getOrderID());
            for (int id : list.keySet()) {
                OrderDetails oi = odDAO.getOrderDetailByID(id);
                emailContent += "<li>" + pDAO.GetProductbyID(oi.getProductID()).getProductName() + " x " + oi.getQuantity() + " = " + getPriceFormat(oi.getPrice()) + " VND</li>";
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
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to send confirmation email. Please try again later.");
            return;
        }
    }

    public String getPriceFormat(double price) {
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = currencyFormat.format(price);
        return formattedPrice;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        HttpSession mySession = request.getSession();
        // Check if user is logged in
        Users user = (Users) mySession.getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        // Nếu email tồn tại, tiếp tục gửi OTP
        Orders o = (Orders) request.getAttribute("order");
        if (o == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order not found");
            return;
        }

        OrderDetailsDAO odDAO = new OrderDetailsDAO();
        ProductsDAO pDAO = new ProductsDAO();
        Random rand = new Random();
        String email = user.getEmail();

        // Thiết lập thông số email
        String to = email;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("duongdthe181160@fpt.edu.vn", "zxobeuirttxaibge");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("duongdthe181160@fpt.edu.vn")); // Sender email
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Shop 4 Electrical");
            // Nội dung email
            String emailContent = "<html><body>"
                    + "<p>Kính gửi " + user.getFirstName() + " " + user.getLastName() + ",</p>"
                    + "<p>Cảm ơn bạn đã đặt hàng tại <strong>Shop 4 Electrical</strong>. Dưới đây là thông tin chi tiết về đơn hàng của bạn:</p>"
                    + "<p><strong>Mã đơn hàng:</strong> " + o.getOrderID() + "</p>"
                    + "<p><strong>Ngày đặt hàng:</strong> " + o.getOrderDate() + "</p>"
                    + "<p><strong>Tổng thanh toán:</strong> " + getPriceFormat(o.getTotalAmount()) + " VND</p>"
                    + "<p><strong>Chi tiết đơn hàng:</strong></p>"
                    + "<ul>";
            Map<Integer, OrderDetails> list = odDAO.getOrderDetailsByOrderIDasMap(o.getOrderID());
            for (int id : list.keySet()) {
                OrderDetails oi = odDAO.getOrderDetailByID(id);
                emailContent += "<li>" + pDAO.GetProductbyID(oi.getProductID()).getProductName() + " x " + oi.getQuantity() + " = " + getPriceFormat(oi.getPrice()) + " VND</li>";
            }

            emailContent += "</ul>"
                    + "<p><strong>Địa chỉ giao hàng:</strong></p>"
                    + "<p>" + user.getAddress() + "</p>"
                    + "<p>Nếu bạn có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi qua email <strong>support@shop4electrical.com</strong> hoặc số điện thoại <strong>0123 456 789</strong>.</p>"
                    + "<p>Trân trọng,</p>"
                    + "<p><strong>Shop 4 Electrical</strong></p>"
                    + "</body></html>";

            message.setContent(emailContent, "text/html; charset=UTF-8");
            Transport.send(message);
            System.out.println("Message sent successfully");
            request.getRequestDispatcher("CartCompletion.jsp").forward(request, response);
        } catch (MessagingException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to send confirmation email. Please try again later.");
            return;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles cart completion and sends order confirmation email";
    }
}