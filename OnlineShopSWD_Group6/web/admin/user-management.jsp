<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<jsp:include page="header.jsp"></jsp:include>
    <main>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Quản lý người dùng</h1>
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
                                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addUserModal">
                                    Thêm mới
                                </button>
                            </div>
                            <form action="${contextPath}/admin/user-management" method="GET" class="row g-3">
                                <div class="col-md-3">
                                    <label for="genderFilter" class="form-label">Giới tính</label>
                                    <select name="gender" id="genderFilter" class="form-select" onchange="this.form.submit()">
                                        <option value="" ${empty param.gender ? 'selected' : ''}>Tất cả</option>
                                        <option value="Male" ${param.gender eq 'Male' ? 'selected' : ''}>Nam</option>
                                        <option value="Female" ${param.gender eq 'Female' ? 'selected' : ''}>Nữ</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label for="roleFilter" class="form-label">Vai trò</label>
                                    <select name="role" id="roleFilter" class="form-select" onchange="this.form.submit()">
                                        <option value="" ${empty param.role ? 'selected' : ''}>All</option>
                                        <option value="Admin" ${param.role eq 'Admin' ? 'selected' : ''}>Admin</option>
                                        <option value="Customer" ${param.role eq 'Customer' ? 'selected' : ''}>Customer</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label for="statusFilter" class="form-label">Trạng thái</label>
                                    <select name="status" id="statusFilter" class="form-select" onchange="this.form.submit()">
                                        <option value="" ${empty param.status ? 'selected' : ''}>Tất cả</option>
                                        <option value="Active" ${param.status eq 'Active' ? 'selected' : ''}>Hoạt động</option>
                                        <option value="Deactive" ${param.status eq 'Deactive' ? 'selected' : ''}>Không hoạt động</option>
                                    </select>
                                </div>
                            </form>
                        </div>
                    </div>
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Tên</th>
                            <th>Tên đăng nhập</th>
                            <th>Giới tính</th>
                            <th>Vai trò</th>
                            <th>Email</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${users}" var="u" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${u.firstName} ${u.lastName}</td>
                                <td>${u.userName} </td>
                                <td>${u.gender} </td>
                                <td>${u.role} </td>
                                <td>${u.email} </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${u.status eq 'Active'}"><span style="color: green">Hoạt động</span></c:when>
                                        <c:otherwise><span style="color: red">Không hoạt động</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td style="display: inline; gap: 5px">
                                    <a class="btn btn-secondary btn-sm" href="${contextPath}/admin/user-detail?id=${u.userID}"><i class="fa fa-eye"></i></a>
                                        <c:if test="${currentUser.userID ne u.userID}">
                                        <button type="button" 
                                                class="btn btn-primary btn-sm" 
                                                data-bs-toggle="modal" 
                                                data-bs-target="#editRoleModal" 
                                                data-userid="${u.userID}" 
                                                data-username="${u.userName}" 
                                                data-currentrole="${u.role}">
                                            Sửa
                                        </button>
                                        <form action="${contextPath}/admin/user-management" method="POST" style="display:inline"
                                              onsubmit="return confirm('Bạn có chắc muốn thay đổi trạng thái người dùng này?');">
                                            <input type="hidden" name="action" value="toggle">
                                            <input type="hidden" name="id" value="${u.userID}">
                                            <button type="submit" class="btn btn-warning btn-sm">
                                                <c:choose>
                                                    <c:when test="${u.status eq 'Active'}">Bỏ kích hoạt</c:when>
                                                    <c:otherwise>Kích hoạt</c:otherwise>
                                                </c:choose>
                                            </button>
                                        </form>
                                    </c:if>
                                </td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
