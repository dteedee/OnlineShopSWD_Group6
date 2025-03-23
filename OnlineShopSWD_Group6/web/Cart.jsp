<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.*"%>
<%@page import="model.*"%>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<!DOCTYPE html>
<html>
    <head>
  <meta charset="utf-8">
  <title>Giỏ Hàng</title>

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

        

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
            function doDelete(id) {
                if (confirm("Bạn có chắc chắn muốn xóa ?")) {
                    window.location = 'DeleteCartItem?id=' + id;
                }
            }
        </script>
</head>
    
    <body class="ecommerce">
    <%@ include file="./Public/header.jsp" %>
    <div class="main">
      <div class="container">
          <%
                // Định dạng tiền tệ Việt Nam
                NumberFormat vnCurrencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                vnCurrencyFormat.setMaximumFractionDigits(0); // Không hiển thị phần thập phân
          %>
        <!-- BEGIN SIDEBAR & CONTENT -->
        <div class="row margin-bottom-40">
          <!-- BEGIN CONTENT -->
          <div class="col-md-12 col-sm-12">
            <h1>Shopping cart</h1>
            <div class="goods-page">
              <div class="goods-data clearfix">
                <div class="table-wrapper-responsive">
                <table summary="Shopping cart">
                  <tr>
                    <th class="goods-page-image">Chọn</th> <!-- Thêm cột checkbox -->
                    <th class="goods-page-image">Hình ảnh</th>
                    <th class="goods-page-description">Mô tả sản phẩm</th>
                    <th class="goods-page-quantity">Số lượng</th>
                    <th class="goods-page-price">Đơn giá</th>
                    <th class="goods-page-total" colspan="2">Thành giá</th>
                  </tr>
                  <%
                      Map<Integer, CartItems> list = (Map<Integer, CartItems>) request.getAttribute("list");
                      Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
                      ProductsDAO pDAO = new ProductsDAO();
                        for (int id : list.keySet()) {
                            CartItems ci = list.get(id);
                            Products p = pDAO.getProductByID(ci.getProductID());
                   %>     
                  <tr>
                    <td class="goods-page-image">
                        <input type="checkbox" name="selectedItems" value="<%=ci.getCartItemID()%>" checked />
                    </td>
                    <td class="goods-page-image">
                        <a href="/OnlineShopSWD_Group6/ProductDetailController?id=<%=p.getProductID()%>"><img src="<%=p.getImageLink()%>"></a>
                    </td>
                    <td class="goods-page-description">
                      <h3><a href="javascript:;">Cool green dress with red bell</a></h3>
                      <p><strong><%=p.getProductName()%></strong></p>
                      <em><%=p.getDescription()%></em>
                    </td>
                    <td class="goods-page-quantity">
                        <div class="product-quantity">
                          <button class="btn btn-decrease" data-cart-item-id="<%=ci.getCartItemID()%>">-</button>
                          <input id="product-quantity-<%=ci.getCartItemID()%>" type="text" value="<%=ci.getQuantity()%>" class="form-control input-sm quantity-input" readonly>
                          <input id="product-quantity1-<%=ci.getProductID()%>" value="<%=ci.getQuantity()%>" hidden>
                          <button class="btn btn-increase" data-cart-item-id="<%=ci.getCartItemID()%>">+</button>
                        </div>
                    </td>
                    <td class="goods-page-price">
                        <strong><span id="product-price-<%=ci.getCartItemID()%>"><%=p.getPriceFormat()%></span></strong>
                      </td>
                      <td class="goods-page-total">
                        <strong><span id="product-total-<%=ci.getCartItemID()%>"><%=p.getPrice() * ci.getQuantity()%></span></strong>
                    </td>
                    <td class="del-goods-col">
                      <a class="del-goods" onclick="doDelete('<%=ci.getCartItemID()%>')">&nbsp;</a>
                    </td>
                  </tr>
                  <%
                        }
                  %>
                </table>
                </div>

                <div class="shopping-total">
                    <ul>
                      <li>
                        <em>Tổng giá trị</em>
                        <strong class="price"><span id="cart-total"></span></strong>
                      </li>
                    </ul>
                </div>
              </div>
                <a href="/OnlineShopSWD_Group6/home"><button class="btn btn-default" type="submit">Continue shopping <i class="fa fa-shopping-cart"></i></button></a>
                <form id="checkoutForm" action="ConfirmOrder" method="POST">
                <input type="hidden" name="selectedItems" id="selectedItemsInput" />
                <button class="btn btn-primary" id="checkoutButton">Checkout <i class="fa fa-check"></i></button>
                </form>
            </div>
          </div>
          <!-- END CONTENT -->
        </div>
        <!-- END SIDEBAR & CONTENT -->

        <!-- BEGIN SIMILAR PRODUCTS -->
        <div class="row margin-bottom-40">
          <div class="col-md-12 col-sm-12">
            <h2>Most popular products</h2>
            <div class="owl-carousel owl-carousel4">
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="assets/pages/img/products/k1.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="assets/pages/img/products/k1.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                  <div class="sticker sticker-new"></div>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="assets/pages/img/products/k2.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="assets/pages/img/products/k2.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress2</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="assets/pages/img/products/k3.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="assets/pages/img/products/k3.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress3</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="assets/pages/img/products/k4.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="assets/pages/img/products/k4.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress4</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                  <div class="sticker sticker-sale"></div>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="assets/pages/img/products/k1.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="assets/pages/img/products/k1.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress5</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
              <div>
                <div class="product-item">
                  <div class="pi-img-wrapper">
                    <img src="assets/pages/img/products/k2.jpg" class="img-responsive" alt="Berry Lace Dress">
                    <div>
                      <a href="assets/pages/img/products/k2.jpg" class="btn btn-default fancybox-button">Zoom</a>
                      <a href="#product-pop-up" class="btn btn-default fancybox-fast-view">View</a>
                    </div>
                  </div>
                  <h3><a href="shop-item.html">Berry Lace Dress6</a></h3>
                  <div class="pi-price">$29.00</div>
                  <a href="javascript:;" class="btn btn-default add2cart">Add to cart</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- END SIMILAR PRODUCTS -->
      </div>
    </div>
    <%@ include file="./Public/footer.jsp" %>

    <!-- Load javascripts at bottom, this will reduce page load time -->
    <!-- BEGIN CORE PLUGINS (REQUIRED FOR ALL PAGES) -->
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
        jQuery(document).ready(function() {
            Layout.init();    
            Layout.initOWL();
            Layout.initTwitter();
            Layout.initImageZoom();
            Layout.initTouchspin();
            Layout.initUniform();
            Layout.initSliderRange();
        });
    </script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    $(document).ready(function() {
        $('#checkoutButton').on('click', function() {
            var selectedItems = []; // Mảng để lưu các sản phẩm đã chọn

            // Lặp qua các sản phẩm đã chọn
            $('input[name="selectedItems"]:checked').each(function() {
                var productID = $(this).val(); // Lấy productID
                var quantity = parseInt($('#product-quantity1-' + productID).val()); // Lấy số lượng

                // Thêm sản phẩm vào mảng
                selectedItems.push({
                    productID: productID,
                    quantity: quantity
                });
            });

            // Kiểm tra nếu có ít nhất một sản phẩm được chọn
            if (selectedItems.length > 0) {
                // Chuyển đổi mảng thành JSON và gán vào input hidden
                $('#selectedItemsInput').val(JSON.stringify(selectedItems));

                // Gửi form
                console.log(productID);
                $('#checkoutForm').submit();
            } else {
                alert('Vui lòng chọn ít nhất một sản phẩm.');
            }
        });
    });
    </script>
