<%@ page import="model.Users" %>
<%@ page import="dao.UsersDAO" %>
<%@ page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String userName = (String) session.getAttribute("username");
    if (userName == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    UsersDAO usersDAO = new UsersDAO();
    Users user = usersDAO.getUserByUserName(userName);

    // Get success or error message from request attributes
    String updateSuccess = (String) request.getAttribute("updateSuccess");
    String updateError = (String) request.getAttribute("updateError");
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chỉnh Sửa Hồ Sơ</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f4f4;
            }

            .container {
                max-width: 600px;
                margin: 50px auto;
                background-color: white;
                padding: 20px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            h2 {
                text-align: center;
                color: #333;
            }

            label {
                display: block;
                margin: 10px 0 5px;
                font-size: 14px;
                color: #555;
            }

            input[type="text"],
            input[type="date"],
            select {
                width: 100%;
                padding: 8px;
                margin: 5px 0 15px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            input[type="submit"],
            .btn-back {
                padding: 10px 15px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                text-align: center;
                font-size: 16px;
            }

            .btn-back {
                background-color: #f44336;
            }

            input[type="submit"]:hover,
            .btn-back:hover {
                opacity: 0.8;
            }

            .button-container {
                display: flex;
                justify-content: space-between;
            }

            /* Toast styles */
            .toast {
                visibility: hidden;
                max-width: 250px;
                background-color: #4CAF50;
                color: white;
                text-align: center;
                border-radius: 4px;
                padding: 16px;
                position: fixed;
                z-index: 1;
                left: 20px;
                top: 20px;
                font-size: 17px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
                transition: visibility 0.5s ease-in-out, max-width 0.5s ease-in-out;
            }

            .toast.show {
                visibility: visible;
                max-width: 250px;
                transition: visibility 0.5s ease-in-out, max-width 0.5s ease-in-out;
            }

        </style>
    </head>
    <body>

        <div class="container">
            <h2>Chỉnh Sửa Hồ Sơ</h2>
            <form action="UpdateProfileServlet" method="post">
                <input type="hidden" name="userID" value="<%= user.getUserID() %>">

                <label for="userName">Tên Người Dùng:</label>
                <input type="text" id="userName" name="userName" value="<%= user.getUserName() %>" readonly><br>

                <label for="email">Email:</label>
                <input type="text" id="email" name="email" value="<%= user.getEmail() %>" readonly><br>

                <label for="firstName">Họ:</label>
                <input type="text" id="firstName" name="firstName" value="<%= user.getFirstName() %>" required><br>

                <label for="lastName">Tên:</label>
                <input type="text" id="lastName" name="lastName" value="<%= user.getLastName() %>" required><br>

                <label for="gender">Giới Tính:</label>
                <select id="gender" name="gender">
                    <option value="Male" <%= "Male".equals(user.getGender()) ? "selected" : "" %>>Nam</option>
                    <option value="Female" <%= "Female".equals(user.getGender()) ? "selected" : "" %>>Nữ</option>
                </select><br>

                <label for="dateOfBirth">Ngày Sinh:</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth" value="<%= user.getDateOfBirth() %>"><br>

                <label for="phoneNumber">Số Điện Thoại:</label>
                <input type="tel" id="phoneNumber" name="phoneNumber" value="<%= user.getPhoneNumber() %>" pattern="0[0-9]{9}" title="The phone number must have 10 digits and start with 0" required><br>
                <div id="error-phone-message" style="font-size: 10px; color: red;"></div>
                
                <label for="address">Địa Chỉ:</label>
                <input type="text" id="address" name="address" value="<%= user.getAddress() %>" required><br>

                <div class="button-container">
                    <input type="submit" value="Lưu Thay Đổi">
                    <a href="ChangePassword.jsp" class="btn-back">Đổi Mật Khẩu</a>
                    <a href="${pageContext.request.contextPath}/home" class="btn-back">Về Trang Chủ</a>

                </div>
            </form>
        </div>

        <!-- Toast notification for success -->
        <div id="toast" class="toast">
            <%= updateSuccess != null ? updateSuccess : "" %>
        </div>

        <script>
            // Show toast if there's a success message
            if ('<%= updateSuccess != null ? "true" : "false" %>' === 'true') {
                var toast = document.getElementById("toast");
                toast.className = "toast show";
                setTimeout(function () {
                    toast.className = toast.className.replace("show", "");
                }, 3000);
            }
        </script>

    </body>
</html>
