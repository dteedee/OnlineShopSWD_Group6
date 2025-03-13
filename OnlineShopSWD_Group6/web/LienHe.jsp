<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Liên hệ - S4E</title>
        <link rel="shortcut icon" href="assets/img/S4EWhite.png" type="image/x-icon" />
        <link rel="stylesheet" href="assets/css/reset.css" />
        <link rel="stylesheet" href="assets/css/base.css" />
        <link rel="stylesheet" href="assets/css/main_PC.css" />
        <link rel="stylesheet" href="assets/css/main_Tablet.css" />
        <link rel="stylesheet" href="assets/css/TaiKhoan/LienHe.css" />
        <link rel="stylesheet" href="assets/fonts/fontawesome-free-6.0.0-web/css/all.css" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap&subset=vietnamese" />

        <style>
            body {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                overflow-x: hidden; /* Ngăn chặn tràn ngang */
                font-family: 'Roboto', sans-serif;
            }

            .container {
                width: 75%;
                margin: 0 auto;
                padding-top: 20px;
                max-width: 1100px;
            }

            .contact-container {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                margin-top: 20px;
                flex-wrap: wrap;
            }

            .contact-form {
                flex: 1;
                max-width: 50%;
                padding-right: 50px;
            }

            .contact-info {
                flex: 1;
                max-width: 50%;
            }

            .contact-form input,
            .contact-form textarea {
                width: 100%;
                padding: 10px;
                font-size: 15px;
                margin-bottom: 12px;
                border: 1px solid #ccc;
                border-radius: 4px;
                outline: none;
                box-sizing: border-box;
            }

            .contact-form button {
                font-size: 16px;
                padding: 12px 20px;
                font-weight: 600;
                color: #fff;
                background-color: #dc0021;
                border: none;
                cursor: pointer;
                border-radius: 5px;
                width: 100%;
                max-width: 250px;
            }

            .contact-info p {
                margin-bottom: 12px;
                font-size: 16px;
                line-height: 1.6;
            }

            .contact-info p strong {
                color: #222;
                font-weight: bold;
            }

            /* Điều chỉnh kích thước phần input email và số điện thoại */
            .form-row {
                display: flex;
                gap: 10px;
            }

            .form-row input {
                flex: 1;
            }

            h1 {
                text-align: center;
                font-size: 28px;
                font-weight: bold;
                margin-bottom: 30px;
            }

            h3 {
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 20px;
            }

            /* Điều chỉnh container để thẳng hàng với menu */
            .main {
                margin-left: auto;
                margin-right: auto;
                width: 100%;
                max-width: 1100px;
                padding: 20px;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .contact-container {
                    flex-direction: column;
                    align-items: center;
                }

                .contact-form, .contact-info {
                    max-width: 100%;
                    padding-right: 0;
                    text-align: center;
                }

                .contact-form button {
                    width: 100%;
                }
            }
        </style>
    </head>
    <body>

        <%@ include file="./Public/header.jsp" %>

        <main class="container">
            <h1>LIÊN HỆ</h1>

            <div class="contact-container">
                <!-- Form liên hệ -->
                <div class="contact-form">
                    <h3>Gửi thắc mắc cho chúng tôi</h3>
                    <form action="ContactServlet" method="post">
                        <input type="text" name="name" placeholder="Tên của bạn" required />
                        <div class="form-row">
                            <input type="email" name="email" placeholder="Email của bạn" required />
                            <input type="text" name="phone" placeholder="Số điện thoại của bạn" required />
                        </div>
                        <textarea rows="4" name="message" placeholder="Nội dung" required></textarea>
                        <button type="submit">GỬI CHO CHÚNG TÔI</button>
                    </form>
                </div>

                <!-- Thông tin liên hệ -->
                <div class="contact-info">
                    <h3>Liên hệ</h3>
                    <p><strong>Địa chỉ của chúng tôi</strong><br/>
                         Trường Đại Học FPT Hà Nội, Khu Công nghệ cao Hòa Lạc, Km29 Đại lộ Thăng Long, huyện Thạch Thất, Hà Nội.</p>

                    <p><strong>Email của chúng tôi</strong><br/>
                        shop4electric@gmail.com</p>

                    <p><strong>Điện thoại</strong><br/>
                        0969.995.633</p>

                    <p><strong>Thời gian làm việc</strong><br/>
                        Thứ 2 đến Thứ 6 từ 8h đến 22h; Thứ 7 và Chủ nhật từ 8h00 đến 20h00</p>
                </div>
            </div>
        </main>

        <%@ include file="./Public/footer.jsp" %>

        <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
        <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
        <script src="assets/Javascript/main.js"></script>

    </body>
</html>
