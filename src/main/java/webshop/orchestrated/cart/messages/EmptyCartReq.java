package webshop.orchestrated.cart.messages;

public final class EmptyCartReq implements CartMessage {
	private final String userID;

	public EmptyCartReq( String userID ) {
		this.userID = userID;
	}

	public String userID() {
		return this.userID;
	}
	
	public CartCommand getCommand() {
		return CartCommand.EMPTY_CART_REQ;
	}

}
