package webshop.orchestrated.cart.messages;

public final class GetCartReq implements CartMessage {
	private final String userID;

	public GetCartReq( String userID ) {
		this.userID = userID;
	}

	public String userID() {
		return this.userID;
	}
	
	public CartCommand getCommand() {
		return CartCommand.GET_CART_REQ;
	}

}
