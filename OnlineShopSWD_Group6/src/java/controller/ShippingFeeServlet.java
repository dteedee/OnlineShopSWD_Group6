/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ShippingFeeServlet", urlPatterns = {"/calculate-shipping"})
public class ShippingFeeServlet extends HttpServlet {

    private static final String GHN_API_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
    private static final String TOKEN = "8d932ea4-f276-11ef-a653-3600c660ea00";
    private static final int SHOP_ID = 196031;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String toWardCode = request.getParameter("toWardCode");
        String toDistrictId = request.getParameter("toDistrictId");

        if (toWardCode == null || toDistrictId == null) {
            response.getWriter().write("{\"error\": \"Thiếu thông tin địa chỉ\"}");
            return;
        }


//        int serviceId = getServiceId(13005, Integer.parseInt(toDistrictId)); // 1447 là from_district (có thể thay đổi)
//        if (serviceId == -1) {
//            response.getWriter().write("{\"error\": \"Không tìm thấy dịch vụ vận chuyển\"}");
//            return;
//        }
        // Tạo request JSON
        JSONObject requestBody = new JSONObject();
        requestBody.put("shop_id", SHOP_ID);
        requestBody.put("to_ward_code", toWardCode);
        requestBody.put("from_district_id ", 13005);
        requestBody.put("to_district_id", Integer.parseInt(toDistrictId));
        requestBody.put("weight", 100);
        requestBody.put("service_type_id", 2);
        // Gửi request đến API GHN
        try {
            URL url = new URL(GHN_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Token", TOKEN);
            conn.setDoOutput(true);

            // Ghi dữ liệu vào request body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Nhận phản hồi từ API GHN
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder responseText = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        responseText.append(responseLine.trim());
                    }
                    response.getWriter().write(responseText.toString());
                }
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder errorText = new StringBuilder();
                    String errorLine;
                    while ((errorLine = br.readLine()) != null) {
                        errorText.append(errorLine.trim());
                    }
                    System.out.println("API Error: " + errorText.toString()); // Debug lỗi từ API
                    response.getWriter().write("{\"error\": \"Không thể lấy phí ship\", \"details\": " + errorText.toString() + "}");
                }
            }
        } catch (Exception e) {
            response.getWriter().write("{\"error\": \"Lỗi kết nối API\"}");
            e.printStackTrace();
        }
    }
    private int getServiceId(int fromDistrict, int toDistrict) throws IOException {
    String apiUrl = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";
    
    JSONObject requestBody = new JSONObject();
    requestBody.put("shop_id", SHOP_ID);
    requestBody.put("from_district", fromDistrict);
    requestBody.put("to_district", toDistrict);
    
    URL url = new URL(apiUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setRequestProperty("Token", TOKEN);
    conn.setDoOutput(true);

    try (OutputStream os = conn.getOutputStream()) {
        byte[] input = requestBody.toString().getBytes("utf-8");
        os.write(input, 0, input.length);
    }

    int responseCode = conn.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder responseText = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseText.append(responseLine.trim());
            }
            JSONObject responseJson = new JSONObject(responseText.toString());
            if (responseJson.has("data")) {
                return responseJson.getJSONArray("data").getJSONObject(0).getInt("service_id");
            }
        }
    }
    return -1; // Trả về -1 nếu không tìm thấy service_id
}

}


