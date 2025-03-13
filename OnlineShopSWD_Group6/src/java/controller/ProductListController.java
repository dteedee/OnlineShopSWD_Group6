package controller;

import dao.CategoryDAO;
import dao.ProductsDAO;
import model.Category;
import model.Products;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Legion
 */
    @WebServlet(name = "ProductListController", urlPatterns = {"/productsList"})
public class ProductListController extends HttpServlet {

    final ProductsDAO dbProducts = new ProductsDAO();
    final CategoryDAO categoryDAO = new CategoryDAO();

    final int PAGE_SIZE = 12;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String[] categoriesList = request.getParameterValues("category");
        String orderBy = request.getParameter("orderBy");
        List<String> categoryIds = new LinkedList<>();
        if (name == null) {
            name = "";
        }
        if (orderBy == null) {
            orderBy = ProductOrderBy.LATEST.getValue();
        }

        if (categoriesList != null) {
            categoryIds = Arrays.asList(categoriesList);
        }

        int index = 1;
        String raw_index = request.getParameter("index");

        if (raw_index != null && !raw_index.isBlank()) {
            try {
                index = Integer.parseInt(raw_index);
            } catch (Exception e) {
                index = 1;
            }
        }


        int sizeList = dbProducts.getTotalProducts(name, categoryIds, orderBy);
        int total = (sizeList % PAGE_SIZE == 0) ? (sizeList / PAGE_SIZE) : ((sizeList / PAGE_SIZE) + 1);

        request.setAttribute("index", index);
        request.setAttribute("total", total);

        Map<String, Category> mapCategories = categoryDAO.getAllCategories();
        request.setAttribute("categories", mapCategories.values());
        List<Products> productses = dbProducts.getProductsByPaging(index, PAGE_SIZE, name, categoryIds, orderBy);
        request.setAttribute("products", dbProducts.getProductsByPaging(index, PAGE_SIZE, name, categoryIds, orderBy));
        request.setAttribute("orderByList", ProductOrderBy.values());
        
        request.setAttribute("name", name);
        request.setAttribute("category", categoriesList);
        request.setAttribute("orderBy", orderBy);
        
        request.getRequestDispatcher("ProductsList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
