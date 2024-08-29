package webshop.orchestrated.checkout;

import webshop.orchestrated.cart.CartService_Client;
import java.util.List;
import choral.lang.Unit;
import webshop.orchestrated.checkout.messages.PlaceOrderReq;
import choral.channels.SymChannel_B;
import webshop.orchestrated.checkout.messages.PlaceOrderRes;
import webshop.orchestrated.cart.CartItem;

public class CheckoutService_Checkout {
	private SymChannel_B < Object > ch_CheckoutClient;
	private CartService_Client cartService;

	public CheckoutService_Checkout( SymChannel_B < Object > ch_CheckoutClient, CartService_Client cartService ) {
		this.ch_CheckoutClient = ch_CheckoutClient;
		this.cartService = cartService;
	}

	public Unit placeOrder( Unit req ) {
		return placeOrder();
	}
	
	public Unit placeOrder() {
		PlaceOrderReq sReq = ch_CheckoutClient.< PlaceOrderReq >com( Unit.id );
		List < CartItem > orderItems = cartService.getCart( sReq.userID );
		PlaceOrderRes res = new PlaceOrderRes( "1234", sReq.address, orderItems );
		return ch_CheckoutClient.< PlaceOrderRes >com( res );
	}

}
