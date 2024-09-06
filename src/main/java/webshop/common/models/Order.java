package webshop.common.models;

public final class Order {
    public final String orderID;
    public final String userID;
    public final Cart products;
    public String shippingAddress;

    public Order(String orderID, String userID, Cart products) {
        this.orderID = orderID;
        this.userID = userID;
        this.products = products;
    }

    public void addShippingAddress(String address) {
        this.shippingAddress = address;
    }
}