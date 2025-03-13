<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div id="layoutSidenav_content">
    <jsp:include page="header.jsp"></jsp:include>
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">${title}</h1>
            <c:if test="${not empty sessionScope.notification}">
                <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.notification}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("notification"); %>
            </c:if>
            <c:if test="${not empty sessionScope.notificationErr}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.notificationErr}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("notificationErr");%>
            </c:if>
            <div class="card mb-4">
                <div class="card-body">
                    <table id="datatablesSimple">
                        <div class="container mb-4">
                            <div class="row">
                                <div class="col-md-4">
                                    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addPDModal">
                                        Thêm mới
                                    </button>
                                </div>
                                <form action="${contextPath}/admin/product-management" method="GET" class="row g-3">
                                    <div class="col-md-3">
                                        <label for="categoryFilter" class="form-label">Loại sản phẩm</label>
                                        <select name="category" id="categoryFilter" class="form-select" onchange="this.form.submit()">
                                            <option value="" ${empty param.category ? 'selected' : ''}>Tất cả</option>
                                            <c:forEach items="${listCategory}" var="c">
                                                <option value="${c.categoryID}" ${param.category eq c.categoryID ? 'selected' : ''}>${c.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label for="search" class="form-label">Tìm kiếm</label>
                                        <input type="text" class="form-control" id="search" name="search" value="${param.search}" placeholder="Nhập tiêu đề hoặc mô tả">
                                    </div>
                                </form>
                            </div>
                        </div>
                        <thead>
                            <tr>
                                <th>STT</th>
                                <th>Ảnh</th>
                                <th>Tên sản phẩm <a href="?sort=productName">↑↓</a></th>
                                <th>Loại sản phẩm <a href="?sort=category">↑↓</a></th>
                                <th>Nhà cung cấp</th>
                                <th>Giá <a href="?sort=price">↑↓</a></th>
                                <th>Giá cũ <a href="?sort=oldprice">↑↓</a></th>
                                <th>Bảo hành</th>
                                <th>Số lượng <a href="?sort=amount">↑↓</a></th>
                                <th>Ngày tạo</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${products}" var="p" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td><img src="${contextPath}/${p.imageLink}" width="90" height="60" alt="${p.productName}"/></td>
                                    <td>${p.productName}</td>
                                    <td>${p.category.categoryName}</td>
                                    <td>${p.provider}</td>
                                    <td>${p.getPriceFormat()}</td>
                                    <td>${p.getOldPriceFormat()}</td>
                                    <td>${p.warrantyPeriod}</td>
                                    <td>${p.amount}</td>
                                    <td><fmt:formatDate value="${p.getCreateAtDate()}" pattern="dd/MM/yyyy"/></td>
                                    <td style="width: 150px">
                                        <span style="color: ${p.status eq 'Active' ? 'green' : 'red'}">
                                            ${p.status eq 'Active' ? 'Hoạt động' : 'Không hoạt động'}
                                            <div class="form-check form-switch">
                                                <input class="form-check-input toggle-status" type="checkbox"
                                                       data-bs-toggle="modal" data-bs-target="#confirmToggleModal"
                                                       data-id="${p.productID}" data-status="${p.status}"
                                                       ${p.status eq 'Active' ? 'checked' : ''}>
                                            </div>
                                        </span>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-info btn-sm"
                                                data-bs-toggle="modal" data-bs-target="#editProductModal"
                                                data-id="${p.productID}"
                                                data-name="${p.productName}"
                                                data-category="${p.category.categoryID}"
                                                data-provider="${p.provider}"
                                                data-price="${p.price}"
                                                data-oldprice="${p.oldprice}" 
                                                data-warranty="${p.warrantyPeriod}"
                                                data-amount="${p.amount}"
                                                data-description="${p.description}"
                                                data-image="${contextPath}/${p.imageLink}">
                                            <i class="fa fa-pen" style="color: white"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>
    <!-- Modal Thêm Sản Phẩm -->
    <div class="modal fade" id="addPDModal" tabindex="-1" aria-labelledby="addPDModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${contextPath}/admin/product-management" method="POST" enctype="multipart/form-data" class="needs-validation" novalidate>
                    <input type="hidden" name="action" value="add">
                    <div class="modal-header">
                        <h5 class="modal-title">Thêm Sản Phẩm Mới</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="productName" class="form-label">Tên sản phẩm</label>
                            <input type="text" class="form-control" id="productName" name="productName" required>
                        </div>
                        <div class="mb-3">
                            <label for="categoryID" class="form-label">Loại sản phẩm</label>
                            <select class="form-select" id="categoryID" name="categoryID" required>
                                <option value="">Chọn loại sản phẩm</option>
                                <c:forEach items="${listCategory}" var="c">
                                    <option value="${c.categoryID}">${c.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="provider" class="form-label">Nhà cung cấp</label>
                            <input type="text" class="form-control" id="provider" name="provider" required>
                        </div>
                        <div class="mb-3">
                            <label for="warrantyPeriod" class="form-label">Thời gian bảo hành (Năm)</label> <!-- Sửa ID -->
                            <input type="number" min="1" class="form-control" id="warrantyPeriod" name="warrantyPeriod" required>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Giá</label>
                            <input type="number" min="1" class="form-control" id="price" name="price" required>
                        </div>
                        <div class="mb-3">
                            <label for="oldprice" class="form-label">Giá cũ</label>
                            <input type="number" min="0" class="form-control" id="oldprice" name="oldprice" value="0" required>
                        </div>
                        <div class="mb-3">
                            <label for="amount" class="form-label">Số lượng</label>
                            <input type="number" min="1" class="form-control" id="amount" name="amount" required>
                        </div>
                        <div class="mb-3">
                            <label for="imageFile" class="form-label">Ảnh sản phẩm</label>
                            <input type="file" class="form-control" id="imageFile" name="imageFile" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Mô tả</label>
                            <textarea class="form-control" id="description" name="description" required></textarea> <!-- Sửa ID -->
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-success">Thêm</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Modal Chỉnh sửa sản phẩm -->
    <div class="modal fade" id="editProductModal" tabindex="-1" aria-labelledby="editProductModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${contextPath}/admin/product-management" method="POST" enctype="multipart/form-data" class="needs-validation" novalidate>
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="productID" id="editProductID">
                    <div class="modal-header">
                        <h5 class="modal-title">Chỉnh sửa sản phẩm</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="editProductName" class="form-label">Tên sản phẩm</label>
                            <input type="text" class="form-control" id="editProductName" name="productName" required>
                        </div>
                        <div class="mb-3">
                            <label for="editCategoryID" class="form-label">Loại sản phẩm</label>
                            <select class="form-select" id="editCategoryID" name="categoryID" required>
                                <c:forEach items="${listCategory}" var="c">
                                    <option value="${c.categoryID}">${c.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="editProvider" class="form-label">Nhà cung cấp</label>
                            <input type="text" class="form-control" id="editProvider" name="provider" required>
                        </div>
                        <div class="mb-3">
                            <label for="editWarrantyPeriod" class="form-label">Thời gian bảo hành (Năm)</label>
                            <input type="number" class="form-control" id="editWarrantyPeriod" name="warrantyPeriod" required>
                        </div>
                        <div class="mb-3">
                            <label for="editPrice" class="form-label">Giá</label>
                            <input type="number" class="form-control" id="editPrice" name="price" required>
                        </div>
                        <div class="mb-3">
                            <label for="editOldPrice" class="form-label">Giá cũ</label>
                            <input type="number" class="form-control" id="editOldPrice" name="oldprice" required>
                        </div>
                        <div class="mb-3">
                            <label for="editAmount" class="form-label">Số lượng</label>
                            <input type="number" class="form-control" id="editAmount" name="amount" required>
                        </div>
                        <div class="mb-3 text-center">
                            <label class="form-label">Ảnh hiện tại</label>
                            <img id="editCurrentImage" src="" class="img-fluid rounded" width="150" alt="Current Image">
                        </div>
                        <div class="mb-3">
                            <label for="editImageFile" class="form-label">Tải ảnh mới</label>
                            <input type="file" class="form-control" id="editImageFile" name="imageFile">
                        </div>
                        <div class="mb-3">
                            <label for="editDescription" class="form-label">Mô tả</label>
                            <textarea class="form-control" id="editDescription" name="description"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="confirmToggleModal" tabindex="-1" aria-labelledby="confirmToggleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${contextPath}/admin/product-management" method="POST">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmToggleModalLabel">Xác nhận thay đổi trạng thái</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="productID" id="toggleProductID">
                        <input type="hidden" name="action" value="toggleStatus">
                        <input type="hidden" name="status" id="toggleStatusValue">
                        <p id="toggleMessage"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-warning">Xác nhận</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var editRoleModal = document.getElementById('editRoleModal');
            editRoleModal.addEventListener('show.bs.modal', function (event) {
                // Get the button that triggered the modal
                var button = event.relatedTarget;
                // Extract the data attributes
                var userId = button.getAttribute('data-userid');
                var userName = button.getAttribute('data-username');
                var currentRole = button.getAttribute('data-currentrole');

                // Update the modal's content.
                var modalTitle = editRoleModal.querySelector('.modal-title');
                var hiddenInput = editRoleModal.querySelector('#editUserId');
                var roleSelect = editRoleModal.querySelector('#roleSelect');

                modalTitle.textContent = 'Edit Role for User  ' + userName;
                hiddenInput.value = userId;
                roleSelect.value = currentRole; // pre-select the current role
            });
        });
    </script>
    <script>
        // Bootstrap 5 custom form validation
        (function () {
            'use strict';
            var forms = document.querySelectorAll('.needs-validation');
            Array.prototype.slice.call(forms).forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        })();

        document.addEventListener('DOMContentLoaded', function () {
            var editProductModal = document.getElementById('editProductModal');

            editProductModal.addEventListener('show.bs.modal', function (event) {
                var button = event.relatedTarget;

                document.getElementById('editProductID').value = button.getAttribute('data-id');
                document.getElementById('editProductName').value = button.getAttribute('data-name');
                document.getElementById('editCategoryID').value = button.getAttribute('data-category');
                document.getElementById('editProvider').value = button.getAttribute('data-provider');
                document.getElementById('editPrice').value = button.getAttribute('data-price');
                document.getElementById('editOldPrice').value = button.getAttribute('data-oldprice');
                document.getElementById('editAmount').value = button.getAttribute('data-amount');

                // Extract Warranty Period
                var warrantyPeriodText = button.getAttribute('data-warranty') || "0 Năm";
                var warrantyPeriodNumber = warrantyPeriodText.split(" ")[0];
                document.getElementById('editWarrantyPeriod').value = warrantyPeriodNumber;

                document.getElementById('editDescription').value = button.getAttribute('data-description');

                // Set Current Image
                var currentImage = button.getAttribute('data-image');
                document.getElementById('editCurrentImage').src = currentImage;
            });
        });
        document.addEventListener('DOMContentLoaded', function () {
            var confirmToggleModal = document.getElementById('confirmToggleModal');

            confirmToggleModal.addEventListener('show.bs.modal', function (event) {
                var button = event.relatedTarget;
                var productId = button.getAttribute('data-id');
                var currentStatus = button.getAttribute('data-status');

                // Determine new status
                var newStatus = currentStatus === 'Active' ? 'DeActive' : 'Active';

                // Set hidden input values
                document.getElementById('toggleProductID').value = productId;
                document.getElementById('toggleStatusValue').value = newStatus;

                // Update confirmation message dynamically
                document.getElementById('toggleMessage').textContent =
                        "Bạn có chắc chắn muốn chuyển trạng thái sản phẩm này sang '" +
                        (newStatus === 'Active' ? 'Hoạt động' : 'Không hoạt động') + "'?";
            });
        });


    </script>

    <jsp:include page="footer.jsp"></jsp:include>
