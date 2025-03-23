<%-- 
    Document   : HomePage
    Created on : Jan 18, 2025, 12:47:06 AM
    Author     : Tung Duong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <style>

        .filter-title {
            font-size: 16px;
            font-weight: bold;
            color: #d9232d;
            margin-top: 15px;
            margin-bottom: 10px;
        }

        .filter-list {
            list-style: none;
            padding: 0;
            margin: 10px 0;
        }

        .filter-item {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 6px 0;
        }

        .filter-checkbox {
            width: 16px;
            height: 16px;
            accent-color: #d9232d;
            cursor: pointer;
        }

        .filter-label {
            font-size: 14px;
            color: #333;
            cursor: pointer;
        }

        .filter-select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            background: white;
            cursor: pointer;
        }

        .filter-button {
            width: 100%;
            background: #d9232d;
            color: white;
            font-size: 16px;
            font-weight: bold;
            border: none;
            padding: 10px;
            border-radius: 6px;
            cursor: pointer;
            transition: 0.3s;
        }

        .filter-button:hover {
            background: #b71c23;
        }

        /*Pagination*/
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
            margin-top: 20px;
            font-size: 16px;
        }

        .pagination-link {
            display: inline-block;
            text-decoration: none;
            color: #333;
            background: #f0f0f0;
            padding: 8px 12px;
            border-radius: 5px;
            transition: background 0.3s, color 0.3s;
        }

        .pagination-link:hover {
            background: #d9232d;
            color: white;
        }

        .pagination-link.active {
            background: #d9232d;
            color: white;
            font-weight: bold;
        }

        .pagination-dots {
            color: #888;
            font-size: 18px;
        }

        .pagination-arrow {
            display: inline-block;
            text-decoration: none;
            color: #d9232d;
            font-size: 18px;
            padding: 6px 10px;
            transition: color 0.3s;
        }

        .pagination-arrow:hover {
            color: #b71c23;
        }

    </style>
    <body>
        <%@ include file="./Public/header.jsp" %>

        <!-- MAIN -->
        <main class="main">
            <div class="main__gird gird"> <!-- Cái khung của trang web -->
                <div class="main__left">
                    <!-- Danh mục sản phẩm - Gom các sản phẩm thành các danh mục nhỏ - Hiển thị trên cùng bên trái main trang web -->
                    <form method="GET" action="productsList">
                        <h2 class="main__left-title" style="margin-top: 50px">Tìm kiếm</h2>
                        <input value="${name}" type="text" style="width: 100%" class="main__left-search-box" name="name" placeholder="Bạn cần tìm gì?" />
                        <h6 class="filter-title">Danh mục</h6>

                        <ul class="filter-list">
                            <c:forEach var="cate" items="${categories}">
                                <c:set var="isChecked" value="false"/>

                                <c:forEach var="selected" items="${category}">
                                    <c:if test="${selected eq cate.categoryID}">
                                        <c:set var="isChecked" value="true"/>
                                    </c:if>
                                </c:forEach>

                                <li class="filter-item">
                                    <input type="checkbox" class="filter-checkbox" name="category" value="${cate.categoryID}"
                                           <c:if test="${isChecked eq 'true'}">checked</c:if>  />
                                    <label class="filter-label">${cate.categoryName}</label>
                                </li>
                            </c:forEach>

                        </ul>

                        <h6 class="filter-title">Sắp xếp theo</h6>
                        <select class="filter-select" name="orderBy">
                            <c:forEach items="${orderByList}" var="order">
                                <option value="${order.value}" <c:if test="${order.value == orderBy}">selected</c:if>>
                                    ${order.label}
                                </option>
                            </c:forEach>
                        </select>

                        <button class="filter-button">Tìm kiếm</button>

                        <input type="hidden" name="index" value="${index}"/>
                    </form>
                </div>

                <!-- Main Right -->
                <!-- Đây là phần thân bên phải của trang web -->
                <div class="main__right">
                    <!-- Danh mục các sản phẩm -->
                    <div class="main__right-sensor">
                        <h3 class="main__right-heading">
                            <span class="main__right-heading-title">Sản phẩm</span>
                            <!--                            <a href="Category.jsp?id=TBQ" class="main__right-heading-link">
                                                            Xem thêm
                                                            <i class="main__right-heading-icon fa-solid fa-angle-right"></i>
                                                        </a>-->
                        </h3>

                        <div class="main__right-sensor-list">
                            <c:forEach var="product" items="${products}">
                                <div class="main__right-sensor-items">
                                    <a href="ProductDetailControllerCustomer?id=${product.productID}" class="main__right-sensor-link">
                                        <img src="${product.imageLink}" height="200px" alt="${product.productName}" class="main__right-sensor-img" />
                                        <div class="main__right-sensor-title">${product.productName}</div>
                                    </a>
                                    <!-- Hiển thị giá sản phẩm -->
                                    <span class="main__right-sensor-price">
                                        <strong><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>đ</strong>
                                    </span>
                                    <!--<button type="button" class="main__right-sensor-button"/>Mua ngay</button>-->
                                </div>
                            </c:forEach>

                        </div>
                        <c:set var="queryParams" value="name=${name}&orderBy=${orderBy}" />
                        <c:forEach var="cat" items="${category}">
                            <c:set var="queryParams" value="${queryParams}&category=${cat}" />
                        </c:forEach>

                        <div class="pagination">
                            <a href="productsList?${queryParams}&index=${index-1}" class="pagination-arrow">&laquo;</a>
                            <c:choose>
                                <c:when test="${index <= 4}">
                                    <c:choose>
                                        <c:when test="${total <= 5}">
                                            <c:forEach begin="1" end="${total}" step="1" var="i">
                                                <a href="productsList?${queryParams}&index=${i}" class="pagination-link <c:if test='${i == index}'> active </c:if>">
                                                    ${i}
                                                </a>
                                            </c:forEach>
                                        </c:when>
                                        <c:when test="${total > 5}">
                                            <c:forEach begin="1" end="5" step="1" var="i">
                                                <a href="productsList?${queryParams}&index=${i}" class="pagination-link <c:if test='${i == index}'> active </c:if>">
                                                    ${i}
                                                </a>
                                            </c:forEach>
                                            <span class="pagination-dots">...</span>
                                            <a href="productsList?${queryParams}&index=${total}" class="pagination-link">${total}</a>
                                        </c:when>
                                    </c:choose>
                                </c:when>
                                <c:when test="${total > 5 and index > 4 and index < (total-3)}">
                                    <a href="productsList?${queryParams}&index=1" class="pagination-link">1</a>
                                    <span class="pagination-dots">...</span>
                                    <c:forEach begin="${index-2}" end="${index+2}" step="1" var="i">
                                        <a href="productsList?${queryParams}&index=${i}" class="pagination-link <c:if test='${i == index}'> active </c:if>">
                                            ${i}
                                        </a>
                                    </c:forEach>
                                    <span class="pagination-dots">...</span>
                                    <a href="productsList?${queryParams}&index=${total}" class="pagination-link">${total}</a>
                                </c:when>
                                <c:when test="${total > 5 and index >= (total-3)}">
                                    <a href="productsList?${queryParams}&index=1" class="pagination-link">1</a>
                                    <span class="pagination-dots">...</span>
                                    <c:forEach begin="${total - 4}" end="${total}" step="1" var="i">
                                        <a href="productsList?${queryParams}&index=${i}" class="pagination-link <c:if test='${i == index}'> active </c:if>">
                                            ${i}
                                        </a>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                            <a href="productsList?${queryParams}&index=${index+1}" class="pagination-arrow">&raquo;</a>
                        </div>

                    </div>
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
