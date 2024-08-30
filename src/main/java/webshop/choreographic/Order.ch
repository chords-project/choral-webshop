package webshop.choreographic;

public final class Order@A {
    public final String@A orderID;
    public final String@A userID;
    public final Cart@A products;

    public Order(String@A orderID, String@A userID, Cart@A products) {
        this.orderID = orderID;
        this.userID = userID;
        this.products = products;
    }
}