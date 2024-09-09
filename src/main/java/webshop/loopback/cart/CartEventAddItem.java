package webshop.loopback.cart;

public class CartEventAddItem extends CartEvent {
	public CartCommand getCommand() {
		return CartCommand.ADD_ITEM;
	}

}
