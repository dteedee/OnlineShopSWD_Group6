package controller;
import dao.*;
import model.CartItems;
import model.Carts;
import model.Category;
import model.Products;
import model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpSession;

@WebServlet(name="CartController", urlPatterns={"/Cart"})
public class CartController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        CartsDAO cDAO = new CartsDAO();
        CartItemsDAO ciDAO = new CartItemsDAO();
        Users user = (Users) session.getAttribute("user");
        if(user!=null){
            if(cDAO.getCartByCustomerID(user.getUserID())!=null){
            Map<Integer, CartItems> list = ciDAO.getCartItemsByCartIDasMap(cDAO.getCartByCustomerID(user.getUserID()).getCartID());
            request.setAttribute("list", list);
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
            }else{
                cDAO.addCart(new Carts(user.getUserID(), "Normal"));
                Map<Integer, CartItems> list = ciDAO.getCartItemsByCartIDasMap(cDAO.getCartByCustomerID(user.getUserID()).getCartID());
                request.setAttribute("list", list);
                request.getRequestDispatcher("Cart.jsp").forward(request, response);
            }
        }else{
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
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
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
