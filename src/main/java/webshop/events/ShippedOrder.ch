package webshop.events;

public final class ShippedOrder@A {
    public final String@A orderID;
    public final String@A userID;
    public final Cart@A products;
    public final String@A address;

    public ShippedOrder(String@A orderID, String@A userID, Cart@A products, String@A address) {
        this.orderID = orderID;
        this.userID = userID;
        this.products = products;
        this.address = address;
    }
}