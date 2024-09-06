package webshop.common.models;

public class CartItem {
    public final String productID;
    public final int quantity;

    public CartItem(String productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }
}
