package controller;
import dao.UsersDAO;
import model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ form
        String userName = request.getParameter("userName");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String gender = request.getParameter("gender");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        int userID = Integer.parseInt(request.getParameter("userID"));

        // Khởi tạo DAO và lấy thông tin người dùng từ database
        UsersDAO usersDAO = new UsersDAO();
        Users currentUser = usersDAO.getUserByID(userID);

        // Cập nhật các trường không liên quan đến mật khẩu và email
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setGender(gender);
        currentUser.setDateOfBirth(dateOfBirth);
        currentUser.setPhoneNumber(phoneNumber);
        currentUser.setAddress(address);

        // Email và username không thay đổi
        currentUser.setUserName(userName);
        currentUser.setEmail(currentUser.getEmail());

        // Thực hiện cập nhật thông tin người dùng
        boolean isUpdated = usersDAO.updateUser(currentUser);

        // Xử lý kết quả cập nhật
        if (isUpdated) {
            request.setAttribute("updateSuccess", "Hồ sơ được cập nhật thành công!");
        } else {
            request.setAttribute("updateError", "Lỗi khi cập nhật hồ sơ.");
        }
        request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
    }
}
