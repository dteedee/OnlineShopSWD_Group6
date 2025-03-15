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

                <div class="row margin-bottom-40">
                    <!-- BEGIN CONTENT -->
                    <div class="col-md-12 col-sm-12">
                        <h1>Checkout</h1>
                        <!-- BEGIN CHECKOUT PAGE -->
                        <div class="panel-group checkout-page accordion scrollable" id="checkout-page">

                            <!-- BEGIN SHIPPING ADDRESS -->
                            <div id="shipping-address" class="panel panel-default">
                                <div class="panel-heading">
                                    <h2 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#checkout-page" href="#shipping-address-content" class="accordion-toggle">
                                            Step 1: Delivery Details
                                        </a>
                                    </h2>
                                </div>
                                <div id="shipping-address-content" class="panel-collapse collapse">
                                    <div class="panel-body row">
                                        <div class="col-md-6 col-sm-6">
                                            <div class="form-group">
                                                <label for="firstname-dd">First Name <span class="require">*</span></label>
                                                <input type="text" id="firstname-dd" class="form-control" value="<%= (user != null) ? user.getFirstName() : "" %>">
                                            </div>
                                            <div class="form-group">
                                                <label for="lastname-dd">Last Name <span class="require">*</span></label>
                                                <input type="text" id="lastname-dd" class="form-control" value="<%= (user != null) ? user.getLastName() : "" %>">
                                            </div>
                                            <div class="form-group">
                                                <label for="email-dd">E-Mail <span class="require">*</span></label>
                                                <input type="text" id="email-dd" class="form-control" value="<%= (user != null) ? user.getEmail() : "" %>">
                                            </div>
                                        </div>
                                        <div class="col-md-6 col-sm-6">
                                            <div class="form-group">
                                                <label for="address1-dd">Address</label>
                                                <input type="text" id="address-dd" class="form-control" value="<%= (user != null) ? user.getAddress() : "" %>">
                                            </div>
                                            <div class="form-group">
                                                <label for="post-code-dd">Giới tính <span class="require">*</span></label>
                                                <input type="text" id="gender-dd" class="form-control" value="<%= (user != null) ? user.getGender() : "" %>">
                                            </div>
                                            <div class="form-group">
                                                <label for="telephone-dd">Telephone <span class="require">*</span></label>
                                                <input type="text" id="phone-dd" class="form-control" value="<%= (user != null) ? user.getPhoneNumber() : "" %>">
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <button class="btn btn-primary  pull-right" type="submit" id="button-shipping-address" data-toggle="collapse" data-parent="#checkout-page" data-target="#shipping-method-content">Continue</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- END SHIPPING ADDRESS -->

                            <!-- BEGIN PAYMENT METHOD -->
                            <div id="payment-method" class="panel panel-default">
                                <div class="panel-heading">
                                    <h2 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#checkout-page" href="#payment-method-content" class="accordion-toggle">
                                            Step 2: Payment Method
                                        </a>
                                    </h2>
                                </div>
                                <div id="payment-method-content" class="panel-collapse collapse">
                                    <div class="panel-body row">
                                        <div class="col-md-12">
                                            <p>Please select the preferred payment method to use on this order.</p>
                                            <div class="radio-list">
                                                <label>
                                                    <input type="radio" name="CashOnDelivery" value="CashOnDelivery" id="payment-dd"> Cash On Delivery
                                                </label>
                                            </div>
                                            <div class="form-group">
                                                <label for="delivery-payment-method">Add Comments About Your Order</label>
                                                <textarea id="delivery-payment-method" rows="8" class="form-control" id="comment-dd"></textarea>
                                            </div>
                                            <button class="btn btn-primary  pull-right" type="submit" id="button-payment-method" data-toggle="collapse" data-parent="#checkout-page" data-target="#confirm-content">Continue</button>
                                            <div class="checkbox pull-right">
                                                <label>
                                                    <input type="checkbox"> I have read and agree to the <a title="Terms & Conditions" href="javascript:;">Terms & Conditions </a> &nbsp;&nbsp;&nbsp; 
                                                </label>
                                            </div>  
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- END PAYMENT METHOD -->

                            <!-- BEGIN CONFIRM -->
                            <div id="confirm" class="panel panel-default">
                                <div class="panel-heading">
                                    <h2 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#checkout-page" href="#confirm-content" class="accordion-toggle">
                                            Step 3: Confirm Order
                                        </a>
                                    </h2>
                                </div>
                                <div id="confirm-content" class="panel-collapse collapse">
                                    <div class="panel-body row">
                                        <div class="col-md-12 clearfix">
                                            <div class="table-wrapper-responsive">
                                                <table>
                                                    <tr>
                                                        <th class="checkout-image">Image</th>
                                                        <th class="checkout-description">Description</th>
                                                        <th class="checkout-model">Model</th>
                                                        <th class="checkout-quantity">Quantity</th>
                                                        <th class="checkout-price">Price</th>
                                                        <th class="checkout-total">Total</th>
                                                    </tr>
                                                    <%
                                                            // Lấy danh sách các sản phẩm đã chọn từ session
                                                            ProductsDAO pDAO = new ProductsDAO();
                                                            CartItemsDAO ciDAO = new CartItemsDAO();
                                                            Map<Integer, Integer> list = (Map<Integer, Integer>) request.getAttribute("list");
                                                            // Lấy đối tượng NumberFormat cho định dạng tiền Việt Nam
                                                            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                                                            if (list != null) {
                                                                for(int id : list.keySet()){
                                                                Products p = pDAO.getProductByID(id);
                                                                int quantity = list.get(id);
                                                                // Tính tổng giá tiền
                                                                double totalPrice = p.getPrice() * quantity;
                                                                String formattedTotalPrice = currencyFormat.format(totalPrice);
                                                    %>
                                                    <tr>
                                                        <td class="checkout-image">
                                                            <img src="<%=p.getImageLink()%>">
                                                        </td>
                                                        <td class="checkout-description">
                                                            <h3><a href="javascript:;">Cool green dress with red bell</a></h3>
                                                            <p><strong><%=p.getProductName()%></strong></p>
                                                            <em><%=p.getDescription()%></em>
                                                        </td>
                                                        <td class="checkout-model"><%=p.getDescription()%></td>
                                                        <td class="checkout-quantity"><%=quantity%>
                                                            <input type="number" class="product-quantity" data-product-id="<%=id%>" value="<%=quantity%>" min="1" readonly hidden>
                                                        </td>
                                                        <td class="checkout-price"><strong><%=p.getPriceFormat()%></strong></td>
                                                        <td class="checkout-total"><strong><%=formattedTotalPrice%></strong></td>
                                                    </tr>
                                                    <%
                                                            }
                                                        } else {
                                                    %>
                                                    <p>No items selected.</p>
                                                    <%
                                                        }
                                                    %>
                                                </table>
                                            </div>
                                            <div class="checkout-total-block">
                                                <ul>
                                                    <li>
                                                        <em>Sub total</em>
                                                        <strong class="price"><span>$</span>47.00</strong>
                                                    </li>
                                                    <li>
                                                        <em>Shipping cost</em>
                                                        <strong class="price"><span>$</span>3.00</strong>
                                                    </li>
                                                    <li>
                                                        <em>Eco Tax (-2.00)</em>
                                                        <strong class="price"><span>$</span>3.00</strong>
                                                    </li>
                                                    <li>
                                                        <em>VAT (17.5%)</em>
                                                        <strong class="price"><span>$</span>3.00</strong>
                                                    </li>
                                                    <li class="checkout-total-price">
                                                        <em>Total</em>
                                                        <strong class="price"><span>$</span>56.00</strong>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div class="clearfix"></div>
                                            <button class="btn btn-primary pull-right" type="submit" id="button-confirm">Confirm Order</button>
                                            <button type="button" class="btn btn-default pull-right margin-right-20">Cancel</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- END CONFIRM -->
                        </div>
                        <!-- END CHECKOUT PAGE -->
                    </div>
                    <!-- END CONTENT -->
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
        </script>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    $(document).ready(function() {
        // Xử lý sự kiện khi nhấn nút "Confirm Order"
        $('#button-confirm').on('click', function() {
            // Thu thập dữ liệu từ form
            var firstname = $('#firstname-dd').val();
            var lastname = $('#lastname-dd').val();
            var email = $('#email-dd').val();
            var phone = $('#phone-dd').val();
            var gender = $('#gender-dd').val();
            var address = $('#address-dd').val();
            var comment = $('#comment-dd').val();
            var paymentMethod = $('input[name="CashOnDelivery"]:checked').val();

            // Thu thập thông tin sản phẩm từ bảng
            var products = [];
            $('table tr').each(function() {
                var productId = $(this).find('.product-quantity').data('product-id');
                var quantity = $(this).find('.product-quantity').val();
                if (productId && quantity) {
                    products.push({
                        productId: parseInt(productId), // Chuyển sang số
                        quantity: parseInt(quantity)   // Chuyển sang số
                    });
                }
            });
            
            // Tạo đối tượng dữ liệu để gửi đi
            var data = {
                firstname: firstname,
                lastname: lastname,
                email: email,
                phone: phone,
                gender: gender,
                address: address,
                comment: comment,
                paymentMethod: paymentMethod,
                products: products
            };

            // Gửi dữ liệu bằng AJAX
            console.log(JSON.stringify(data)); // Kiểm tra dữ liệu trước khi gửi

            $.ajax({
                url: '<%=request.getContextPath()%>/ConfirmOrder',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function(response) {
                    console.log(response); // In phản hồi từ server
                    window.location.href = '<%=request.getContextPath()%>/CartCompletion';
                },
                error: function(xhr, status, error) {
                    console.error(error); // In lỗi chi tiết
                    alert('An error occurred while processing your request: ' + error);
                }
            });
        });
    });
</script>

        <!-- END PAGE LEVEL JAVASCRIPTS -->
    </body>
    <!-- END BODY -->
</html>