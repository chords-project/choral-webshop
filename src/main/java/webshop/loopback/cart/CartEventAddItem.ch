package webshop.loopback.cart;

public class CartEventAddItem@X extends CartEvent@X {
    public CartCommand@X getCommand() {
        return CartCommand@X.ADD_ITEM;
    }
}