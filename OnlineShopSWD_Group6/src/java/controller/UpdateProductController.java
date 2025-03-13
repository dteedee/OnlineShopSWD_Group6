package controller;

import dao.ProductsDAO;
import model.Products;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
/**
 *
 * @author admin
 */
@WebServlet(name="UpdateProductController", urlPatterns={"/update-product"})
public class UpdateProductController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateProductController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProductController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        // Đặt kiểu phản hồi là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ProductsDAO pDAO = new ProductsDAO();

        // Đọc dữ liệu JSON từ request body
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        // Lấy dữ liệu JSON
        String jsonData = sb.toString();

        try {
            // Chuyển đổi dữ liệu JSON thành các trường dữ liệu
            JSONObject jsonObject = new JSONObject(jsonData);
            int productId = jsonObject.getInt("productId");  // Lấy ID sản phẩm
            String productImg = jsonObject.getString("newProductImg");
            boolean productPro = jsonObject.getBoolean("newProductPro");
            String productName = jsonObject.getString("productName");
            float productPrice = jsonObject.getFloat("productPrice");
            float productOldPrice = jsonObject.getFloat("productOldPrice");
            String productDescription = jsonObject.getString("productDescription");
            String productCategory = jsonObject.getString("productCategory");
            int productAmount = jsonObject.getInt("productAmount");
            String productWarranty = jsonObject.getString("productWarranty");
            String productProvider = jsonObject.getString("productProvider");

            // Tạo đối tượng Product để cập nhật
            Products product = pDAO.getProductByID(productId);

            product.setImageLink(productImg);
            product.setIsPromoted(productPro);
            product.setProductName(productName);
            product.setPrice(productPrice);
            product.setOldprice(productOldPrice);
            product.setDescription(productDescription);
            product.setCategoryID(productCategory);
            product.setAmount(productAmount);
            product.setWarrantyPeriod(productWarranty);
            product.setProvider(productProvider);

            // Cập nhật sản phẩm vào cơ sở dữ liệu
            
            boolean isUpdated = pDAO.updateProduct1(product);

            // Trả về phản hồi cho client
            JSONObject responseJson = new JSONObject();
            if (isUpdated) {
                // Nếu cập nhật thành công
                responseJson.put("success", true);
            } else {
                // Nếu có lỗi xảy ra khi cập nhật
                responseJson.put("success", false);
                responseJson.put("error", "Không thể cập nhật sản phẩm, vui lòng thử lại sau." + product.getCategoryID());
            }

            // Gửi phản hồi JSON về client
            response.getWriter().write(responseJson.toString());

        } catch (Exception e) {
            // In lỗi ra log
            e.printStackTrace();

            // Trả về lỗi chi tiết nếu có exception
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("error", "Có lỗi trong quá trình xử lý: " + e.getMessage());
            errorResponse.put("exception", e.toString());  // Lưu chi tiết exception

            // Gửi lỗi về client dưới dạng JSON
            response.getWriter().write(errorResponse.toString());
        }
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