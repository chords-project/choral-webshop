package webshop.choreographic;

public final class CartPlaceOrderReq@A {
    public final String@A userID;
    public final Cart@A cart;

    public CartPlaceOrderReq(String@A userID, Cart@A cart) {
        this.userID = userID;
        this.cart = cart;
    }
}