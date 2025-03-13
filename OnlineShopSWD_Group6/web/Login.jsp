<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Đăng Nhập</title>

        <!-- Font Icon -->
        <link rel="stylesheet"
              href="fonts/material-icon/css/material-design-iconic-font.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Main css -->
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="main">

            <!-- Sing in  Form -->
            <section class="sign-in">
                <div class="container">
                    <div class="signin-content">
                        <div class="signin-image">
                            <figure>
                                <a href="${pageContext.request.contextPath}/home"> 
                                    <img src="assets/img/S4EWhite.png" class="header__shop-logo-img" alt="Logo linh ki?n ?i?n t?" />
                                </a>
                            </figure>
                            <a href="Register.jsp" class="signup-image-link">Tạo Tài Khoản</a>
                        </div>

                        <div class="signin-form">
                            <c:if test="${not empty sessionScope.notification}">
                                <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                                    ${sessionScope.notification}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                                <%
                                    session.removeAttribute("notification");
                                %>
                            </c:if>
                            <c:if test="${not empty sessionScope.notificationErr}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                                    ${sessionScope.notificationErr}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                                <%
                                    session.removeAttribute("notificationErr");
                                %>
                            </c:if>
                            <c:if test="${not empty requestScope.loginError}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                                    ${requestScope.loginError}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                            <h2 class="form-title">Đăng Nhập</h2>
                            <form method="post" action="LoginServlet" class="register-form"
                                  id="login-form">
                                <div class="form-group">
                                    <label for="username"><i
                                            class="zmdi zmdi-account material-icons-name"></i></label> <input
                                        type="text" name="username" id="username"
                                        placeholder="Email Của Bạn" />
                                </div>
                                <div class="form-group">
                                    <label for="password"><i class="zmdi zmdi-lock"></i></label> <input
                                        type="password" name="password" id="password"
                                        placeholder="Mật Khẩu" />
                                </div>
                                <div class="form-group">
                                    <a href="ResetPassword.jsp"> Quên Mật Khẩu?</a>
                                </div>
                                <div class="form-group">
                                    <input type="checkbox" name="remember-me" id="remember-me"
                                           class="agree-term" /> <label for="remember-me"
                                           class="label-agree-term"><span><span></span></span>Nhớ Mật Khẩu</label>
                                </div>
                                <div class="form-group form-button">
                                    <input type="submit" name="signin" id="signin"
                                           class="form-submit" value="Đăng Nhập" />
                                </div>
                            </form>

                            <% if ("true".equals(request.getParameter("error"))) { %>
                            <p style="color: red;">Đăng nhập thất bại. Vui lòng thử lại sau.</p>
                            <% } %>


                            <div class="social-login">
                                <span class="social-label">Hoặc Đăng Nhập Với</span>
                                <ul class="socials">
                                    <li><a href="#"><i
                                                class="display-flex-center zmdi zmdi-facebook"></i></a></li>
                                    <li><a href="#"><i
                                                class="display-flex-center zmdi zmdi-twitter"></i></a></li>
                                    <li><a href="#"><i
                                                class="display-flex-center zmdi zmdi-google"></i></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>


        <!-- JS -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="js/main.js"></script>
        <script src ="http://inpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link rel ="stylesheet" href="alert/dist/sweetalert.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript">
            var status = document.getElementById("status").value;
            if (status == "failed") {
            swal("Xin Lỗi", "Sai Email Hoặc Mật Khẩu", "error");
            }
            <style>
        /* CSS cho thông báo lỗi */
        .alert-danger {
                    background - color: #ffebee; /* Màu nền nhạt */
            border - color: #ffcdd2; /* Màu viền */
            color: #c62828; /* Màu chữ */
            padding: 15px; /* Khoảng cách bên trong */
            border - radius: 4px; /* Bo góc */
            margin - bottom: 20px; /* Khoảng cách phía dưới */
            text - align: center; /* Căn giữa chữ */
            font - size: 14px; /* Cỡ chữ */
                    }
                    
                        .alert-danger .btn-close {
                    position: absolute; /* Đặt nút đóng ở góc phải */
            right: 10px;
            top: 50 % ;
            transform: translateY( - 50 % );
                                    }
                                    
                                    .alert-danger strong {
                    font - weight: bold; /* In đậm tiêu đề */
                            }
        </style>
        </script>
    </body>

</html>