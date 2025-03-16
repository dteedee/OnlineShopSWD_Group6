package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "GoogleLoginServlet", urlPatterns = {"/GoogleLoginServlet"})
public class GoogleLoginServlet extends HttpServlet {

    private static final String CLIENT_ID = "97898189481-shtdjcbkm5dthp3g77ocjnei2dcbsgql.apps.googleusercontent.com"; 
    private static final String REDIRECT_URI = "http://localhost:8080/OnlineShopSWD_Group6/GoogleCallbackServlet";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";
    private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Construct the Google OAuth URL
        String oauthUrl = GOOGLE_AUTH_URL +
                "?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code" +
                "&scope=" + SCOPE +
                "&access_type=offline" +
                "&prompt=consent";

        // Redirect the user to Google's OAuth login page
        response.sendRedirect(oauthUrl);
    }
}