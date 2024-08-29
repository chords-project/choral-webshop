package webshop.orchestrated.cart.messages;

public final class EmptyCartReq@A implements CartMessage@A {
    private final String@A userID;

    public EmptyCartReq(String@A userID) {
        this.userID = userID;
    }

    public String@A userID() {
        return this.userID;
    }

    public CartCommand@A getCommand() {
        return CartCommand@A.EMPTY_CART_REQ;
    }
}