package model;

public class CartItems {
    private int cartItemID;
    private int cartID;
    private int productID;
    private int quantity;
    private String productName;
    private double price;

    public CartItems(int cartItemID, int cartID, int productID, int quantity, String productName, double price) {
        this.cartItemID = cartItemID;
        this.cartID = cartID;
        this.productID = productID;
        this.quantity = quantity;
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    

    public CartItems() {
    }

    public CartItems(int cartItemID, int cartID, int productID, int quantity) {
        this.cartItemID = cartItemID;
        this.cartID = cartID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public CartItems(int cartID, int productID, int quantity) {
        this.cartID = cartID;
        this.productID = productID;
        this.quantity = quantity;
    }
    
    public int getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(int cartItemID) {
        this.cartItemID = cartItemID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
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
    
}
