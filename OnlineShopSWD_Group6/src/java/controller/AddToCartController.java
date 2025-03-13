package controller;

import dao.CartItemsDAO;
import dao.CartsDAO;
import model.CartItems;
import model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

@WebServlet(name="AddToCartController", urlPatterns={"/add-to-cart"})
public class AddToCartController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddToCartController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddToCartController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();
        CartItemsDAO ciDAO = new CartItemsDAO();
        CartsDAO cDAO = new CartsDAO();

        try {
            // Lấy dữ liệu từ request
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // Kiểm tra số lượng hợp lệ
            if (quantity < 1) {
                jsonResponse.put("success", false);
                jsonResponse.put("error", "Số lượng không hợp lệ");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            // Lấy giỏ hàng từ session
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            if(user!=null){
            Map<Integer, CartItems> list = ciDAO.getCartItemsByCartIDasMap(cDAO.getCartByCustomerID(user.getUserID()).getCartID());
            // Thêm sản phẩm vào giỏ hàng
            if(list.size() !=0 ){
            for (int id : list.keySet()) {
                // Nếu sản phẩm đã có trong giỏ hàng, cộng thêm số lượng
               CartItems ci = list.get(id);
               if(ci.getProductID() == productId){
                   int old = ciDAO.getCartItemByID(id).getQuantity();
                   ciDAO.updateQuantityCartItem(old + quantity, id);
                   break;
               }else{
                   ciDAO.addCartItem(new CartItems(cDAO.getCartByCustomerID(user.getUserID()).getCartID(), productId, quantity));
                   break;
               }
            }
            }else{
                ciDAO.addCartItem(new CartItems(cDAO.getCartByCustomerID(user.getUserID()).getCartID(), productId, quantity));
            }
                
            } else {
                Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
                if (cart == null) {
                cart = new HashMap<>();
                session.setAttribute("cart", cart); // Lưu giỏ hàng vào session
                }  
                if (cart.containsKey(productId)) {
                // Nếu sản phẩm đã có trong giỏ hàng, cộng thêm số lượng
                cart.put(productId, cart.get(productId) + quantity);
            } else {
                // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
                cart.put(productId, quantity);
            }
            }
            

            // Trả về kết quả thành công
            jsonResponse.put("success", true);
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("error", "Dữ liệu không hợp lệ");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("error", "Lỗi hệ thống: " + e.getMessage());
        }

        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
