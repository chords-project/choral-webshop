package webshop.common.models;

public final class UserCart {
    public final String userID;
    public final Cart cart;

    public UserCart(String userID, Cart cart) {
        this.userID = userID;
        this.cart = cart;
    }
}
