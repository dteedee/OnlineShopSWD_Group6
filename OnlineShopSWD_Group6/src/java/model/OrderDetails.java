package model;

public class OrderDetails {
    private int orderDetailID;
    private int orderID;
    private int productID;
    private int quantity;
    private double price;
    private double priceProduct;
    private String imgProduct;
    private String nameProduct;
    private String desProduct;                                                                                                                                                                                        

    public OrderDetails(int orderDetailID, int orderID, int productID, int quantity, double price, double priceProduct, String imgProduct, String nameProduct, String desProduct) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.priceProduct = priceProduct;
        this.imgProduct = imgProduct;
        this.nameProduct = nameProduct;
        this.desProduct = desProduct;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDesProduct() {
        return desProduct;
    }

    public void setDesProduct(String desProduct) {
        this.desProduct = desProduct;
    }
    
    

    public OrderDetails() {
    }

    public OrderDetails(int orderDetailID, int orderID, int productID, int quantity, double price) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetails(int orderID, int productID, int quantity, double price) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }
    

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
}
