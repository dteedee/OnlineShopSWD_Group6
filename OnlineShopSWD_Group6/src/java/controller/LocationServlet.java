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

/**
 *
 * @author Admin
 */
@WebServlet(name = "LocationServlet", urlPatterns = {"/LocationServlet"})
public class LocationServlet extends HttpServlet {

    private static final String TOKEN = "8d932ea4-f276-11ef-a653-3600c660ea00"; // Thay bằng token thực tế
    private static final String API_PROVINCE = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
    private static final String API_DISTRICT = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district";
    private static final String API_WARD = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String provinceId = request.getParameter("provinceId");
        String districtId = request.getParameter("districtId");

        String jsonResponse = "";

        if ("provinces".equals(action)) {
            jsonResponse = fetchFromGHN(API_PROVINCE);
        } else if ("districts".equals(action) && provinceId != null) {
            jsonResponse = fetchFromGHN(API_DISTRICT, "{\"province_id\":" + provinceId + "}");
        } else if ("wards".equals(action) && districtId != null) {
            jsonResponse = fetchFromGHN(API_WARD, "{\"district_id\":" + districtId + "}");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    private String fetchFromGHN(String urlString) {
        return fetchFromGHN(urlString, null);
    }

    private String fetchFromGHN(String urlString, String jsonInput) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("token", TOKEN);
            conn.setDoOutput(true);

            if (jsonInput != null) {
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInput.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":500, \"message\":\"Error fetching data\"}";
        }
    }

}
