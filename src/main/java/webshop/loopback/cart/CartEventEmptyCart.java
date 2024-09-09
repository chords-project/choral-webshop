package webshop.loopback.cart;

public class CartEventEmptyCart extends CartEvent {
	public CartCommand getCommand() {
		return CartCommand.EMPTY_CART;
	}

}
