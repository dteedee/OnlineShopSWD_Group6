<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đổi mật khẩu thành công</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f4f4;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            
            .container {
                background-color: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
                text-align: center;
                max-width: 400px;
                width: 100%;
            }

            h2 {
                color: #4CAF50;
                font-size: 24px;
                margin-bottom: 20px;
            }

            p {
                font-size: 16px;
                color: #555;
                margin-bottom: 30px;
            }

            a {
                display: inline-block;
                padding: 12px 20px;
                background-color: #4CAF50;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                transition: background-color 0.3s ease;
                font-size: 16px;
            }

            a:hover {
                background-color: #45a049;
            }

            .success-icon {
                font-size: 50px;
                color: #4CAF50;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="success-icon">✔️</div>
            <h2>Đã thay đổi mật khẩu thành công!</h2>
            <p>Mật khẩu của bạn đã được cập nhật thành công. Bây giờ bạn có thể quay lại trang chủ.</p>
            <a href="${pageContext.request.contextPath}/home">Về Trang Chủ</a>
        </div>
    </body>
</html>