<script>
    $(document).ready(function() {
        // Hàm chuyển đổi chuỗi tiền tệ thành số
        function parseCurrency(currencyString) {
            return parseFloat(currencyString.replace(/[^0-9]/g, ""));
        }

        // Hàm định dạng số thành chuỗi tiền tệ Việt Nam
        function formatCurrency(amount) {
            return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
        }

        // Định dạng giá tổng ban đầu
        $('span[id^="product-total-"]').each(function() {
            var totalText = $(this).text();
            var totalPrice = parseFloat(totalText.replace(/[^0-9.-]+/g, ""));
            $(this).text(formatCurrency(totalPrice));
        });

        // Hàm cập nhật giá tổng của sản phẩm
        function updateProductTotal(cartItemId) {
            var quantity = parseInt($('#product-quantity-' + cartItemId).val());
            var priceText = $('#product-price-' + cartItemId).text();
            var price = parseCurrency(priceText);
            var totalPrice = price * quantity;
            $('#product-total-' + cartItemId).text(formatCurrency(totalPrice));
        }

        // Hàm cập nhật tổng giá trị giỏ hàng
        function updateCartTotal() {
            var total = 0;
            $('input[name="selectedItems"]:checked').each(function() {
                var cartItemId = $(this).val(); // Lấy cartItemID của sản phẩm được checked
                var totalText = $('#product-total-' + cartItemId).text(); // Lấy giá tổng của sản phẩm
                var totalPrice = parseCurrency(totalText); // Chuyển đổi giá tổng thành số
                total += totalPrice; // Cộng dồn vào tổng giá trị giỏ hàng
                console.log(cartItemId);
            });
            $('#cart-total').text(formatCurrency(total)); // Cập nhật tổng giá trị giỏ hàng lên giao diện
        }

        // Cập nhật tổng giá trị giỏ hàng khi checkbox thay đổi
        $('input[name="selectedItems"]').on('change', function() {
            updateCartTotal();
        });

        // Cập nhật tổng giá trị giỏ hàng ban đầu
        updateCartTotal();

        // Xử lý nút tăng số lượng
        $('.btn-increase').on('click', function() {
            var cartItemId = $(this).data('cart-item-id');
            var quantityInput = $('#product-quantity-' + cartItemId);
            var hiddenQuantityInput = $('#product-quantity1-' + cartItemId); // Thẻ ẩn
            var currentQuantity = parseInt(quantityInput.val());
            quantityInput.val(currentQuantity + 1);
            hiddenQuantityInput.val(currentQuantity + 1); // Đồng bộ giá trị thẻ ẩn
            updateProductTotal(cartItemId);
            updateCartTotal();
            updateCartOnServer(cartItemId, currentQuantity + 1); // Gửi giá trị mới đến backend
        });

        // Xử lý nút giảm số lượng
        $('.btn-decrease').on('click', function() {
            var cartItemId = $(this).data('cart-item-id');
            var quantityInput = $('#product-quantity-' + cartItemId);
            var hiddenQuantityInput = $('#product-quantity1-' + cartItemId); // Thẻ ẩn
            var currentQuantity = parseInt(quantityInput.val());
            if (currentQuantity > 1) {
                quantityInput.val(currentQuantity - 1);
                hiddenQuantityInput.val(currentQuantity - 1); // Đồng bộ giá trị thẻ ẩn
                updateProductTotal(cartItemId);
                updateCartTotal();
                updateCartOnServer(cartItemId, currentQuantity - 1); // Gửi giá trị mới đến backend
            }
        });

        // Hàm gửi yêu cầu cập nhật giỏ hàng lên server
        function updateCartOnServer(cartItemId, quantity) {
            console.log("Sending data to server - cartItemId:", cartItemId, "quantity:", quantity); // Kiểm tra dữ liệu gửi đi
            $.ajax({
                url: '<%=request.getContextPath()%>/UpdateCartController',
                type: 'POST',
                data: {
                    cartItemId: cartItemId,
                    quantity: quantity
                },
                success: function(response) {
                    if (response === "success") {
                        console.log("Cập nhật giỏ hàng thành công!");
                    } else {
                        console.log("Có lỗi xảy ra khi cập nhật giỏ hàng.");
                    }
                },
                error: function() {
                    console.log("Lỗi kết nối đến server.");
                }
            });
        }
    });
</script>
    <!-- END PAGE LEVEL JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>
