<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="dao.*"%>
<%@page import="model.*"%>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.NumberFormat"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Checkout | Metronic Shop UI</title>

        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <meta content="Metronic Shop UI description" name="description">
        <meta content="Metronic Shop UI keywords" name="keywords">
        <meta content="keenthemes" name="author">

        <meta property="og:site_name" content="-CUSTOMER VALUE-">
        <meta property="og:title" content="-CUSTOMER VALUE-">
        <meta property="og:description" content="-CUSTOMER VALUE-">
        <meta property="og:type" content="website">
        <meta property="og:image" content="-CUSTOMER VALUE-"><!-- link to image for socio -->
        <meta property="og:url" content="-CUSTOMER VALUE-">

        <link rel="shortcut icon" href="favicon.ico">

        <!-- Fonts START -->

        <!-- Fonts END -->

        <!-- Global styles START -->          
        <link href="TulasCSS/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <link href="TulasCSS/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- Global styles END --> 

        <!-- Page level plugin styles START -->
        <link href="TulasCSS/assets/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet">
        <link href="TulasCSS/assets/plugins/owl.carousel/assets/owl.carousel.css" rel="stylesheet">
        <link href="TulasCSS/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css">
        <link href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" rel="stylesheet" type="text/css"><!-- for slider-range -->
        <link href="TulasCSS/assets/plugins/rateit/src/rateit.css" rel="stylesheet" type="text/css">
        <!-- Page level plugin styles END -->

        <!-- Theme styles START -->
        <link href="TulasCSS/assets/corporate/css/style.css" rel="stylesheet">
        <link href="TulasCSS/assets/pages/css/style-shop.css" rel="stylesheet" type="text/css">
        <link href="TulasCSS/assets/corporate/css/style-responsive.css" rel="stylesheet">
        <link href="TulasCSS/assets/corporate/css/themes/red.css" rel="stylesheet" id="style-color">
        <link href="TulasCSS/assets/corporate/css/custom.css" rel="stylesheet">
        <!-- Theme styles END -->
        <link rel="shortcut icon" href="assets/img/S4EWhite.PNG" type="image/x-icon" />
        <link rel="stylesheet" href="assets/css/reset.css" />
        <link rel="stylesheet" href="assets/css/base.css" />
        <link rel="stylesheet" href="assets/css/main_PC.css" />
        <link rel="stylesheet" href="assets/css/main_Tablet.css" />
        <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
        <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
        <link rel="stylesheet" href="assets/fonts/fontawesome-free-6.0.0-web/css/all.css" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swapsubset=vietnamese" />
        <script src="https://cdn.tailwindcss.com">
        </script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    </head>
    <!-- Head END -->

    <!-- Body BEGIN -->
    <body class="ecommerce">
        <%@ include file="./Public/header.jsp" %>
        <div class="main">
            <div class="container">
                <ul class="breadcrumb">
                    <li><a href="index.html">Home</a></li>
                    <li><a href="">Store</a></li>
                    <li class="active">Checkout</li>
                </ul>
                <!-- BEGIN SIDEBAR & CONTENT -->
                <div class="row">
                    <div class="mx-auto p-6 md:flex md:space-x-6">
                        <div>
                            <h1 class="text-3xl font-bold mb-4">Cảm ơn bạn đã mua hàng!</h1>
                            <p class="mb-6">Đơn hàng của bạn sẽ được xử lý trong vòng 24 giờ vào ngày làm việc. Chúng tôi sẽ thông báo qua email khi đơn hàng được giao.</p>
                            <form id="checkoutForm" action="<%=request.getContextPath()%>/CheckoutServlet" method="POST">
                                <div class="mb-6">
                                    <h2 class="font-bold mb-2">Địa chỉ thanh toán</h2>
                                    <label for="name" class="font-bold">Tên</label>
                                    <input type="text" id="name" name="name" class="border p-2 w-full mt-1" required>

                                    <label for="province" class="font-bold mt-2">Chọn tỉnh/thành</label>
                                    <select id="province" name="province" class="border p-2 w-full mt-1" required>
                                        <option value="">Chọn tỉnh/thành</option>
                                    </select>

                                    <label for="district" class="font-bold mt-2">Chọn quận/huyện</label>
                                    <select id="district" name="district" disabled class="border p-2 w-full mt-1" required>
                                        <option value="">Chọn quận/huyện</option>
                                    </select>

                                    <label for="ward" class="font-bold mt-2">Chọn phường/xã</label>
                                    <select id="ward" name="ward" disabled class="border p-2 w-full mt-1" required>
                                        <option value="">Chọn phường/xã</option>
                                    </select>

                                    <label for="phone" class="font-bold mt-2">Số điện thoại</label>
                                    <input type="text" id="phone" name="phone" class="border p-2 w-full mt-1" required>

                                    <label for="email" class="font-bold mt-2">Email</label>
                                    <input type="email" id="email" name="email" class="border p-2 w-full mt-1" required>

                                    <!-- Thêm input ẩn cho sản phẩm và phí vận chuyển -->
                                    <%                        ProductsDAO pDAO = new ProductsDAO();
                                        Map<Integer, Integer> list = (Map<Integer, Integer>) request.getAttribute("list");
                                        if (list != null) {
                                            int index = 0;
                                            for (int id : list.keySet()) {
                                                int quantity = list.get(id);
                                    %>
                                    <input type="hidden" name="productIds[<%=index%>]" value="<%=id%>">
                                    <input type="hidden" name="quantities[<%=index%>]" value="<%=quantity%>">
                                    <%
                                                index++;
                                            }
                                        }
                                    %>
                                    <input type="hidden" id="shippingFeeInput" name="shippingFee" value="0">
                                </div>
                                <button id="button-confirm" type="submit" class="button-confirm bg-red-500 text-white px-6 py-2 rounded-full">Tạo đơn hàng</button>
                            </form>
                        </div>
                        <div class="md:w-1/2 mt-6 md:mt-0">
                            <div class="bg-gray-100 p-6 rounded-lg shadow-lg">
                                <h2 class="text-xl font-bold mb-4">Tóm tắt đơn hàng</h2>
                                <div class="flex justify-between mb-4">
                                    <div>
                                        <p class="text-sm text-gray-600">Ngày đặt hàng</p>
                                        <p class="font-bold" id="orderDate"></p>
                                    </div>
                                </div>
                                <%                                        // Lấy danh sách các sản phẩm đã chọn từ session

                                    CartItemsDAO ciDAO = new CartItemsDAO();
                                    double sum = 0;
                                    // Lấy đối tượng NumberFormat cho định dạng tiền Việt Nam
                                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                                    if (list != null) {
                                        for (int id : list.keySet()) {
                                            Products p = pDAO.getProductByID(id);
                                            int quantity = list.get(id);
                                            // Tính tổng giá tiền
                                            double totalPrice = p.getPrice() * quantity;
                                            sum += totalPrice;
                                            String formattedTotalPrice = currencyFormat.format(totalPrice);
                                %>
                                <div class="flex justify-between items-center mb-4">
                                    <img alt="Desire Of Hearts" class="w-16 h-16 rounded" height="60" src="<%=p.getImageLink()%>" width="60"/>
                                    <div class="flex-1 ml-4">
                                        <p class="font-bold">
                                            <%=p.getProductName()%>
                                        </p>
                                        <p class="text-sm text-gray-600">
                                            Price: <%=p.getPriceFormat()%>
                                        </p>
                                        <p class="text-sm text-gray-600">
                                            Qty: <%=quantity%>
                                        </p>
                                    </div>
                                    <p class="font-bold">
                                        <%=formattedTotalPrice%>
                                    </p>
                                    <input type="number" class="product-quantity" data-product-id="<%=id%>" value="<%=quantity%>" min="1" readonly hidden>
                                </div>
                                <%
                                    }
                                } else {
                                %>
                                <p>No items selected.</p>
                                <%
                                    }
                                %>
                                <div class="border-t border-gray-300 pt-4">
                                    <div class="flex justify-between mb-2">
                                        <p class="text-gray-600">Tạm tính</p>
                                        <p class="font-bold"><%=currencyFormat.format(sum)%></p>
                                    </div>
                                    <div class="flex justify-between mb-2">
                                        <p class="text-gray-600">Phí vận chuyển</p>
                                        <p id="shippingFee">Đang tính...</p>
                                    </div>

                                </div>
                                <div class="border-t border-gray-300 pt-4">
                                    <div class="flex justify-between">
                                        <p class="text-lg font-bold">Tổng đơn hàng</p>
                                        <p class="text-lg font-bold" id="totalPrice"><%=currencyFormat.format(sum)%></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- END SIDEBAR & CONTENT -->
            </div>
        </div>
        <%@ include file="./Public/footer.jsp" %>

        <!-- Load javascripts at bottom, this will reduce page load time -->
        <!-- BEGIN CORE PLUGINS(REQUIRED FOR ALL PAGES) -->
        <!--[if lt IE 9]>
        <script src="assets/plugins/respond.min.js"></script>  
        <![endif]-->  
        <script src="TulasCSS/assets/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="TulasCSS/assets/plugins/jquery-migrate.min.js" type="text/javascript"></script>
        <script src="TulasCSS/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>      
        <script src="TulasCSS/assets/corporate/scripts/back-to-top.js" type="text/javascript"></script>
        <script src="TulasCSS/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->

        <!-- BEGIN PAGE LEVEL JAVASCRIPTS (REQUIRED ONLY FOR CURRENT PAGE) -->
        <script src="TulasCSS/assets/plugins/fancybox/source/jquery.fancybox.pack.js" type="text/javascript"></script><!-- pop up -->
        <script src="TulasCSS/assets/plugins/owl.carousel/owl.carousel.min.js" type="text/javascript"></script><!-- slider for products -->
        <script src='TulasCSS/assets/plugins/zoom/jquery.zoom.min.js' type="text/javascript"></script><!-- product zoom -->
        <script src="TulasCSS/assets/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script><!-- Quantity -->
        <script src="TulasCSS/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="TulasCSS/assets/plugins/rateit/src/jquery.rateit.js" type="text/javascript"></script>
        <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js" type="text/javascript"></script><!-- for slider-range -->

        <script src="TulasCSS/assets/corporate/scripts/layout.js" type="text/javascript"></script>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                Layout.init();
                Layout.initOWL();
                Layout.initTwitter();
                Layout.initImageZoom();
                Layout.initTouchspin();
                Layout.initUniform();
                Layout.initSliderRange();
                // Kiểm tra nếu user đã đăng nhập
            <c:if test="${user != null}">
                // Vô hiệu hóa Step 1
                $('#step1-toggle').attr('data-toggle', '').removeAttr('href');
                $('#step1-toggle').css('pointer-events', 'none'); // Ngăn chặn click
                $('#step1-toggle').css('color', '#ccc'); // Làm mờ liên kết
                $('#step1-toggle').css('cursor', 'not-allowed'); // Thay đổi con trỏ chuột
            </c:if>
            });
            document.getElementById("orderDate").textContent = new Date().toLocaleDateString("vi-VN", {
                day: "2-digit",
                month: "long",
                year: "numeric"
            });
            document.addEventListener("DOMContentLoaded", function () {
                loadProvinces();
            });

            function loadProvinces() {
                fetch("LocationServlet?action=provinces", {method: "POST"})
                        .then(response => response.json())
                        .then(data => {
                            console.log(data);
                            let provinceSelect = document.getElementById("province");
                            if (data.code === 200 && data.data) {
                                data.data.forEach(province => {
                                    provinceSelect.innerHTML += `<option value="\${province.ProvinceID}">\${province.ProvinceName}</option>`;

                                });
                            }
                        })
                        .catch(error => console.error("Lỗi khi lấy danh sách tỉnh:", error));
            }

            document.getElementById("province").addEventListener("change", function () {
                let provinceId = this.value;
                let districtSelect = document.getElementById("district");
                districtSelect.innerHTML = '<option value="">Chọn quận/huyện</option>';
                districtSelect.disabled = true;

                let wardSelect = document.getElementById("ward");
                wardSelect.innerHTML = '<option value="">Chọn phường/xã</option>';
                wardSelect.disabled = true;

                if (!provinceId)
                    return;

                fetch("LocationServlet?action=districts&provinceId=" + provinceId, {method: "POST"})
                        .then(response => response.json())
                        .then(data => {
                            if (data.code === 200 && data.data) {
                                data.data.forEach(district => {
                                    districtSelect.innerHTML += `<option value="\${district.DistrictID}">\${district.DistrictName}</option>`;
                                });
                                districtSelect.disabled = false;
                            }
                        })
                        .catch(error => console.error("Lỗi khi lấy danh sách quận/huyện:", error));
            });

            document.getElementById("district").addEventListener("change", function () {
                let districtId = this.value;
                let wardSelect = document.getElementById("ward");
                wardSelect.innerHTML = '<option value="">Chọn phường/xã</option>';
                wardSelect.disabled = true;

                if (!districtId)
                    return;

                fetch("LocationServlet?action=wards&districtId=" + districtId, {method: "POST"})
                        .then(response => response.json())
                        .then(data => {
                            if (data.code === 200 && data.data) {
                                data.data.forEach(ward => {
                                    wardSelect.innerHTML += `<option value="\${ward.WardCode}">\${ward.WardName}</option>`;
                                });
                                wardSelect.disabled = false;
                            }
                        })
                        .catch(error => console.error("Lỗi khi lấy danh sách phường/xã:", error));
            });

            document.addEventListener("DOMContentLoaded", function () {
                let wardSelect = document.getElementById("ward");
                wardSelect.addEventListener("change", function () {
                    let toWardCode = this.value;
                    let toDistrictId = document.getElementById("district").value;

                    if (toWardCode && toDistrictId) {
                        calculateShippingFee(toWardCode, toDistrictId);
                    }
                });
                // Xử lý submit form
                document.getElementById("checkoutForm").addEventListener("submit", function (e) {
                    // Đảm bảo phí vận chuyển đã được cập nhật trước khi gửi
                    let shippingFee = document.getElementById("shippingFee").textContent;
                    if (shippingFee !== "Đang tính..." && shippingFee !== "Không thể tính phí ship!") {
                        document.getElementById("shippingFeeInput").value = shippingFee.replace(" VND", "");
                    } else {
                        document.getElementById("shippingFeeInput").value = "0"; // Mặc định là 0 nếu chưa tính
                    }
                });
            });

            function calculateShippingFee(toWardCode, toDistrictId) {
                let sum = <%=sum%>;
                let shippingFee = 0;
                fetch("calculate-shipping", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "toWardCode=" + toWardCode + "&toDistrictId=" + toDistrictId
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.data && data.data.total) {
                                shippingFee = data.data.total;
                                document.getElementById("shippingFee").textContent = shippingFee.toLocaleString("vi-VN", {style: "currency", currency: "VND"});
                                document.getElementById("shippingFeeInput").value = shippingFee; // Cập nhật input ẩn
                                let total = sum + shippingFee;
                                document.getElementById("totalPrice").textContent = total.toLocaleString("vi-VN", {style: "currency", currency: "VND"});
                            } else {
                                document.getElementById("shippingFee").textContent = "Không thể tính phí ship!";
                                document.getElementById("shippingFeeInput").value = "0";
                            }
                        })
                        .catch(error => console.error("Lỗi kết nối API:", error));
            }
            document.getElementById("checkoutForm").addEventListener("submit", function (e) {
                e.preventDefault();
                let province = document.getElementById("province").options[document.getElementById("province").selectedIndex].text;
                let district = document.getElementById("district").options[document.getElementById("district").selectedIndex].text;
                let ward = document.getElementById("ward").options[document.getElementById("ward").selectedIndex].text;
                var name = $('#name').val();
                var email = $('#email').val();
                var phone = $('#phone').val();
                let fullAddress = `\${ward}, \${district}, \${province}`;

                let totalPriceText = document.getElementById("totalPrice").textContent.trim();
                let totalPrice = parseFloat(totalPriceText.replace(/[^\d]/g, '')); // Loại bỏ ký tự không phải số

                // Lấy phí vận chuyển
                let shippingFeeText = document.getElementById("shippingFee").textContent.trim();
                let shippingFee = (shippingFeeText === "Đang tính..." || shippingFeeText === "Không thể tính phí ship!")
                        ? 0
                        : parseFloat(shippingFeeText.replace(/[^\d]/g, ''));

                // Thu thập thông tin sản phẩm từ bảng
                let products = [];
                $('.product-quantity').each(function () {
                    let productId = $(this).data('product-id');
                    let quantity = $(this).val();
                    if (productId && quantity) {
                        products.push({
                            productId: parseInt(productId),
                            quantity: parseInt(quantity)
                        });
                    }
                });

                // Tạo đối tượng dữ liệu để gửi đi
                var data = {
                    name: name,
                    email: email,
                    phone: phone,
                    fullAddress: fullAddress,
                    products: products,
                    totalPrice: totalPrice
                };

                // Gửi dữ liệu bằng AJAX
                console.log("json", JSON.stringify(data)); // Kiểm tra dữ liệu trước khi gửi

                $.ajax({
                    url: '<%=request.getContextPath()%>/CheckoutServlet',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function (response) {
                        console.log(response); // In phản hồi từ server
                        
                    },
                    error: function (xhr, status, error) {
                        console.error(error); // In lỗi chi tiết
                        alert('An error occurred while processing your request: ' + error);
                    }
                });
            });
        </script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <!-- END PAGE LEVEL JAVASCRIPTS -->
    </body>
    <!-- END BODY -->
</html>