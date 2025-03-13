<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="header.jsp"></jsp:include>

    <main>
        <div class="container">
            <div class="container-fluid px-4">
                <h1 class="mt-4">Quản lý cài đặt</h1>

                <!-- Hiển thị thông báo thành công/ lỗi -->
            <c:if test="${not empty sessionScope.notification}">
                <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.notification}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <%
                    session.removeAttribute("notification");
                %>
            </c:if>
            <c:if test="${not empty sessionScope.notificationErr}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                    ${sessionScope.notificationErr}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <%
                    session.removeAttribute("notificationErr");
                %>
            </c:if>
            <div class="card mb-4">
                <div class="card-body">
                    <!-- Form Lọc (Filter) -->
                    <div class="mb-3">
                        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addSettingModal">
                            Thêm cài đặt mới
                        </button>
                    </div>
                    <form action="${contextPath}/admin/settings" method="GET" class="row g-3 mb-3">
                        <!-- Nút Thêm Cài Đặt Mới (Mở Modal) -->

                        <div class="col-md-3">
                            <label for="statusFilter" class="form-label">Lọc theo trạng thái</label>
                            <select id="statusFilter" name="status" class="form-select" onchange="this.form.submit()">
                                <option value="">Tất cả</option>
                                <option value="Active"   <c:if test="${status eq 'Active'}">selected</c:if>>Hoạt động</option>
                                <option value="Inactive" <c:if test="${status eq 'Inactive'}">selected</c:if>>Không hoạt động</option>
                                </select>
                            </div>

                        </form>

                        <!-- Bảng hiển thị danh sách Settings -->
                        <table  id="datatablesSimple" class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Kiểu</th>
                                    <th>Giá trị</th>
                                    <th>Trạng thái</th>
                                    <th style="width: 300px;">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="s" items="${listSettings}" varStatus="loop">
                                <tr>
                                    <td>${s.settingID}</td>
                                    <td>${s.settingType}</td>
                                    <td>${s.settingValue}</td>
                                    <td>
                                        <!-- Hiển thị tiếng Việt dựa trên giá trị cột status -->
                                        <c:choose>
                                            <c:when test="${s.status eq 'Active'}">Hoạt động</c:when>
                                            <c:otherwise>Không hoạt động</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <!-- Xem chi tiết -->
                                        <a href="${contextPath}/admin/setting-detail?id=${s.settingID}" 
                                           class="btn btn-secondary btn-sm">
                                            Xem
                                        </a>
                                        <!-- Chuyển trạng thái (Toggle) -->
                                        <form action="${contextPath}/admin/settings" method="POST" style="display:inline"
                                              onsubmit="return confirm('Bạn có chắc muốn thay đổi trạng thái cài đặt này?');">
                                            <input type="hidden" name="action" value="toggle">
                                            <input type="hidden" name="settingID" value="${s.settingID}">
                                            <button type="submit" class="btn btn-warning btn-sm">
                                                <c:choose>
                                                    <c:when test="${s.status eq 'Active'}">Bỏ kích hoạt</c:when>
                                                    <c:otherwise>Kích hoạt</c:otherwise>
                                                </c:choose>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div></div>
</main>

<!-- Modal Thêm Cài Đặt Mới -->
<div class="modal fade" id="addSettingModal" tabindex="-1" aria-labelledby="addSettingModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Sử dụng Bootstrap validation -->
            <form action="${contextPath}/admin/settings" method="POST" class="needs-validation" novalidate>
                <div class="modal-header">
                    <h5 class="modal-title" id="addSettingModalLabel">Thêm cài đặt mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="action" value="add">
                    <div class="mb-3">
                        <label class="form-label">Loại cài đặt</label>
                        <input type="text" name="settingType" class="form-control" required 
                               minlength="2" maxlength="50"
                               title="Loại cài đặt phải từ 2 đến 50 ký tự">
                        <div class="invalid-feedback">
                            Vui lòng nhập loại cài đặt (2-50 ký tự).
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Giá trị cài đặt</label>
                        <textarea name="settingValue" class="form-control" rows="3" required></textarea>
                        <div class="invalid-feedback">
                            Vui lòng nhập giá trị cài đặt.
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Trạng thái</label>
                        <select name="status" class="form-select" required>
                            <option value="Active">Hoạt động</option>
                            <option value="Inactive">Không hoạt động</option>
                        </select>
                        <div class="invalid-feedback">
                            Vui lòng chọn trạng thái.
                        </div>
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

<!-- Script Bootstrap Validation (nếu chưa có) -->
<script>
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
