package webshop.loopback.cart;

public class CartEventEmptyCart@X extends CartEvent@X {
    public CartCommand@X getCommand() {
        return CartCommand@X.EMPTY_CART;
    }
}