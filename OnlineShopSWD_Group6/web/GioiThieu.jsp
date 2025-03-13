<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Giới thiệu</title>
        <link rel="shortcut icon" href="assets/img/S4EWhite.png" type="image/x-icon" />
        <link rel="stylesheet" href="assets/css/reset.css" />
        <link rel="stylesheet" href="assets/css/base.css" />
        <link rel="stylesheet" href="assets/css/main_PC.css" />
        <link rel="stylesheet" href="assets/css/main_Tablet.css" />
        <link rel="stylesheet" href="assets/css/TaiKhoan/GioiThieu.css" />
        <link rel="stylesheet" href="assets/fonts/fontawesome-free-6.0.0-web/css/all.css" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap&subset=vietnamese" />
    </head>
    <body>

        <%@ include file="./Public/header.jsp" %>

        <main class="gird">
            <div class="mainGT">
                <h1 class="mainGT__heading">GIỚI THIỆU</h1>
                <div class="mainGT__img">
                    <img src="assets/img/Panel1.jpg" alt="Ảnh nền giới thiệu cho doanh nghiệp" width="100%" />
                </div>
                <div class="mainGT__content">
                    <h2 class="mainGT__heading" style="font-size: 24px;">Chào mừng bạn đến với S4E - Cửa hàng thiết bị điện hàng đầu!</h2>
                    <p class="mainGT__content-p">
                        S4E (Shop For Electrical) là một trang web chuyên cung cấp các thiết bị điện, linh kiện điện tử, thiết bị chiếu sáng, 
                        công tắc điện, thiết bị thông minh và nhiều sản phẩm liên quan đến điện dân dụng cũng như công nghiệp. 
                        Chúng tôi tự hào là một trong những đơn vị tiên phong trong lĩnh vực phân phối và bán lẻ đồ điện online tại Việt Nam.
                        <br>
                        S4E được thành lập với mục tiêu mang đến cho khách hàng:
                    </p>
                    <ul class="mainGT__content-list">
                        <li class="mainGT__content-items">Những sản phẩm chất lượng cao: Chúng tôi cung cấp các sản phẩm từ những thương hiệu uy tín, đảm bảo an toàn và hiệu suất cao trong quá trình sử dụng.</li>
                        <li class="mainGT__content-items">Giá cả hợp lý: Cam kết giá cả cạnh tranh nhất trên thị trường, phù hợp với mọi đối tượng khách hàng.</li>
                        <li class="mainGT__content-items">Mua sắm tiện lợi: Hệ thống đặt hàng trực tuyến nhanh chóng, đơn giản, giúp khách hàng dễ dàng tìm kiếm và mua sắm mọi lúc, mọi nơi.</li>
                        <li class="mainGT__content-items">Hỗ trợ tận tình: Đội ngũ tư vấn chuyên nghiệp, sẵn sàng hỗ trợ khách hàng trong việc lựa chọn sản phẩm phù hợp với nhu cầu.</li>
                    </ul>
                    <p class="mainGT__content-p">Danh mục sản phẩm tại S4E</p>
                    <ul class="mainGT__content-list">
                        <li class="mainGT__content-items">Công tắc & Ổ cắm điện: Công tắc thông minh, ổ cắm đa năng, ổ cắm chống sét…</li>
                        <li class="mainGT__content-items">Thiết bị điện dân dụng: Quạt điện, máy sấy, ổn áp, biến áp…</li>
                        <li class="mainGT__content-items">Linh kiện điện tử: Điện trở, tụ điện, IC, cảm biến, mạch điện…</li>
                        <li class="mainGT__content-items">Thiết bị điện thông minh: Nhà thông minh, khóa cửa thông minh, camera giám sát…</li>
                        <li class="mainGT__content-items">Phụ kiện & Dụng cụ: Dây điện, cầu chì, bút thử điện, đồng hồ đo điện…</li>
                    </ul>
                </div>
                <div class="mainGT-TTT">
                    <div class="mainGT__content-left">
                        <h2 class="mainGT__heading" style="font-size: 24px;">Cam kết của chúng tôi</h2>
                        <p class="mainGT__content-p">
                            ✔ Chất lượng đặt lên hàng đầu: Tất cả sản phẩm đều trải qua kiểm tra nghiêm ngặt trước khi đến tay khách hàng.
                        </p>
                        <p class="mainGT__content-p">
                            ✔ Giá cả cạnh tranh: Chúng tôi luôn đưa ra mức giá tốt nhất với nhiều chương trình khuyến mãi hấp dẫn.
                        </p>
                        <p class="mainGT__content-p">
                            ✔ Trải nghiệm mua sắm an toàn: Giao dịch trực tuyến đảm bảo bảo mật, đổi trả dễ dàng nếu có lỗi từ nhà sản xuất.
                        </p>
                    </div>

                    <div class="mainGT__content-right">
                        <iframe src="https://www.youtube.com/embed/PkZNo7MFNFg" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </main>

        <%@ include file="./Public/footer.jsp" %>

        <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
        <script src="https://unpkg.com/swiper@8/swiper-bundle.min.js"></script>
        <script src="assets/Javascript/main.js"></script>

    </body>
</html>
