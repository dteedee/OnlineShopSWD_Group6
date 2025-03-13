<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<jsp:include page="header.jsp"></jsp:include>

    <main>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <!-- Card for User Profile -->
                    <div class="card mt-5 shadow">
                        <div class="card-header bg-primary text-white">
                            <h3>Chi tiết người dùng</h3>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <!-- User Avatar -->
                                <div class="col-md-4 text-center">
                                    <img src="https://w7.pngwing.com/pngs/340/946/png-transparent-avatar-user-computer-icons-software-developer-avatar-child-face-heroes-thumbnail.png" 
                                         class="img-fluid rounded-circle mb-3" 
                                         alt="User Avatar" 
                                         style="max-width:150px;">
                                </div>
                                <!-- User Information -->
                                <div class="col-md-8">
                                    <h4>${user.firstName} ${user.lastName}</h4>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item"><strong>Tên người dùng:</strong> ${user.userName}</li>
                                    <li class="list-group-item"><strong>Vai trò:</strong> ${user.role}</li>
                                    <li class="list-group-item"><strong>Email:</strong> ${user.email}</li>
                                    <li class="list-group-item"><strong>Số điện thoại:</strong> ${user.phoneNumber}</li>
                                    <li class="list-group-item"><strong>Giới tính:</strong> ${user.gender}</li>
                                    <li class="list-group-item"><strong>Ngày sinh:</strong> ${user.dateOfBirth}</li>
                                    <li class="list-group-item"><strong>Địa chỉ:</strong> ${user.address}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer text-end">
                        <a href="${contextPath}/admin/user-management" class="btn btn-secondary">Quay lại</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"></jsp:include>
