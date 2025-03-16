
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import dao.CartItemsDAO;
import dao.OrderDetailsDAO;
import dao.OrdersDAO;
import dao.ProductsDAO;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import model.OrderDetails;
import model.Orders;
import model.Users;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy các tham số từ request
        try {
        CartItemsDAO ciDAO = new CartItemsDAO();
        HttpSession session = request.getSession();
        OrdersDAO oDAO = new OrdersDAO();
        OrderDetailsDAO odDAO = new OrderDetailsDAO();
        ProductsDAO pDAO = new ProductsDAO();
            Users user = (Users) session.getAttribute("user");

            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String jsonData = jsonBuilder.toString();

            // Sử dụng JsonParser để phân tích JSON
            JsonParser parser = new JsonParser();
            JsonObject orderData = parser.parse(jsonData).getAsJsonObject();
            // Chuyển JSON sản phẩm thành danh sách đối tượng
            Gson gson = new Gson();
            String fullAddress = orderData.get("fullAddress").getAsString();
            JsonArray productsArray = orderData.get("products").getAsJsonArray();
            double totalPrice = Double.parseDouble(orderData.get("totalPrice").getAsString());
            int id = oDAO.addOrderReturn(new Orders(user.getUserID(), null, fullAddress, "Submitted", totalPrice, ""));
            if (id <0) {
                throw new Exception("Failed to retrieve the latest order.");
            }
            for (int i = 0; i < productsArray.size(); i++) {
                JsonObject product = productsArray.get(i).getAsJsonObject();
                int cartitid = product.get("productId").getAsInt();
                int quantity = product.get("quantity").getAsInt();
                double price = pDAO.getProductByID(ciDAO.getCartItemByID(cartitid).getProductID()).getPrice() * quantity;
                boolean isDetailAdded = odDAO.addOrderDetail(new OrderDetails(id, ciDAO.getCartItemByID(cartitid).getProductID(), quantity, price));
                if (!isDetailAdded) {
                    throw new Exception("Failed to add order details.");
                }
                ciDAO.removeCartItem(cartitid);
            }
        } catch (Exception e) {

        }

    }

    // Class để định dạng phản hồi JSON
    private class ResponseMessage {

        String status;
        String message;

        ResponseMessage(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }

    // Class để ánh xạ dữ liệu sản phẩm từ JSON
    private class ProductItem {

        String productId;
        String productName;
        int quantity;
        double price;
    }
}
