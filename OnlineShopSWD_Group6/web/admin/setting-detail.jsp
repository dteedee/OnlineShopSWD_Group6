<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<jsp:include page="header.jsp"></jsp:include>

    <main>
        <div class="container mt-4">
            <h1>Chi tiết cài đặt</h1>

            <!-- Hiển thị thông báo thành công/lỗi nếu có -->
        <c:if test="${not empty sessionScope.notification}">
            <div class="alert alert-success" role="alert">
                ${sessionScope.notification}
            </div>
            <%
                session.removeAttribute("notification");
            %>
        </c:if>
        <c:if test="${not empty sessionScope.notificationErr}">
            <div class="alert alert-danger" role="alert">
                ${sessionScope.notificationErr}
            </div>
            <%
                session.removeAttribute("notificationErr");
            %>
        </c:if>

        <c:choose>
            <c:when test="${param.edit eq 'true'}">
                <form action="${contextPath}/admin/settings" method="POST" class="needs-validation" novalidate>
                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="settingID" value="${setting.settingID}" />

                    <div class="mb-3">
                        <label class="form-label">Loại cài đặt</label>
                        <input type="text" name="settingType" class="form-control" value="${setting.settingType}"
                               required minlength="2" maxlength="50" 
                               title="Loại cài đặt phải từ 2 đến 50 ký tự">
                        <div class="invalid-feedback">
                            Vui lòng nhập loại cài đặt (2-50 ký tự).
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Giá trị cài đặt</label>
                        <textarea name="settingValue" class="form-control" rows="3" required>${setting.settingValue}</textarea>
                        <div class="invalid-feedback">
                            Vui lòng nhập giá trị cài đặt.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Trạng thái</label>
                        <select name="status" class="form-select" required>
                            <option value="Active" <c:if test="${setting.status eq 'Active'}">selected</c:if>>Hoạt động</option>
                            <option value="Inactive" <c:if test="${setting.status eq 'Inactive'}">selected</c:if>>Không hoạt động</option>
                            </select>
                            <div class="invalid-feedback">
                                Vui lòng chọn trạng thái.
                            </div>
                        </div>

                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                            <a href="${contextPath}/admin/setting-detail?id=${setting.settingID}" class="btn btn-secondary">Hủy</a>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><strong>ID:</strong> ${setting.settingID}</li>
                    <li class="list-group-item"><strong>Loại cài đặt:</strong> ${setting.settingType}</li>
                    <li class="list-group-item"><strong>Giá trị:</strong> ${setting.settingValue}</li>
                    <li class="list-group-item">
                        <strong>Trạng thái:</strong>
                        <c:choose>
                            <c:when test="${setting.status eq 'Active'}">Hoạt động</c:when>
                            <c:otherwise>Không hoạt động</c:otherwise>
                        </c:choose>
                    </li>
                </ul>
                <div class="mt-3 d-flex justify-content-between">
                    <a href="${contextPath}/admin/setting-detail?id=${setting.settingID}&edit=true" class="btn btn-primary">Chỉnh sửa</a>
                    <a href="${contextPath}/admin/settings" class="btn btn-secondary">Quay lại danh sách</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<!-- JavaScript kích hoạt Bootstrap Validation -->
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
