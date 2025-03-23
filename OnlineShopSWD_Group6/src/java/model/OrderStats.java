package model;

public class OrderStats {

    private int successOrders;
    private int cancelledOrders;
    private int submittedOrders;

    public int getSuccessOrders() {
        return successOrders;
    }

    public void setSuccessOrders(int successOrders) {
        this.successOrders = successOrders;
    }

    public int getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(int cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }

    public int getSubmittedOrders() {
        return submittedOrders;
    }

    public void setSubmittedOrders(int submittedOrders) {
        this.submittedOrders = submittedOrders;
    }
}
