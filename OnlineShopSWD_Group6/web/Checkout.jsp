<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Checkout</title>
</head>
<body>
    <h1>Checkout</h1>
    <%
        // Lấy danh sách các sản phẩm đã chọn từ session
        List<Map<String, Object>> selectedItems = (List<Map<String, Object>>) session.getAttribute("selectedItems");

        // Kiểm tra nếu danh sách không rỗng
        if (selectedItems != null) {
        for (Map<String, Object> item : selectedItems) {
            // Lấy giá trị productID và quantity
            Object productIDObj = item.get("productID");
            Object quantityObj = item.get("quantity");

            // Kiểm tra kiểu dữ liệu và xử lý phù hợp
            int productID = 0;
            int quantity = 0;

            if (productIDObj instanceof Double) {
                productID = ((Double) productIDObj).intValue(); // Chuyển Double sang int
            } else if (productIDObj instanceof String) {
                productID = Integer.parseInt((String) productIDObj); // Chuyển String sang int
            }

            if (quantityObj instanceof Double) {
                quantity = ((Double) quantityObj).intValue(); // Chuyển Double sang int
            } else if (quantityObj instanceof String) {
                quantity = Integer.parseInt((String) quantityObj); // Chuyển String sang int
            }
%>
            <!-- Hiển thị thông tin sản phẩm -->
            <p>Product ID: <%= productID %>, Quantity: <%= quantity %></p>
<%
        }
    } else {
%>
        <p>No items selected.</p>
<%
    }
%>
</body>
</html>