<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.OrdersDAO"%>
<%@page import="dao.OrderDetailsDAO"%>
<%@page import="model.Orders"%>
<%@page import="model.OrderDetails"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Theo dõi đơn hàng</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link rel="stylesheet" href="assets/css/reset.css" />
        <link rel="stylesheet" href="assets/css/base.css" />
        <link rel="stylesheet" href="assets/css/main_PC.css" />
        <link rel="stylesheet" href="assets/css/main_Tablet.css" />
        <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
        <link rel="stylesheet" href="https://unpkg.com/swiper@8/swiper-bundle.min.css" />
        <link rel="stylesheet" href="assets/fonts/fontawesome-free-6.0.0-web/css/all.css" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swapsubset=vietnamese" />

        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">

        <!-- Bootstrap -->
        <link type="text/css" rel="stylesheet" href="TulasCSS/css/bootstrap.min.css"/>

        <!-- Slick -->
        <link type="text/css" rel="stylesheet" href="TulasCSS/css/slick.css"/>
        <link type="text/css" rel="stylesheet" href="TulasCSS/css/slick-theme.css"/>

        <!-- nouislider -->
        <link type="text/css" rel="stylesheet" href="TulasCSS/css/nouislider.min.css"/>

        <!-- Font Awesome Icon -->
        <link rel="stylesheet" href="TulasCSS/css/font-awesome.min.css">

        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="TulasCSS/css/style.css"/>
</head>
<body class="ecommerce">
    <%@ include file="./Public/header.jsp" %>
    <div class="main">
        <div class="container">
            <h1>Theo dõi đơn hàng</h1>
            <div class="row margin-bottom-40">
                <div class="col-md-12 col-sm-12">
                    <!-- Form nhập mã đơn hàng hoặc mã vận đơn -->
                    <form action="OrderTrackingController" method="POST" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-md-3 control-label">Mã đơn hàng hoặc mã vận đơn:</label>
                            <div class="col-md-6">
                                <input type="text" name="trackingCode" class="form-control" placeholder="Nhập mã đơn hàng hoặc mã vận đơn" required>
                            </div>
                            <div class="col-md-3">
                                <button type="submit" class="btn btn-primary">Tra cứu <i class="fa fa-search"></i></button>
                            </div>
                        </div>
                    </form>

                    <!-- Hiển thị thông tin đơn hàng nếu có -->
                    <%
                        Orders order = (Orders) request.getAttribute("order");
                        List<OrderDetails> orderDetailsList = (List<OrderDetails>) request.getAttribute("orderDetails");
                        if (order != null) {
                            NumberFormat vnCurrencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                            vnCurrencyFormat.setMaximumFractionDigits(0);
                    %>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Thông tin đơn hàng #<%=order.getOrderID()%></h3>
                        </div>
                        <div class="panel-body">
                            <p><strong>Mã đơn hàng:</strong> <%=order.getOrderID()%></p>                         
                            <p><strong>Ngày đặt hàng:</strong> <%=order.getOrderDate()%></p>
                            <p><strong>Địa chỉ giao hàng:</strong> <%=order.getDeliveryAddress()%></p>
                            <p><strong>Trạng thái:</strong> <%=order.getStatus()%></p>
                            <p><strong>Tổng tiền:</strong> <%=vnCurrencyFormat.format(order.getTotalAmount())%> VNĐ</p>
                        </div>
                    </div>

                    <!-- Hiển thị chi tiết đơn hàng -->
                    <div class="table-wrapper-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Hình ảnh</th>
                                    <th>Tên sản phẩm</th>
                                    <th>Mô tả</th>
                                    <th>Số lượng</th>
                                    <th>Đơn giá</th>
                                    <th>Thành tiền</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
                                        for (OrderDetails od : orderDetailsList) {
                                %>
                                <tr>
                                    <td><img src="<%=od.getImgProduct()%>" width="50" alt="<%=od.getNameProduct()%>"></td>
                                    <td><%=od.getNameProduct()%></td>
                                    <td><%=od.getDesProduct()%></td>
                                    <td><%=od.getQuantity()%></td>
                                    <td><%=vnCurrencyFormat.format(od.getPrice())%> VNĐ</td>
                                    <td><%=vnCurrencyFormat.format(od.getPrice() * od.getQuantity())%> VNĐ</td>
                                </tr>
                                <%
                                        }
                                    } else {
                                %>
                                <tr>
                                    <td colspan="6">Không có chi tiết đơn hàng.</td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                    <%
                        } else if (request.getAttribute("error") != null) {
                    %>
                    <div class="alert alert-danger">
                        <%=request.getAttribute("error")%>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="./Public/footer.jsp" %>

    <!-- JavaScript -->
    <script src="TulasCSS/assets/plugins/jquery.min.js" type="text/javascript"></script>
    <script src="TulasCSS/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="TulasCSS/assets/corporate/scripts/layout.js" type="text/javascript"></script>
    <script type="text/javascript">
        jQuery(document).ready(function() {
            Layout.init();
        });
    </script>
</body>
</html>