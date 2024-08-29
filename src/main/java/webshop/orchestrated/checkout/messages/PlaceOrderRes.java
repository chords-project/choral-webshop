package webshop.orchestrated.checkout.messages;

import java.util.List;
import webshop.orchestrated.cart.CartItem;

public class PlaceOrderRes implements CheckoutMessage {
	public final String orderID;
	public final String address;
	public final List < CartItem > items;

	public PlaceOrderRes( String orderID, String address, List < CartItem > items ) {
		this.orderID = orderID;
		this.address = address;
		this.items = items;
	}

	public CheckoutCommand getCommand() {
		return CheckoutCommand.PLACE_ORDER_RES;
	}

}
