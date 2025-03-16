<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>

        <link
            href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
            rel="stylesheet" id="bootstrap-css">
        <script
        src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <!------ Include the above in your HEAD tag ---------->

        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

        <style type="text/css">
            .form-gap {
                padding-top: 70px;
            }
        </style>
    </head>

    <body>
        <div class="form-gap"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="text-center">
                                <h3>
                                    <i class="fa fa-lock fa-4x"></i>
                                </h3>
                                <h2 class="text-center">Nhập OTP</h2>
                                <%
if(request.getAttribute("message")!=null)
{
        out.print("<p class='text-danger ml-1'>"+request.getAttribute("message")+"</p>");
}
		  
                                %>

                                <div class="panel-body">

                                    <form id="register-form" action="ValidateOtp" role="form" autocomplete="off"
                                          class="form" method="post">

                                        <div class="form-group">
                                            <div class="input-group">
                                                <span class="input-group-addon"><i
                                                        class="glyphicon glyphicon-envelope color-blue"></i></span> <input
                                                    id="opt" name="otp" placeholder="Nhập OTP"
                                                    class="form-control" type="text" required="required">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <input name="recover-submit"
                                                   class="btn btn-lg btn-primary btn-block"
                                                   value="Đặt lại mật khẩu" type="submit">
                                        </div>
                                </div>
                                <div id="countdown" style="font-weight:bold; color: red; margin-top: 20px;"></div>
                                <p>OTP sẽ hết hạn sau 60 giây.</p>
                                <script>
                                    // Hàm khởi tạo bộ đếm ngược 60 giây
                                    function startCountdown() {
                                        var timeLeft = 60; // Số giây đếm ngược
                                        var countdownElement = document.getElementById("countdown");

                                        // Cập nhật mỗi giây
                                        var countdownTimer = setInterval(function () {
                                            if (timeLeft <= 0) {
                                                clearInterval(countdownTimer);
                                                countdownElement.innerHTML = "OTP đã hết hạn. Đang chuyển hướng đến Đăng nhập...";
                                                setTimeout(function () {
                                                    // Chuyển hướng sau khi OTP hết hạn
                                                    window.location.href = "Login.jsp";
                                                }, 2000); // Chờ 2 giây trước khi chuyển hướng
                                            } else {
                                                countdownElement.innerHTML = "Thời gian còn: " + timeLeft + " giây";
                                            }
                                            timeLeft -= 1;
                                        }, 1000);
                                    }

                                    // Gọi hàm khi trang tải xong
                                    window.onload = function () {
                                        startCountdown();
                                    };
                                </script>
                                <input type="hidden" class="hide" name="token" id="token"
                                       value="">
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>