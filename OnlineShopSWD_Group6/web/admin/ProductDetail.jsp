<%-- 
Document   : HomePage
Created on : Jan 18, 2025, 12:47:06 AM
Author     : Tung Duong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.*"%>
<%@page import="model.*"%>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="com.google.gson.Gson"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

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

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>
    <body>
        
        <%
            UsersDAO uDAO = new UsersDAO();
            ProductsDAO pDAO = new ProductsDAO();
            List<Category> categories = pDAO.getAllCategories();
            request.setAttribute("categories", categories);
            String categoriesJson = new Gson().toJson(categories);
            Products p = (Products) request.getAttribute("product");
        %>
        <!-- SECTION -->
        <div class="section">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">
                    <!-- Product main img -->
                    <div class="col-md-5 col-md-push-2">
                        <div id="product-main-img">
                            <div class="product-preview">
                                <img src="<%= p.getImageLink() %>" alt="">
                            </div>

                            <div class="product-preview">
                                <img src="<%= p.getImageLink() %>" alt="">
                            </div>

                            <div class="product-preview">
                                <img src="<%= p.getImageLink() %>" alt="">
                            </div>

                            <div class="product-preview">
                                <img src="<%= p.getImageLink() %>" alt="">
                            </div>
                        </div>
                    </div>
                    <!-- /Product main img -->

                    <!-- Product thumb imgs -->
                    <div class="col-md-2  col-md-pull-5">
                        <div id="product-imgs">
                            <div class="product-preview">
                                <img src="<%= p.getImageLink() %>" alt="">
                            </div>

                            <div class="product-preview">
                                <img src="<%= p.getImageLink() %>" alt="">
                            </div>

                            <div class="product-preview">
                                <img src="<%= p.getImageLink() %>" alt="">
                            </div>

                            <div class="product-preview">
                                <img src="<%= p.getImageLink() %>" alt="">
                            </div>
                        </div>
                    </div>
                    <!-- /Product thumb imgs -->

                    <!-- Product details -->
                    <div class="col-md-5">
                        <div class="product-details">
                            <div class="switch-container">
                                <input type="hidden" id="product-id" value="<%= p.getProductID() %>">
                                <input type="hidden" id="product-img" value="<%= p.getImageLink() %>">
                                <input type="hidden" id="product-pro" value="<%= p.getIsPromoted() %>">
                                <h2 id="product-name"><%= p.getProductName() %></h2>
                                <label class="switch">
                                    <input type="checkbox" id="edit-toggle">
                                    <span class="slider"></span>
                                </label>
                                <span class="switch-label">Chế độ chỉnh sửa</span>
                            </div>
                            
                            <div>
                                <h3 class="product-price"><%= p.getPriceFormat() %></h3><del class="product-old-price"> <%= p.getOldPriceFormat()%></del>

                                <span class="product-available">Còn Hàng</span>
                            </div>
                            <p id="Description"><%= p.getDescription() %></p>

                            <div class="add-to-cart">
                                <div class="qty-label">
                                    Số lượng đặt
                                    <div class="input-number">
                                        <input type="number" id="quantity-input" value="1" min="1">
                                        <span class="qty-up">+</span>
                                        <span class="qty-down">-</span>
                                    </div>
                                </div>
                                <button class="add-to-cart-btn" id="add-to-cart-btn">
                                    <i class="fa fa-shopping-cart"></i> Add to cart
                                </button>
                            </div>

                            <ul class="product-btns">
                                <li><a href="#"><i class="fa fa-heart-o"></i> add to wishlist</a></li>
                            </ul>

                            <ul class="product-links">
                                <li>Danh mục:</li>
                                <li><a href="#" id="Category"><%= pDAO.GetCategorybyID(p.getCategoryID()).getCategoryName() %></a></li>
                            </ul>
                            <ul class="product-links">
                                <li>Số lượng tồn kho:</li>
                                <li><a href="#" id="Amount"><%= p.getAmount() %></a></li>
                            </ul>
                            <ul class="product-links">
                                <li>Thời gian bảo hành:</li>
                                <li><a href="#" id="WarrantyPeriod"><%= p.getWarrantyPeriod() %></a></li>
                            </ul>
                            <ul class="product-links">
                                <li>Nhà cung cấp:</li>
                                <li><a href="#" id="Provider"><%= p.getProvider() %></a></li>
                            </ul>
                            <button id="save-button" style="display:none;">Lưu</button>

                            <ul class="product-links">
                                <li>Share:</li>
                                <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                                <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                                <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                                <li><a href="#"><i class="fa fa-envelope"></i></a></li>
                            </ul>

                        </div>
                    </div>
                    <!-- /Product details -->

                    <!-- Product tab -->
                    <div class="col-md-12">
                        <div id="product-tab">
                            <!-- product tab nav -->
                            <ul class="tab-nav">
                                <li class="active"><a data-toggle="tab" href="#tab1">Description</a></li>
                                <li><a data-toggle="tab" href="#tab2">Details</a></li>
                            </ul>
                            <!-- /product tab nav -->

                            <!-- product tab content -->
                            <div class="tab-content">
                                <!-- tab1  -->
                                <div id="tab1" class="tab-pane fade in active">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <p><%= p.getDescription() %></p>
                                        </div>
                                    </div>
                                </div>
                                <!-- /tab1  -->

                                <!-- tab2  -->
                                <div id="tab2" class="tab-pane fade in">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                        </div>
                                    </div>
                                </div>
                                <!-- /tab2  -->

                            </div>
                            <!-- /product tab content  -->
                        </div>
                    </div>
                    <!-- /product tab -->
                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /SECTION -->

        <!-- Section -->
        <div class="section">
            <!-- container -->
            <div class="container">
                <!-- row -->
                <div class="row">

                    <div class="col-md-12">
                        <div class="section-title text-center">
                            <h3 class="title">Related Products</h3>
                        </div>
                    </div>

                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <img src="./img/product01.png" alt="">
                                <div class="product-label">
                                    <span class="sale">-30%</span>
                                </div>
                            </div>
                            <div class="product-body">
                                <p class="product-category">Category</p>
                                <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                <div class="product-rating">
                                </div>
                                <div class="product-btns">
                                    <button class="add-to-wishlist"><i class="fa fa-heart-o"></i><span class="tooltipp">add to wishlist</span></button>
                                    <button class="add-to-compare"><i class="fa fa-exchange"></i><span class="tooltipp">add to compare</span></button>
                                    <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick view</span></button>
                                </div>
                            </div>
                            <div class="add-to-cart">
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                            </div>
                        </div>
                    </div>
                    <!-- /product -->

                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <img src="./img/product02.png" alt="">
                                <div class="product-label">
                                    <span class="new">NEW</span>
                                </div>
                            </div>
                            <div class="product-body">
                                <p class="product-category">Category</p>
                                <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                <div class="product-rating">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                </div>
                                <div class="product-btns">
                                    <button class="add-to-wishlist"><i class="fa fa-heart-o"></i><span class="tooltipp">add to wishlist</span></button>
                                    <button class="add-to-compare"><i class="fa fa-exchange"></i><span class="tooltipp">add to compare</span></button>
                                    <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick view</span></button>
                                </div>
                            </div>
                            <div class="add-to-cart">
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                            </div>
                        </div>
                    </div>
                    <!-- /product -->

                    <div class="clearfix visible-sm visible-xs"></div>

                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <img src="./img/product03.png" alt="">
                            </div>
                            <div class="product-body">
                                <p class="product-category">Category</p>
                                <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                <div class="product-rating">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star-o"></i>
                                </div>
                                <div class="product-btns">
                                    <button class="add-to-wishlist"><i class="fa fa-heart-o"></i><span class="tooltipp">add to wishlist</span></button>
                                    <button class="add-to-compare"><i class="fa fa-exchange"></i><span class="tooltipp">add to compare</span></button>
                                    <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick view</span></button>
                                </div>
                            </div>
                            <div class="add-to-cart">
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                            </div>
                        </div>
                    </div>
                    <!-- /product -->

                    <!-- product -->
                    <div class="col-md-3 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <img src="./img/product04.png" alt="">
                            </div>
                            <div class="product-body">
                                <p class="product-category">Category</p>
                                <h3 class="product-name"><a href="#">product name goes here</a></h3>
                                <h4 class="product-price">$980.00 <del class="product-old-price">$990.00</del></h4>
                                <div class="product-rating">
                                </div>
                                <div class="product-btns">
                                    <button class="add-to-wishlist"><i class="fa fa-heart-o"></i><span class="tooltipp">add to wishlist</span></button>
                                    <button class="add-to-compare"><i class="fa fa-exchange"></i><span class="tooltipp">add to compare</span></button>
                                    <button class="quick-view"><i class="fa fa-eye"></i><span class="tooltipp">quick view</span></button>
                                </div>
                            </div>
                            <div class="add-to-cart">
                                <button class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> add to cart</button>
                            </div>
                        </div>
                    </div>
                    <!-- /product -->

                </div>
                <!-- /row -->
            </div>
            <!-- /container -->
        </div>
        <!-- /Section -->


        <!-- jQuery Plugins -->
        <script src="TulasCSS/js/jquery.min.js"></script>
        <script src="TulasCSS/js/bootstrap.min.js"></script>
        <script src="TulasCSS/js/slick.min.js"></script>
        <script src="TulasCSS/js/nouislider.min.js"></script>
        <script src="TulasCSS/js/jquery.zoom.min.js"></script>
        <script src="TulasCSS/js/main.js"></script>

    </body>

    <script>
        document.getElementById('add-to-cart-btn').addEventListener('click', function () {
            // Lấy productId và số lượng
            const productId = document.getElementById('product-id').value;
            const quantity = document.getElementById('quantity-input').value;

            // Kiểm tra số lượng hợp lệ
            if (quantity < 1) {
                alert('Số lượng phải lớn hơn 0');
                return;
            }

            // Gửi dữ liệu lên server bằng AJAX
            $.ajax({
                url: '<%=request.getContextPath()%>/add-to-cart',
                method: 'POST',
                data: {
                    productId: productId,
                    quantity: quantity
                },
                success: function (data) {
                    if (data.success) {
                        alert('Đã thêm sản phẩm vào giỏ hàng!');
                    } else {
                        alert('Lỗi: ' + data.error);
                    }
                },
                error: function (xhr, status, error) {
                    console.error('AJAX Error:', error);
                    alert('Có lỗi xảy ra khi thêm vào giỏ hàng');
                }
            });
        });
    </script>

    <script>
        document.getElementById('save-button').addEventListener('click', function () {
            var newProductId = document.getElementById('product-id').value;
            var newProductImg = document.getElementById('product-img').value;
            var newProductPro = document.getElementById('product-pro').checked;
            var newProductName = document.getElementById('product-name-input').value;
            var newProductPrice = document.getElementById('product-new-price-input').value;
            var newProductOldPrice = document.getElementById('product-old-price-input').value;
            var newProductDescription = document.getElementById('product-description-input').value;
            var newProductCategory = document.getElementById('product-category-input').value;
            var newProductAmount = document.getElementById('product-amount-input').value;
            var newProductWarranty = document.getElementById('product-warranty-input').value;
            var newProductProvider = document.getElementById('product-provider-input').value;

            // Tạo đối tượng chứa tất cả các giá trị cần cập nhật
            var productData = {
                productId: newProductId,
                newProductImg: newProductImg,
                newProductPro: newProductPro,
                productName: newProductName,
                productPrice: newProductPrice,
                productOldPrice: newProductOldPrice,
                productDescription: newProductDescription,
                productCategory: newProductCategory,
                productAmount: newProductAmount,
                productWarranty: newProductWarranty,
                productProvider: newProductProvider
            };

            // Debug: In ra dữ liệu sản phẩm để kiểm tra
            console.log('Product Data:', productData);

            // Gửi yêu cầu AJAX để cập nhật thông tin sản phẩm
            $.ajax({
                url: '<%=request.getContextPath()%>/update-product',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(productData),
                success: function (data) {
                    if (data.success) {
                        alert('Thông tin sản phẩm đã được cập nhật thành công');
                        // Tắt chế độ chỉnh sửa
                        document.getElementById('edit-toggle').checked = false;

                        // Kích hoạt sự kiện change để khôi phục giao diện
                        document.getElementById('edit-toggle').dispatchEvent(new Event('change'));
                    } else {
                        alert('Có lỗi xảy ra khi cập nhật thông tin sản phẩm' + data.error);
                        console.error(data.error);  // Log lỗi trả về từ server
                    }
                },
                error: function (xhr, status, error) {
                    console.error('AJAX Error:', error);  // Log lỗi AJAX nếu có
                    alert('Có lỗi xảy ra khi gửi yêu cầu cập nhật');
                }
            });
        });




    </script>

    <script>
        document.getElementById('edit-toggle').addEventListener('change', function () {
            const productNameElement = document.getElementById('product-name');
            const productNewPriceElement = document.querySelector('.product-price');
            const productOldPriceElement = document.querySelector('.product-old-price');
            const productDescriptionElement = document.getElementById('Description');
            const productCategoryElement = document.getElementById('Category');
            const productAmountElement = document.getElementById('Amount');
            const productWarrantyElement = document.getElementById('WarrantyPeriod');
            const productProviderElement = document.getElementById('Provider');
            var saveButton = document.getElementById('save-button');

            if (this.checked) {
                // Chuyển đổi Product Name
                const nameInput = document.createElement('input');
                nameInput.type = 'text';
                nameInput.value = productNameElement.textContent;
                nameInput.className = 'edit-mode-input';
                nameInput.id = 'product-name-input';
                productNameElement.parentNode.replaceChild(nameInput, productNameElement);

                // Chuyển đổi New Price
                const newPriceInput = document.createElement('input');
                newPriceInput.type = 'text';
                newPriceInput.value = productNewPriceElement.textContent.replace(/\D/g, '');
                newPriceInput.className = 'edit-mode-input';
                newPriceInput.id = 'product-new-price-input';
                productNewPriceElement.parentNode.replaceChild(newPriceInput, productNewPriceElement);

                // Chuyển đổi Old Price
                const oldPriceInput = document.createElement('input');
                oldPriceInput.type = 'text';
                oldPriceInput.value = productOldPriceElement.textContent.replace(/\D/g, '');
                oldPriceInput.className = 'edit-mode-input';
                oldPriceInput.id = 'product-old-price-input';
                productOldPriceElement.parentNode.replaceChild(oldPriceInput, productOldPriceElement);

                // Chuyển đổi Description
                const descriptionInput = document.createElement('textarea');
                descriptionInput.value = productDescriptionElement.textContent;
                descriptionInput.className = 'edit-mode-input';
                descriptionInput.id = 'product-description-input';
                productDescriptionElement.parentNode.replaceChild(descriptionInput, productDescriptionElement);

                window.categories = <%= categoriesJson %>;
                // Chuyển đổi Danh mục
                const categorySelect = document.createElement('select');
                categorySelect.className = 'edit-mode-input';
                categorySelect.id = 'product-category-input';

                // Lấy giá trị category hiện tại của sản phẩm
                const currentCategoryName = productCategoryElement.textContent.trim();

                // Lặp qua danh sách categories từ biến JavaScript
                window.categories.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.categoryID; // Sử dụng CategoryID làm giá trị
                    option.textContent = category.categoryName; // Hiển thị CategoryName

                    // So sánh tên category để chọn mục hiện tại
                    if (category.categoryName === currentCategoryName) {
                        option.selected = true;
                    }
                    categorySelect.appendChild(option);
                });


                productCategoryElement.parentNode.replaceChild(categorySelect, productCategoryElement);

                // Chuyển đổi Số lượng tồn kho
                const amountInput = document.createElement('input');
                amountInput.type = 'number';
                amountInput.value = productAmountElement.textContent;
                amountInput.className = 'edit-mode-input';
                amountInput.id = 'product-amount-input';
                productAmountElement.parentNode.replaceChild(amountInput, productAmountElement);

                // Chuyển đổi Thời gian bảo hành
                const warrantyInput = document.createElement('input');
                warrantyInput.type = 'text';
                warrantyInput.value = productWarrantyElement.textContent;
                warrantyInput.className = 'edit-mode-input';
                warrantyInput.id = 'product-warranty-input';
                productWarrantyElement.parentNode.replaceChild(warrantyInput, productWarrantyElement);

                const ProviderInput = document.createElement('input');
                ProviderInput.type = 'text';
                ProviderInput.value = productProviderElement.textContent;
                ProviderInput.className = 'edit-mode-input';
                ProviderInput.id = 'product-provider-input';
                productProviderElement.parentNode.replaceChild(ProviderInput, productProviderElement);

                saveButton.style.display = 'inline-block';

                // Tự động focus vào trường đầu tiên
                setTimeout(() => nameInput.focus(), 50);
            } else {
                // Khôi phục Product Name
                const nameInput = document.getElementById('product-name-input');
                const nameText = document.createElement('h2');
                nameText.id = 'product-name';
                nameText.textContent = nameInput.value;
                nameInput.parentNode.replaceChild(nameText, nameInput);

                // Khôi phục New Price
                const newPriceInput = document.getElementById('product-new-price-input');
                const newPriceText = document.createElement('h3');
                newPriceText.className = 'product-price';
                newPriceText.textContent = formatCurrency(parseInt(newPriceInput.value.replace(/\D/g, ''))); // Định dạng lại giá trị
                newPriceInput.parentNode.replaceChild(newPriceText, newPriceInput);

                // Khôi phục Old Price
                const oldPriceInput = document.getElementById('product-old-price-input');
                const oldPriceText = document.createElement('del');
                oldPriceText.className = 'product-old-price';
                oldPriceText.textContent = formatCurrency(parseInt(oldPriceInput.value.replace(/\D/g, ''))); // Định dạng lại giá trị
                oldPriceInput.parentNode.replaceChild(oldPriceText, oldPriceInput);

                // Khôi phục Description
                const descriptionInput = document.getElementById('product-description-input');
                const descriptionText = document.createElement('p');
                descriptionText.id = 'Description';
                descriptionText.textContent = descriptionInput.value;
                descriptionInput.parentNode.replaceChild(descriptionText, descriptionInput);

                // Khôi phục Danh mục
                const categorySelect = document.getElementById('product-category-input');
                const categoryText = document.createElement('a');
                categoryText.href = '#';
                categoryText.id = 'Category';
                categoryText.textContent = categorySelect.options[categorySelect.selectedIndex].text;
                categorySelect.parentNode.replaceChild(categoryText, categorySelect);

                // Khôi phục Số lượng tồn kho
                const amountInput = document.getElementById('product-amount-input');
                const amountText = document.createElement('a');
                amountText.href = '#';
                amountText.id = 'Amount';
                amountText.textContent = amountInput.value;
                amountInput.parentNode.replaceChild(amountText, amountInput);

                // Khôi phục Thời gian bảo hành
                const warrantyInput = document.getElementById('product-warranty-input');
                const warrantyText = document.createElement('a');
                warrantyText.href = '#';
                warrantyText.id = 'WarrantyPeriod';
                warrantyText.textContent = warrantyInput.value;
                warrantyInput.parentNode.replaceChild(warrantyText, warrantyInput);

                // Khôi phục Thời gian bảo hành
                const ProviderInput = document.getElementById('product-provider-input');
                const ProviderText = document.createElement('a');
                ProviderText.href = '#';
                ProviderText.id = 'Provider';
                ProviderText.textContent = ProviderInput.value;
                ProviderInput.parentNode.replaceChild(ProviderText, ProviderInput);

                saveButton.style.display = 'none';
            }
        });

        function formatCurrency(value) {
            return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(value);
        }
    </script>
</html>