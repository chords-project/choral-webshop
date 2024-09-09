package webshop.loopback.cart;

public class CartEventGetCart@X extends CartEvent@X {
    public CartCommand@X getCommand() {
        return CartCommand@X.GET_CART;
    }
}