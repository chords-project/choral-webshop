package webshop.orchestrated.checkout.messages;

import java.util.List;
import webshop.orchestrated.cart.CartItem;

public class PlaceOrderRes@A implements CheckoutMessage@A {
    public final String@A orderID;
    public final String@A address;
    public final List@A<CartItem> items;

    public PlaceOrderRes(String@A orderID, String@A address, List@A<CartItem> items) {
        this.orderID = orderID;
        this.address = address;
        this.items = items;
    }

    public CheckoutCommand@A getCommand() {
        return CheckoutCommand@A.PLACE_ORDER_RES;
    }
}