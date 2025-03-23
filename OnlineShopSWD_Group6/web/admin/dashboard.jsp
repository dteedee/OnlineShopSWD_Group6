<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<jsp:include page="header.jsp"></jsp:include>

    <main>
        <div class="container-fluid px-4">
            <h1 class="mt-4">Bảng điều khiển</h1>

            <!-- Form Khoảng Thời Gian -->
            <div class="row mb-4">
                <div class="col-md-12">
                    <form action="${contextPath}/admin/dashboard" method="get" class="form-inline">
                    <label for="startDate">Ngày bắt đầu:</label>
                    <input type="date" id="startDate" name="startDate" value="${startDate}" required />
                      
                    <label for="endDate">Ngày kết thúc:</label>
                    <input type="date" id="endDate" name="endDate" value="${endDate}" required />
                      
                    <button type="submit" class="btn btn-primary">Lọc</button>
                </form>
            </div>
        </div>

        <!-- Các Thẻ Thông Tin Bảng Điều Khiển -->
        <div class="row">
            <!-- Thẻ Đơn Hàng Thành Công -->
            <div class="col-xl-3 col-md-6">
                <div class="card bg-primary text-white mb-4">
                    <div class="card-body">
                        Đơn hàng thành công: ${orderStats.successOrders}
                    </div>
                    <div class="card-footer d-flex align-items-center justify-content-between">
                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                    </div>
                </div>
            </div>
            <!-- Thẻ Đơn Hàng Bị Hủy -->
            <div class="col-xl-3 col-md-6">
                <div class="card bg-warning text-white mb-4">
                    <div class="card-body">
                        Đơn hàng bị hủy: ${orderStats.cancelledOrders}
                    </div>
                    <div class="card-footer d-flex align-items-center justify-content-between">
                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                    </div>
                </div>
            </div>
            <!-- Thẻ Đơn Hàng Đã Gửi -->
            <div class="col-xl-3 col-md-6">
                <div class="card bg-success text-white mb-4">
                    <div class="card-body">
                        Đơn hàng đã gửi: ${orderStats.submittedOrders}
                    </div>
                    <div class="card-footer d-flex align-items-center justify-content-between">
                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                    </div>
                </div>
            </div>
            <!-- Thẻ Tổng Doanh Thu -->
            <div class="col-xl-3 col-md-6">
                <div class="card bg-danger text-white mb-4">
                    <div class="card-body">
                        Tổng doanh thu: <fmt:formatNumber value="${totalRevenue}" type="number" groupingUsed="true" />đ
                    </div>
                    <div class="card-footer d-flex align-items-center justify-content-between">
                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bảng Xu Hướng Đơn Hàng -->
        <div class="card mb-4">
            <div class="card-header">
                <i class="fas fa-table me-1"></i>
                Xu hướng đơn hàng (từ ${startDate} đến ${endDate})
            </div>
            <div class="card-body">
                <table class="table table-bordered" id="trendTable">
                    <thead>
                        <tr>
                            <th>Ngày đặt hàng</th>
                            <th>Tổng số đơn hàng</th>
                            <th>Đơn hàng thành công</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="trend" items="${orderTrendList}">
                            <tr>
                                <td><fmt:formatDate value="${trend.orderDay}" pattern="yyyy-MM-dd"/></td>
                                <td>${trend.totalOrders}</td>
                                <td>${trend.successOrders}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp"></jsp:include>