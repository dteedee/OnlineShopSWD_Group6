package controller;

import com.google.gson.Gson;
import dao.CartsDAO;
import dao.UsersDAO;
import model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import model.Carts;

@WebServlet(name = "GoogleCallbackServlet", urlPatterns = {"/GoogleCallbackServlet"})
public class GoogleCallbackServlet extends HttpServlet {

    private static final String CLIENT_ID = "97898189481-shtdjcbkm5dthp3g77ocjnei2dcbsgql.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/OnlineShopSWD_Group6/GoogleCallbackServlet";
    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null) {
            response.sendRedirect("Login.jsp?error=Authorization code not found");
            return;
        }

        // Step 1: Exchange authorization code for access token
        String accessToken = getAccessToken(code);
        if (accessToken == null) {
            response.sendRedirect("Login.jsp?error=Failed to obtain access token");
            return;
        }

        // Step 2: Use access token to get user info
        GoogleUserInfo userInfo = getUserInfo(accessToken);
        if (userInfo == null) {
            response.sendRedirect("Login.jsp?error=Failed to retrieve user info");
            return;
        }

        // Step 3: Check if the user exists in the database
        UsersDAO userDAO = new UsersDAO();
        Users user = userDAO.getUserByEmail(userInfo.getEmail());
        HttpSession session = request.getSession();

        if (user != null) {
            // User exists in the database, log them in
            session.setAttribute("username", user.getUserName());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());
            session.setAttribute("user", user);

            if (user.getRole().equalsIgnoreCase("Admin")) {
                response.sendRedirect("admin/dashboard");
            } else {
                response.sendRedirect("/OnlineShopSWD_Group6/home");
            }
        } else {
            // User does not exist, create a new user
            user = new Users();
            user.setEmail(userInfo.getEmail());
            user.setUserName(userInfo.getEmail().split("@")[0]); // Use email prefix as username
            user.setFirstName(userInfo.getGiven_name());
            user.setLastName(userInfo.getFamily_name());
            user.setPassword(""); // No password for Google login users (or set a random one)
            user.setRole("Customer"); // Default role
            user.setStatus("Active"); // Default status
            // Set other fields as needed (e.g., gender, address, etc.)

            boolean added = userDAO.addUser(user);
            if (added) {
                user = userDAO.getUserByEmail(userInfo.getEmail()); // Fetch the newly created user
                CartsDAO cartsDAO = new CartsDAO();
                cartsDAO.addCart(new Carts(user.getUserID(), "Normal"));
                session.setAttribute("username", user.getUserName());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("role", user.getRole());
                session.setAttribute("user", user);
                response.sendRedirect("/OnlineShopSWD_Group6/home");
            } else {
                response.sendRedirect("Login.jsp?error=Failed to create new user");
            }
        }
    }

    // Exchange authorization code for access token
    private String getAccessToken(String code) throws IOException {
        URL url = new URL(TOKEN_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String params = "code=" + code +
                "&client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET +
                "&redirect_uri=" + REDIRECT_URI +
                "&grant_type=authorization_code";
        byte[] postData = params.getBytes(StandardCharsets.UTF_8);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        // Parse the JSON response to get the access token
        Gson gson = new Gson();
        TokenResponse tokenResponse = gson.fromJson(response.toString(), TokenResponse.class);
        return tokenResponse.getAccess_token();
    }

    // Get user info using the access token
    private GoogleUserInfo getUserInfo(String accessToken) throws IOException {
        URL url = new URL(USER_INFO_URL + "?access_token=" + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        // Parse the JSON response to get user info
        Gson gson = new Gson();
        return gson.fromJson(response.toString(), GoogleUserInfo.class);
    }

    // Class to hold the token response from Google
    private static class TokenResponse {
        private String access_token;

        public String getAccess_token() {
            return access_token;
        }
    }

    // Class to hold the user info from Google
    private static class GoogleUserInfo {
        private String email;
        private String given_name;
        private String family_name;

        public String getEmail() {
            return email;
        }

        public String getGiven_name() {
            return given_name;
        }

        public String getFamily_name() {
            return family_name;
        }
    }
}