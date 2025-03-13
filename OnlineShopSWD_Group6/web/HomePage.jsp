<%-- 
    Document   : HomePage
    Created on : Jan 18, 2025, 12:47:06 AM
    Author     : Tung Duong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Category, model.Products"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Cửa hàng đồ diện - S4E</title>
        <link rel="shortcut icon" href="assets/img/S4EWhite.PNG" type="image/x-icon" />
        <link rel="stylesheet" href="assets/css/reset.css" />
        <link rel="stylesheet" href="assets/css/base.css" />
        <link rel="stylesheet" href="assets/css/main_PC.css" />
        <link rel="stylesheet" href="assets/css/main_Tablet.css" />
        <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
        <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
        <link rel="stylesheet" href="assets/fonts/fontawesome-free-6.0.0-web/css/all.css" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swapsubset=vietnamese" />

    </head>
    <body>
        <%@ include file="./Public/header.jsp" %>

        <!-- MAIN -->
        <main class="main">
            <div class="main__gird gird"> <!-- Cái khung của trang web -->
                <div class="main__left">
                    <!-- Danh mục sản phẩm - Gom các sản phẩm thành các danh mục nhỏ - Hiển thị trên cùng bên trái main trang web -->
                    <h2 class="main__left-title">DANH MỤC SẢN PHẨM</h2>

                 

                    <ul class="main__left-category-list">
                        <c:forEach var="categoryID" items="${['TBQ', 'TBCS', 'CTD', 'TBTM', 'TBSCBT']}">
                            <c:forEach var="category" items="${categories}">
                                <c:if test="${category.categoryID == categoryID}">
                                    <li class="main__left-category-items">
                                        <a href="Category.jsp?id=${category.categoryID}" class="main__left-category-link">
                                            ${category.categoryName}
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </ul>

                    <!-- Main Left Promotional Products : Sản phẩm khuyến mãi -->
                    <!-- Một số sản phẩm khuyến mãi - Hiển thị dưới Danh mục sản phẩm -->
                    <h2 class="main__left-title main__left-margin-top">SẢN PHẨM KHUYẾN MÃI</h2>
                    <ul class="main__left-product-list">
                        <c:forEach var="product" items="${promotedProducts}" varStatus="status">
                            <c:if test="${status.index < 5}">  <%-- Chỉ hiển thị 5 sản phẩm đầu tiên --%>
                                <li class="main__left-product-items main__left-product-items--vertical">
                                    <a href="/Project-SWP391-G2-SP25/ProductDetailControllerCustomer?id=${product.productID}" class="main__left-product-link">
                                        <img src="${product.imageLink}" alt="${product.productName}" class="main__left-product-img" />
                                        <span class="main__left-product-title">${product.productName}</span>
                                    </a>
                                    <br />
                                    <span class="main__left-product"><del>
                                            <fmt:formatNumber value="${product.oldprice}" type="number" groupingUsed="true"/>đ
                                        </del></span>
                                    <span class="main__right-sensor-price">
                                        <strong><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>đ</strong>
                                    </span>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>

                    <!-- Main Left New products : Sản phẩm mới -->
                    <!-- Một số sản phẩm mới của cửa hàng - Hiển thị dưới Sản phẩm khuyến mãi -->
                    <h2 class="main__left-title main__left-margin-top">SẢN PHẨM MỚI</h2>
                    <ul class="main__left-product-list">
                        <c:forEach var="product" items="${newProducts}" varStatus="status">
                            <c:if test="${status.index < 5}">
                                <li class="main__left-product-items main__left-product-items--vertical">
                                    <a href="/Project-SWP391-G2-SP25/ProductDetailControllerCustomer?id=${product.productID}" class="main__left-product-link">
                                        <img src="${product.imageLink}" alt="${product.productName}" class="main__left-product-img" />
                                        <span class="main__left-product-title">${product.productName}</span>
                                    </a>
                                    <br />
                                    <span class="main__right-sensor-price">
                                        <strong><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>đ</strong>
                                    </span>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>

                <!-- Main Right -->
                <!-- Đây là phần thân bên phải của trang web -->
                <div class="main__right">
                    <!-- Panel của trang web - Có thể lướt qua lại, dùng để quảng cáo làm cho trang web nổi bật hơn -->
                    <!-- Imgage Slider start -->
                    <div class="main__right-panel">
                        <div class="swiper swiper-panel">
                            <!-- Additional required wrapper -->
                            <div class="swiper-wrapper">
                                <!-- Slides -->
                                <div class="swiper-slide"><img src="./assets/img/Panel1.jpg" alt="" class="main__right-panel-img"></div>
                                <div class="swiper-slide"><img src="./assets/img/Panel2.png" alt="" class="main__right-panel-img"></div>
                                <div class="swiper-slide"><img src="./assets/img/Panel3.png" alt="" class="main__right-panel-img"></div>
                                <div class="swiper-slide"><img src="./assets/img/Panel4.png" alt="" class="main__right-panel-img"></div>
                            </div>

                            <div class="panel-prev">
                                <i class="fa-solid fa-angle-left button-prev"></i>
                            </div>
                            <div class="panel-next">
                                <i class="fa-solid fa-angle-right button-next"></i>
                            </div>

                        </div>
                    </div>   
                    <!-- Imgage Slider end -->

                    <!-- Danh mục các sản phẩm quạt điện -->
                    <div class="main__right-sensor">
                        <h3 class="main__right-heading">
                            <span class="main__right-heading-title">Thiết bị quạt</span>
                            <a href="Category.jsp?id=TBQ" class="main__right-heading-link">
                                Xem thêm
                                <i class="main__right-heading-icon fa-solid fa-angle-right"></i>
                            </a>
                        </h3>

                        <div class="main__right-sensor-list">
                            <c:forEach var="product" items="${fanProducts}">
                                <div class="main__right-sensor-items">
                                    <a href="/Project-SWP391-G2-SP25/ProductDetailControllerCustomer?id=${product.productID}" class="main__right-sensor-link">
                                        <img src="${product.imageLink}" alt="${product.productName}" class="main__right-sensor-img" />
                                        <div class="main__right-sensor-title">${product.productName}</div>
                                    </a>
                                    <!-- Hiển thị giá sản phẩm -->
                                    <span class="main__right-sensor-price">
                                        <strong><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>đ</strong>
                                    </span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <!--Danh mục Thiết bị chiếu sáng 
                        Lighting equipment : Thiết bị chiếu sáng - Start -->
                    <div class="main__right-sensor">
                        <h3 class="main__right-heading">
                            <span class="main__right-heading-title">Thiết bị chiếu sáng</span>
                            <a href="Category.jsp?id=TBCS" class="main__right-heading-link">
                                Xem thêm
                                <i class="main__right-heading-icon fa-solid fa-angle-right"></i>
                            </a>
                        </h3>
                        <div class="main__right-sensor-list swiper mySwiper1">
                            <div class="swiper-wrapper">
                                <c:forEach var="product" items="${lightingProducts}">
                                    <div class="swiper-slide">
                                        <div class="main__right-sensor-item">
                                            <a href="/Project-SWP391-G2-SP25/ProductDetailControllerCustomer?id=${product.productID}" class="main__right-sensor-link">
                                                <img src="${product.imageLink}" alt="${product.productName}" class="main__right-sensor-img" />
                                                <div class="main__right-sensor-title">${product.productName}</div>
                                            </a>
                                            <span class="main__right-sensor-price">
                                                <strong><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>đ</strong>
                                            </span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="main__right-sensor-prev">
                                <i class="fa-solid fa-angle-left sensor-button-prev"></i>
                            </div>
                            <div class="main__right-sensor-next">
                                <i class="fa-solid fa-angle-right sensor-button-next"></i>
                            </div>
                        </div>
                    </div>
                    <!--Lighting equipment : Thiết bị chiếu sáng - End -->

                    <!-- Danh mục các sản phẩm Công tắc điện -->
                    <!-- Electronic Switch : Công tắc điện - Start -->
                    <div class="main__right-sensor">
                        <h3 class="main__right-heading">
                            <span class="main__right-heading-title">Công tắc điện</span>
                            <a href="Category.jsp?id=CTD" class="main__right-heading-link">
                                Xem thêm
                                <i class="main__right-heading-icon fa-solid fa-angle-right"></i>
                            </a>
                        </h3>
                        <div class="main__right-sensor-list swiper mySwiper2">
                            <div class="swiper-wrapper">
                                <c:forEach var="product" items="${electricalSwitchProducts}">
                                    <div class="swiper-slide">
                                        <div class="main__right-sensor-item">
                                            <a href="/Project-SWP391-G2-SP25/ProductDetailControllerCustomer?id=${product.productID}" class="main__right-sensor-link">
                                                <img src="${product.imageLink}" alt="${product.productName}" class="main__right-sensor-img" />
                                                <div class="main__right-sensor-title">${product.productName}</div>
                                            </a>
                                            <span class="main__right-sensor-price">
                                                <strong><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>đ</strong>
                                            </span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="main__right-electrical-prev">
                                <i class="fa-solid fa-angle-left electrical-button-prev"></i>
                            </div>
                            <div class="main__right-electrical-next">
                                <i class="fa-solid fa-angle-right electrical-button-next"></i>
                            </div>
                        </div>
                    </div>
                    <!-- Electronic Switch : Công tắc điện - End -->

                    <!-- Thiết bị thông minh - Start -->
                    <!-- Main Right Smart Devices -->
                    <div class="main__right-sensor">
                        <h3 class="main__right-heading">
                            <span class="main__right-heading-title">Thiết bị thông minh</span>
                            <a href="#" class="main__right-heading-link">
                                Xem thêm
                                <i class="main__right-heading-icon fa-solid fa-angle-right"></i>
                            </a>
                        </h3>

                        <div class="main__right-sensor-list">
                            <c:forEach var="product" items="${smartDevices}">
                                <div class="main__right-sensor-items">
                                    <a href="/Project-SWP391-G2-SP25/ProductDetailControllerCustomer?id=${product.productID}" class="main__right-sensor-link">
                                        <img src="${product.imageLink}" alt="${product.productName}" class="main__right-sensor-img" />
                                        <div class="main__right-sensor-title">${product.productName}</div>
                                    </a>
                                    <span class="main__right-sensor-price">
                                        <strong><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>đ</strong>
                                    </span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- Thiết bị thông minh - End -->
                </div>
                <div class="clear"></div>

                <!-- Thông tin vận chuyển, giao hàng, thanh toán và chăm sóc khách hàng của cửa hàng -->
                <div class="main__installation clear">
                    <div class="main__installation-box">
                        <img src="assets/img/KT-1.png" alt="Hình ảnh minh họa cho chức năng" />
                        <br />
                        <strong class="main__installation-strong">Lắp đặt chuyên nghiệp</strong>
                        <br />
                        <span class="main__installation-title">Đội ngũ lắp đặt giàu kinh nghiệm</span>
                    </div>
                    <div class="main__installation-box">
                        <img src="assets/img/KT-2.png" alt="Hình ảnh minh họa cho chức năng" />
                        <br />
                        <strong class="main__installation-strong">Giao nhận tiện lợi</strong>
                        <br />
                        <span class="main__installation-title">Giao nhận trong ngày nhanh</span>
                    </div>
                    <div class="main__installation-box">
                        <img src="assets/img/KT-3.png" alt="Hình ảnh minh họa cho chức năng" />
                        <br />
                        <strong class="main__installation-strong">Thanh toán linh hoạt</strong>
                        <br />
                        <span class="main__installation-title">Phương thức thanh toán đa dạng</span>
                    </div>
                    <div class="main__installation-box">
                        <img src="assets/img/KT-4.png" alt="Hình ảnh minh họa cho chức năng" />
                        <br />
                        <strong class="main__installation-strong">Hậu mãi chu đáo</strong>
                        <br />
                        <span class="main__installation-title">Chăm sóc khách hàng tận tình</span>
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