<!-- Edit Role Modal -->
<div class="modal fade" id="editRoleModal" tabindex="-1" aria-labelledby="editRoleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Form to submit the role change -->
            <form action="${contextPath}/admin/user-management" method="POST">
                <div class="modal-header">
                    <h5 class="modal-title" id="editRoleModalLabel">Cập nhật người dùng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Hidden field to store the user ID -->
                    <input type="hidden" id="editUserId" name="id" value="">
                    <input type="hidden"  name="action" value="edit">
                    <div class="mb-3">
                        <label for="roleSelect" class="form-label">Vai trò</label>
                        <select class="form-select" name="role" id="roleSelect">
                            <option value="Admin">Admin</option>
                            <option value="Customer">Customer</option>
                        </select>
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
<!-- Add User Modal -->
<!-- Modal Thêm Người Dùng -->
<div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Sử dụng lớp 'needs-validation' của Bootstrap để bật xác thực -->
            <form action="${contextPath}/admin/user-management" method="POST" class="needs-validation" novalidate>
                <!-- Trường ẩn xác định hành động thêm mới -->
                <input type="hidden" name="action" value="add">
                <div class="modal-header">
                    <h5 class="modal-title" id="addUserModalLabel">Thêm Người Dùng Mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                </div>
                <div class="modal-body">
                    <!-- Tên (First Name) -->
                    <div class="mb-3">
                        <label for="firstName" class="form-label">Tên</label>
                        <input type="text" class="form-control" id="firstName" name="firstName"
                               required minlength="2" maxlength="20" pattern=".{4,20}"
                               title="Tên phải có từ 2 đến 20 ký tự">
                        <div class="invalid-feedback">
                            Vui lòng nhập Tên (từ 4 đến 20 ký tự).
                        </div>
                    </div>
                    <!-- Họ (Last Name) -->
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Họ</label>
                        <input type="text" class="form-control" id="lastName" name="lastName"
                               required minlength="2" maxlength="20" pattern=".{4,20}"
                               title="Họ phải có từ 2 đến 20 ký tự">
                        <div class="invalid-feedback">
                            Vui lòng nhập Họ (từ 4 đến 20 ký tự).
                        </div>
                    </div>
                    <!-- Email -->
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required
                               title="Vui lòng nhập email hợp lệ">
                        <div class="invalid-feedback">
                            Vui lòng nhập địa chỉ email hợp lệ.
                        </div>
                    </div>
                    <!-- Số điện thoại -->
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Số điện thoại</label>
                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                               required pattern="^0\d{9}$"
                               title="Số điện thoại phải có 10 chữ số và bắt đầu bằng 0">
                        <div class="invalid-feedback">
                            Vui lòng nhập số điện thoại hợp lệ (10 chữ số, bắt đầu bằng 0).
                        </div>
                    </div>
                    <!-- Giới tính -->
                    <div class="mb-3">
                        <label for="gender" class="form-label">Giới tính</label>
                        <select class="form-select" id="gender" name="gender" required>
                            <option value="">Chọn giới tính</option>
                            <option value="Male">Nam</option>
                            <option value="Female">Nữ</option>
                        </select>
                        <div class="invalid-feedback">
                            Vui lòng chọn giới tính.
                        </div>
                    </div>
                    <!-- Vai trò -->
                    <div class="mb-3">
                        <label for="role" class="form-label">Vai trò</label>
                        <select class="form-select" id="role" name="role" required>
                            <option value="">Chọn vai trò</option>
                            <option value="Admin">Admin</option>
                            <option value="Customer">Khách hàng</option>
                        </select>
                        <div class="invalid-feedback">
                            Vui lòng chọn vai trò.
                        </div>
                    </div>
                    <!-- Ngày sinh -->
                    <div class="mb-3">
                        <label for="dateOfBirth" class="form-label">Ngày sinh</label>
                        <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" required>
                        <div class="invalid-feedback">
                            Vui lòng chọn ngày sinh.
                        </div>
                    </div>
                    <!-- Địa chỉ -->
                    <div class="mb-3">
                        <label for="address" class="form-label">Địa chỉ</label>
                        <textarea class="form-control" id="address" name="address" rows="2" required></textarea>
                        <div class="invalid-feedback">
                            Vui lòng nhập địa chỉ.
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-success">Thêm Người Dùng</button>
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
</script>

<jsp:include page="footer.jsp"></jsp:include>
