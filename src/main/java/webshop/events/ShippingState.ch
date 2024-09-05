package webshop.events;

public class ShippingState@A {
    public ShippingState() {}

    public String@A shipOrder(Order@A order) {
        String@A address = "Shipping Address 123"@A;
        System@A.out.println("SHIPPING: Ship order '"@A + order.orderID + "' to address: "@A + address);
        return address;
    }
}