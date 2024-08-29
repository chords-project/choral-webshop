package webshop.orchestrated.cart;

public final class CartItem@A {
    private final String@A productID;
    private final int@A quantity;

    public CartItem(String@A productID, int@A quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public String@A productID() {
        return this.productID;
    }

    public int@A quantity() {
        return this.quantity;
    }
}
