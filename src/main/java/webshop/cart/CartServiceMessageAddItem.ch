package webshop.cart;

public final class CartServiceMessageAddItem@A implements CartServiceMessage@A {
    private final String@A userID;
    private final CartItem@A item;

    public CartServiceMessageAddItem(String@A userID, CartItem@A item) {
        this.userID = userID;
        this.item = item;
    }

    public String@A userID() {
        return this.userID;
    }

    public CartItem@A cartItem() {
        return this.item;
    }

    public CartServiceCommand@A getCommand() {
        return CartServiceCommand@A.ADD_ITEM;
    }
}