package model;

import java.util.Date;

public class OrderTrend {
    private Date orderDay; 
    private int totalOrders;
    private int successOrders;

    public Date getOrderDay() {
        return orderDay;
    }
    public void setOrderDay(Date orderDay) {
        this.orderDay = orderDay;
    }
    public int getTotalOrders() {
        return totalOrders;
    }
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
    public int getSuccessOrders() {
        return successOrders;
    }
    public void setSuccessOrders(int successOrders) {
        this.successOrders = successOrders;
    }
}
