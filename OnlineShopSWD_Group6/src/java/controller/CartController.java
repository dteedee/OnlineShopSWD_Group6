package controller;
import dao.*;
import model.*;
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
/**
 *
 * @author admin
 */
@WebServlet(name="CartController", urlPatterns={"/Cart"})
public class CartController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            request.setAttribute("loginError", "Bạn cần phải đăng nhập trước");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
        
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
