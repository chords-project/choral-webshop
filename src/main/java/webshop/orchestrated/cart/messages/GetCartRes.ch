package webshop.orchestrated.cart.messages;

import java.util.List;
import webshop.orchestrated.cart.CartItem;

public final class GetCartRes@A implements CartMessage@A {
    private final String@A userID;
    private final List@A<CartItem> items;

    public GetCartRes(String@A userID, List@A<CartItem> items) {
        this.userID = userID;
        this.items = items;
    }

    public String@A userID() {
        return this.userID;
    }

    public List@A<CartItem> items() {
        return this.items;
    }

    public CartCommand@A getCommand() {
        return CartCommand@A.GET_CART_RES;
    }
}