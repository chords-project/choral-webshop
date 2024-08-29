package webshop.orchestrated.cart.messages;

import webshop.orchestrated.cart.CartItem;

public final class AddItemReq implements CartMessage {
	private final String userID;
	private final CartItem item;

	public AddItemReq( String userID, CartItem item ) {
		this.userID = userID;
		this.item = item;
	}

	public String userID() {
		return this.userID;
	}
	
	public CartItem item() {
		return this.item;
	}
	
	public CartCommand getCommand() {
		return CartCommand.ADD_ITEM_REQ;
	}

}
