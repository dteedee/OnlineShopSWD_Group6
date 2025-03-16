<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đổi Mật Khẩu</title>
        <style>
            /* Đặt các thuộc tính toàn cục */
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                margin: 0;
                padding: 0;
            }

            .modal {
                display: block;
                position: fixed;
                z-index: 1;
                padding-top: 100px;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0,0,0,0.5);
                overflow: auto;
            }

            .modal-content {
                background-color: #fff;
                margin: auto;
                padding: 30px;
                border-radius: 10px;
                width: 40%;
                max-width: 500px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                animation: fadeIn 0.5s ease;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
            }

            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }

            label {
                font-weight: bold;
                color: #333;
                display: block;
                margin-bottom: 5px;
            }

            input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
            }

            input[type="password"]:focus {
                outline: none;
                border-color: #007BFF;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
            }

            .buttons {
                text-align: center;
                margin-top: 20px;
            }

            input[type="submit"], button {
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                margin-right: 10px;
            }

            input[type="submit"] {
                background-color: #28a745;
                color: white;
            }

            input[type="submit"]:hover {
                background-color: #218838;
            }

            button {
                background-color: #dc3545;
                color: white;
            }

            button:hover {
                background-color: #c82333;
            }

            /* Phong cách cho thông báo lỗi */
            .error-message {
                color: white;
                background-color: #dc3545;
                font-weight: bold;
                padding: 10px;
                border-radius: 5px;
                text-align: center;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>
        <div class="modal">
            <div class="modal-content">
                <h2>Đổi Mật Khẩu</h2>

                <form action="ChangePasswordServlet" method="POST">
                    <!-- Hiển thị thông báo lỗi nếu có -->
                    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                    <% if (errorMessage != null) { %>
                    <div class="error-message"><%= errorMessage %></div>
                    <% } %>

                    <label for="currentPassword">Mật Khẩu Cũ:</label>
                    <input type="password" name="currentPassword" required ">

                    <label for="newPassword">Mật Khẩu Mới:</label>
                    <input type="password" name="newPassword" required ">

                    <label for="confirmPassword">Xác Nhận Mật Khẩu Mới:</label>
                    <input type="password" name="confirmPassword" required ">

                    <div class="buttons">
                        <input type="submit" value="Đổi Mật Khẩu">
                        <button type="button" onclick="window.location.href = 'UserProfile.jsp'">Thoát</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
