package webshop.orchestrated.cart.messages;

import webshop.orchestrated.cart.CartItem;
import java.util.List;

public final class GetCartRes implements CartMessage {
	private final String userID;
	private final List < CartItem > items;

	public GetCartRes( String userID, List < CartItem > items ) {
		this.userID = userID;
		this.items = items;
	}

	public String userID() {
		return this.userID;
	}
	
	public List < CartItem > items() {
		return this.items;
	}
	
	public CartCommand getCommand() {
		return CartCommand.GET_CART_RES;
	}

}
