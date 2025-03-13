package model;

public class ProductDetails {
    private int productDetailID;
    private int productID;
    private String productDetailName;
    private String value;

    public ProductDetails() {
    }

    public ProductDetails(int productDetailID, int productID, String productDetailName, String value) {
        this.productDetailID = productDetailID;
        this.productID = productID;
        this.productDetailName = productDetailName;
        this.value = value;
    }

    public int getProductDetailID() {
        return productDetailID;
    }

    public void setProductDetailID(int productDetailID) {
        this.productDetailID = productDetailID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductDetailName() {
        return productDetailName;
    }

    public void setProductDetailName(String productDetailName) {
        this.productDetailName = productDetailName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
