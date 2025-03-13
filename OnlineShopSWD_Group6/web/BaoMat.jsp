<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Chính sách bảo mật</title>
    <link rel="shortcut icon" href="assets/img/S4EWhite.png" type="image/x-icon" />
    <link rel="stylesheet" href="assets/css/reset.css" />
    <link rel="stylesheet" href="assets/css/base.css" />
    <link rel="stylesheet" href="assets/css/main_PC.css" />
    <link rel="stylesheet" href="assets/css/main_Tablet.css" />
    <link rel="stylesheet" href="assets/css/TaiKhoan/GioiThieu.css" />
    <link rel="stylesheet" href="assets/fonts/fontawesome-free-6.0.0-web/css/all.css" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap&subset=vietnamese" />

    <style>
        .mainGT {
            text-align: justify;
            font-size: 16px;
            color: #444;
            max-width: 90%;
            margin: 0 auto;
            line-height: 1.6;
        }

        .mainGT__heading {
            text-align: center;
            font-size: 32px;
            font-weight: bold;
            margin: 30px 0;
            color: #333;
            text-transform: uppercase;
        }

        .mainGT ol {
            list-style: none;
            padding-left: 0;
        }

        .mainGT ol > li {
            font-weight: bold;
            font-size: 18px;
            margin-top: 25px;
        }

        .mainGT ul {
            list-style: disc;
            padding-left: 20px;
            margin-top: 10px;
        }

        .mainGT ul ul {
            list-style: circle;
            padding-left: 20px;
        }

        .mainGT ul li {
            margin: 8px 0;
        }
    </style>
</head>
<body>

    <%@ include file="./Public/header.jsp" %>

    <main class="main gird">
        <div class="mainGT">
            <h1 class="mainGT__heading">CHÍNH SÁCH BẢO MẬT</h1>
            <ol>
                <li>1. Thu thập thông tin cá nhân</li>
                <ul>
                    <li>Các thông tin thu thập sẽ giúp chúng tôi:</li>
                    <ul>
                        <li>Hỗ trợ khách hàng khi mua sản phẩm.</li>
                        <li>Giải đáp thắc mắc khách hàng.</li>
                        <li>Cung cấp cho quý khách thông tin mới nhất của chúng tôi.</li>
                        <li>Xem xét và nâng cấp nội dung và giao diện của website.</li>
                        <li>Thực hiện các hoạt động quảng bá liên quan đến sản phẩm và dịch vụ.</li>
                    </ul>
                    <li>
                        Để truy cập và sử dụng một số dịch vụ tại website, quý khách có thể sẽ được yêu cầu đăng ký với chúng tôi thông tin cá nhân.
                        Mọi thông tin khai báo phải đảm bảo tính chính xác và hợp pháp.
                    </li>
                </ul>

                <li>2. Sử dụng thông tin cá nhân</li>
                <ul>
                    <li>Chúng tôi sử dụng thông tin để phục vụ khách hàng tốt hơn.</li>
                    <li>Chúng tôi có thể liên hệ với khách hàng qua email, điện thoại hoặc tin nhắn.</li>
                </ul>

                <li>3. Chia sẻ thông tin cá nhân</li>
                <ul>
                    <li>Chúng tôi cam kết không tiết lộ thông tin cá nhân quý khách ra ngoài.</li>
                    <li>Trong một số trường hợp, thông tin có thể được cung cấp cho đơn vị nghiên cứu thị trường.</li>
                    <li>Chúng tôi có thể tiết lộ thông tin khi có yêu cầu từ cơ quan pháp luật.</li>
                </ul>

                <li>4. Truy xuất thông tin cá nhân</li>
                <ul>
                    <li>Khách hàng có thể truy cập và chỉnh sửa thông tin cá nhân bất cứ lúc nào.</li>
                </ul>

                <li>5. Bảo mật thông tin cá nhân</li>
                <ul>
                    <li>Chúng tôi cam kết bảo mật thông tin cá nhân của khách hàng bằng mọi biện pháp có thể.</li>
                    <li>Khách hàng nên đảm bảo rằng không tiết lộ mật khẩu và thông tin đăng nhập của mình.</li>
                    <li>Khi nghi ngờ tài khoản bị xâm nhập, khách hàng nên thay đổi mật khẩu ngay lập tức.</li>
                </ul>
            </ol>
        </div>
    </main>

    <%@ include file="./Public/footer.jsp" %>

    <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
    <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
    <script src="assets/Javascript/main.js"></script>

</body>
</html>
