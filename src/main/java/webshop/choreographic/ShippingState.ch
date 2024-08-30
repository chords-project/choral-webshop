package webshop.choreographic;

public class ShippingState@A {
    public ShippingState() {}

    public ShippedOrder@A shipOrder(Order@A order) {
        ShippedOrder@A shippedOrder = new ShippedOrder@A(order.orderID, order.userID, order.products, "Shipping Address 123"@A);
        System@A.out.println("SHIPPING: Ship order '"@A + shippedOrder.orderID + "' to address: "@A + shippedOrder.address);
        return shippedOrder;
    }
}