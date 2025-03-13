package model;

public class Carts {

    private int cartID;
    private int customerID;
    private String status;

    public Carts() {
    }

    public Carts(int cartID, int customerID, String status) {
        this.cartID = cartID;
        this.customerID = customerID;
        this.status = status;
    }

    public Carts(int customerID, String status) {
        this.customerID = customerID;
        this.status = status;
    }
    
    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
