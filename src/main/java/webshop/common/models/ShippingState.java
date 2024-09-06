package webshop.common.models;

public class ShippingState {
    public ShippingState() {
    }

    public String shipOrder(Order order) {
        String address = "Shipping Address 123";
        System.out.println("SHIPPING: Ship order '" + order.orderID + "' to address: " + address);
        return address;
    }
}