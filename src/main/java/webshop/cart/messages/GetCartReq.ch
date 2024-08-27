package webshop.cart.messages;

public final class GetCartReq@A implements CartMessage@A {
    private final String@A userID;

    public GetCartReq(String@A userID) {
        this.userID = userID;
    }

    public String@A userID() {
        return this.userID;
    }

    public CartCommand@A getCommand() {
        return CartCommand@A.GET_CART_REQ;
    }
}