package controller;

import dao.UsersDAO;
import model.Users;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "UserDetailController", urlPatterns = {"/admin/user-detail"})
public class UserDetailController extends HttpServlet {

    UsersDAO userDAO = new UsersDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String emailSession = (String) session.getAttribute("email");
        
        // Ensure the user is logged in and is an Admin.
        Users currentUser = userDAO.getUserByEmail(emailSession);
        if (currentUser == null || !currentUser.getRole().equalsIgnoreCase("Admin")) {
            session.setAttribute("notificationErr", "Access Denied! You must be an Admin.");
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        
        // Retrieve the user id from the query parameter.
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            session.setAttribute("notificationErr", "User ID is missing.");
            response.sendRedirect(request.getContextPath() + "/admin/user-management");
            return;
        }
        
        try {
            int userId = Integer.parseInt(idStr);
            Users user = userDAO.getUserByID(userId);
            if (user == null) {
                session.setAttribute("notificationErr", "User not found.");
                response.sendRedirect(request.getContextPath() + "/admin/user-management");
                return;
            }
            // Set the user object as a request attribute and forward to the detail page.
            request.setAttribute("user", user);
            request.getRequestDispatcher("user-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            session.setAttribute("notificationErr", "Invalid User ID provided.");
            response.sendRedirect(request.getContextPath() + "/admin/user-management");
        }
    }
}
