package webshop.loopback.cart;

public class CartEventGetCart extends CartEvent {
	public CartCommand getCommand() {
		return CartCommand.GET_CART;
	}

}
